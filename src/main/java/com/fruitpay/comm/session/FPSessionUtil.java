package com.fruitpay.comm.session;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.auth.FPAuthentication;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.StringUtil;

public class FPSessionUtil {
	private static final Logger logger = Logger.getLogger(FPSessionUtil.class);

	public static FPSessionInfo getFPSession(String FPToken) throws Exception {
		FPSessionInfo FPSession = null;
		try {
			FPSession = FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
			if (null != FPSession && null != FPSession.getFPToken() && FPSession.getFPToken().length() > 0) {
				logger.debug("The FPSessionInfo Exist");
			} else {
				throw new Exception("FPSessionInfo is null");
			}

		} catch (Exception e) {
			logger.error("getFPSession fail", e);
			throw e;
		}
		return FPSession;
	}

	public static String getUserLocale(String FPToken) throws Exception {
		String locale = null;
		try {
			FPSessionInfo FPSession = FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
			if (null != FPSession && null != FPSession.getFPToken() && FPSession.getFPToken().length() > 0) {
				locale = FPSession.getUserLocale().getLanguage() + "_" + FPSession.getUserLocale().getCountry();
			} else {
				throw new Exception("FPSessionInfo is null");
			}

		} catch (Exception e) {
			logger.error("getFPSession fail", e);
			throw e;
		}
		return locale;
	}

	public static String logonGetToken(Customer pCustomer, HttpServletRequest request, String loginStyle) {
		String FPToken = null;
		try {
			if (!StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_UID))) {
				
				/*****check LogonMap: clean user others token**********/
				removeUserToken(pCustomer.getCustomerId().toString());				
				
				FPSessionInfo fpSessionInfo = getFPSessionInfo(pCustomer, request, loginStyle);

				if (StringUtil.isEmptyOrSpace(FPToken) || "null".equalsIgnoreCase(FPToken)) {
					FPToken = FPAuthentication.generateFPToken(fpSessionInfo);
				}
				// Generate FPToken Key
				fpSessionInfo.setFPToken(FPToken);
				logger.debug("The currency Token:" + FPToken);
				// Put FPToken into FPSessionMap
				FPSessionFactory.getInstance().putFPToken(FPToken, fpSessionInfo);
				FPSessionFactory.getInstance().putLogonMap(pCustomer.getCustomerId().toString()//fpSessionInfo.getSessionId(),
						,fpSessionInfo.getLogonAddress() + ";" + FPToken);
				logger.debug("FPSessionMap[" + FPSessionFactory.getInstance().getFPSessionMap().entrySet() + "]");
				/********put FPToken into session********/
				 HttpSession session = request.getSession(false);
				 if(session != null){
				 session.invalidate();
				 request.getSession().setAttribute(LoginConst.LOGIN_AUTHORIZATION, FPToken);
				 }
				 else{
					 
				 }
			} else {
				System.out.println("The request JSESSIONID is null");
			}
		} catch (Exception e) {
			logger.error("FPAuthorizationService.logon fail", e);
		}
		return FPToken;
	}

	public static boolean getInfoAndVlidateToken(Customer pCustomer, HttpServletRequest request, String loginStyle)
			throws Exception {
		FPSessionInfo fpSessionInfo = getFPSessionInfo(pCustomer, request, loginStyle);
		boolean validated = false;
		logger.debug(getHeader(request, LoginConst.LOGIN_UID));
		logger.debug(getHeader(request, LoginConst.LOGIN_AUTHORIZATION));
		if (!StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_UID))
				&& !StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_AUTHORIZATION))) {
			fpSessionInfo.setFPToken(getHeader(request, LoginConst.LOGIN_AUTHORIZATION));
			validated = FPAuthentication.validateFPToken(request);
		} else {
			System.out.println("The request JSESSIONID is null or request Token is null");
		}
		return validated;
	}

	private static FPSessionInfo getFPSessionInfo(Customer pCustomer, HttpServletRequest request, String loginStyle) {
		FPSessionInfo fpSessionInfo = null;
		Locale locale = null;
		try {
			fpSessionInfo = new FPSessionInfo();
			fpSessionInfo.setSessionId(getHeader(request, LoginConst.LOGIN_UID));
			logger.debug("The SessionId:" + fpSessionInfo.getSessionId());
			/**
			 * Set User Information
			 */
			fpSessionInfo.setUserName(pCustomer.getFirstName());
			logger.debug("The UserName:" + fpSessionInfo.getUserName());
			fpSessionInfo.setUserId(Integer.toString(pCustomer.getCustomerId()));
			logger.debug("The UserId:" + fpSessionInfo.getUserId());
			locale = Locale.getDefault();
			logger.debug("Locale: " + locale);
			fpSessionInfo.setUserLocale(locale);
			logger.debug("The UserLocale:" + fpSessionInfo.getUserLocale());
			fpSessionInfo.setLogonAddress(
					StringUtil.isEmptyOrSpace(request.getRemoteAddr()) ? "127.0.0.1" : request.getRemoteAddr());

			Date date = new Date();
			fpSessionInfo.setLastAccessTime(date);
			fpSessionInfo.setLogonTime(date);
		} catch (Exception e) {
			logger.error("FPSessionUtil.getFPSessionInfo fail", e);
		}
		return fpSessionInfo;
	}

	// get request headers
	private static Map<String, String> getHeadersInfo(HttpServletRequest request) {

		Map<String, String> map = new HashMap<String, String>();

		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement().toLowerCase();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	public static String getHeader(HttpServletRequest request, String key) {
		return getHeadersInfo(request).get(key.toLowerCase());
	}
	
	public static void removeUserToken(String userId) {
		if(!StringUtil.isEmptyOrSpace(FPSessionFactory.getInstance().getLogonMap().get(userId))){
			String userToken = FPSessionFactory.getInstance().getLogonMap().get(userId);
			userToken= userToken.substring(userToken.indexOf(";"), userToken.length());
			FPSessionFactory.getInstance().getFPSessionMap().remove(userToken);			
		}		
	}
}
