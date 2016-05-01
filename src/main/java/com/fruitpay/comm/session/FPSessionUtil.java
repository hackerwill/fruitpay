package com.fruitpay.comm.session;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.http.HttpRequest;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.auth.FPAuthentication;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.model.ReturnMessage;
import com.fruitpay.comm.model.Role;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.StringUtil;

import javassist.NotFoundException;

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

	public static String logonGetToken(Role role, HttpServletRequest request, String loginStyle) throws Exception {
		String FPToken = null;
		
		if (StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_UID))) {
			throw new NotFoundException("The request JSESSIONID is null");
		}
		
		/*****check LogonMap: clean user others token**********/
		removeUserToken(role.getUserId());				
		
		FPSessionInfo fpSessionInfo = getFPSessionInfo(role, request, loginStyle);

		if (StringUtil.isEmptyOrSpace(FPToken) || "null".equalsIgnoreCase(FPToken)) {
			FPToken = FPAuthentication.generateFPToken(fpSessionInfo);
		}
		
		// Generate FPToken Key
		fpSessionInfo.setFPToken(FPToken);
		logger.debug("The currency Token:" + FPToken);
		
		// Put FPToken into FPSessionMap
		FPSessionFactory.getInstance().putFPToken(FPToken, fpSessionInfo);
		FPSessionFactory.getInstance().putLogonMap(role.getUserId().toString()//fpSessionInfo.getSessionId(),
				,fpSessionInfo.getLogonAddress() + ";" + FPToken);
		logger.debug("FPSessionMap[" + FPSessionFactory.getInstance().getFPSessionMap().entrySet() + "]");
		
		/********put FPToken into session********/
		 HttpSession session = request.getSession(false);
		 if(session != null){
			 session.invalidate();
			 request.getSession().setAttribute(LoginConst.LOGIN_AUTHORIZATION, FPToken);
		 }
		 
		return FPToken;
	}
	
	public static FPSessionInfo getFPsessionInfo(HttpServletRequest request) throws AuthenticationException {
		String FPToken = FPSessionUtil.getHeader(request, LoginConst.LOGIN_AUTHORIZATION);	
		if(AssertUtils.isEmpty(FPToken))
			throw new AuthenticationException("Authentication failed.");
		
		FPSessionInfo fpSessionInfo = FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
		if(AssertUtils.isEmpty(fpSessionInfo))
			throw new AuthenticationException("Authentication failed.");
		
		return fpSessionInfo;
	}

	public static boolean getInfoAndValidateToken(Role role, HttpServletRequest request, String loginStyle)
			throws Exception {
		boolean validated = false;
		
		if (StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_UID))
				|| StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_AUTHORIZATION))) {
			throw new NotFoundException("The request JSESSIONID is null or request Token is null");
		}

		FPSessionInfo fpSessionInfo = getFPSessionInfo(role, request, loginStyle);
		fpSessionInfo.setFPToken(getHeader(request, LoginConst.LOGIN_AUTHORIZATION));
		
		if (!StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_UID))
				&& !StringUtil.isEmptyOrSpace(getHeader(request, LoginConst.LOGIN_AUTHORIZATION))) {
			
			validated = FPAuthentication.validateFPToken(fpSessionInfo);
			
		} else {
			
		}
		return validated;
	}

	private static FPSessionInfo getFPSessionInfo(Role role, HttpServletRequest request, String loginStyle) {
		FPSessionInfo fpSessionInfo = null;
		Locale locale = null;
		try {
			fpSessionInfo = new FPSessionInfo();
			fpSessionInfo.setSessionId(getHeader(request, LoginConst.LOGIN_UID));
			/**
			 * Set User Information
			 */
			fpSessionInfo.setUserName(role.getUserName());
			fpSessionInfo.setUserId(role.getUserId());
			locale = Locale.getDefault();
			fpSessionInfo.setUserLocale(locale);
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
