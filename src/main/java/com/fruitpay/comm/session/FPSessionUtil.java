package com.fruitpay.comm.session;

import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.auth.FPAuthentication;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.StringUtil;



public class FPSessionUtil {
	private static final Log log = LogFactory.getLog(FPSessionUtil.class);
	public static FPSessionInfo getFPSession(String FPToken) throws Exception{
		FPSessionInfo FPSession = null;
		try{
			FPSession = FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
			if(null!=FPSession && null != FPSession.getFPToken() && FPSession.getFPToken().length()>0){
				log.debug("The FPSessionInfo Exist");
			}else{
				throw new Exception("FPSessionInfo is null");
			}
					
		}catch(Exception e){
			log.error("getFPSession fail", e);
			throw e;
		}
		return FPSession;
	}
	public static String getUserLocale(String FPToken) throws Exception{
		String locale = null;
		try{
			FPSessionInfo FPSession = FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
			if(null!=FPSession && null != FPSession.getFPToken() && FPSession.getFPToken().length()>0){
				locale = FPSession.getUserLocale().getLanguage()+"_"+FPSession.getUserLocale().getCountry();
			}else{
				throw new Exception("FPSessionInfo is null");				
			}
					
		}catch(Exception e){
			log.error("getFPSession fail", e);
			throw e;
		}
		return locale;
	}
	public static String logonGetToken(Customer pCustomer, HttpServletRequest request, String loginStyle) {
		Locale locale = null;
		String FPToken = null;
		try{
			if (!StringUtil.isEmptyOrSpace(request.getHeaders("uId"))) {
				FPSessionInfo fpSessionInfo = new FPSessionInfo();
				fpSessionInfo.setSessionId(request.getHeaders("uId").toString());
				log.debug("The SessionId:"+fpSessionInfo.getSessionId());
				/**
				 * Set User Information
				 */
				fpSessionInfo.setUserName(pCustomer.getFirstName()+pCustomer.getLastName());
				log.debug("The UserName:"+fpSessionInfo.getUserName());
				fpSessionInfo.setUserId(Integer.toString(pCustomer.getCustomerId()));
				log.debug("The UserId:"+fpSessionInfo.getUserId());				
			    locale = Locale.getDefault();			 
			    log.debug("Locale: " + locale);
			    fpSessionInfo.setUserLocale(locale);
			    log.debug("The UserLocale:"+fpSessionInfo.getUserLocale());
			    fpSessionInfo.setLogonAddress(StringUtil.isEmptyOrSpace(request.getRemoteAddr())?"127.0.0.1":request.getRemoteAddr());
				
				Date date = new Date();
				fpSessionInfo.setLastAccessTime(date);
				fpSessionInfo.setLogonTime(date);			
				
				if(StringUtil.isEmptyOrSpace(FPToken) || "null".equalsIgnoreCase(FPToken)){
					FPToken=FPAuthentication.generateFPToken(fpSessionInfo);
				}
				//Generate FPToken Key				
				fpSessionInfo.setFPToken(FPToken);
				log.debug("The currency Token:"+FPToken);
				//Put FPToken into FPSessionMap
				FPSessionFactory.getInstance().putFPToken(FPToken, fpSessionInfo);
				FPSessionFactory.getInstance().putLogonMap(fpSessionInfo.getSessionId(), fpSessionInfo.getLogonAddress()+";"+FPToken);
				log.debug("FPSessionMap["+FPSessionFactory.getInstance().getFPSessionMap().entrySet()+"]");				
			}else{
				System.out.println("The request JSESSIONID is null");
			}
		}catch(Exception e){
			log.error("FPAuthorizationService.logon fail", e);		
		}
		return FPToken;
	}
}
