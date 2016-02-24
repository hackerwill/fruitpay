package com.fruitpay.comm.auth;

import java.net.InetAddress;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fruitpay.comm.service.RoleService;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.StringUtil;

public class FPAuthentication {
	private static final Log log = LogFactory.getLog(FPAuthentication.class);
	public static void main(String[] args) throws Exception {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Generate FP Token by FPSessionInfo
	 * 
	 * @param FPToken
	 * @return
	 * @throws Exception
	 */
	public static String generateFPToken(FPSessionInfo FPToken) throws Exception {
		String encodeKey = null;
		try {
			String orignalKey = FPToken.getLogonAddress() + ";" + FPToken.getUserName() + ";" + FPToken.getSessionId();
			encodeKey = FPEnDeCryption.encryptCipher(orignalKey);
			encodeKey = encodeKey.replaceAll(" ", ":");
			encodeKey = encodeKey.replaceAll("[+]", ":");
			encodeKey = encodeKey.replaceAll("=", ":");
		} catch (Exception e) {
			log.error("Generate FPToken fail", e);
			e.printStackTrace();
			throw e;
		}
		return encodeKey;
	}

	/**
	 * Validate FPToken By FPSessionInf
	 * 
	 * @param FPToken
	 * @return
	 * @throws Exception
	 */
	public static boolean validateFPToken(HttpServletRequest request) throws Exception {
		boolean isValidate = false;
		/*** get user token from Header **/
		String FPToken = FPSessionUtil.getHeader(request, LoginConst.LOGIN_AUTHORIZATION);	    
		if (!StringUtil.isEmptyOrSpace(FPToken)) {
			try {			
				isValidate =StringUtil.isEmptyOrSpace(FPSessionFactory.getInstance().getFPSessionMap().get(FPToken))?false:true;
			} catch (Exception e) {
				isValidate = false;
				e.printStackTrace();
				log.error("validateFPToken Fail", e);
			}
		}
		else{
			/** token is null **/
			isValidate =true;
		}
		return isValidate ;
	}
//		try {
//			String originalKey = fpToken.getLogonAddress() + ";" + fpToken.getUserName() + ";" + fpToken.getSessionId();
//			String deKey = FPEnDeCryption.encryptCipher(originalKey);
//			deKey = deKey.replaceAll(" ", ":");
//			deKey = deKey.replaceAll("[+]", ":");
//			deKey = deKey.replaceAll("=", ":");
//			if (null != deKey && deKey.length() > 0) {
//				if (deKey.equalsIgnoreCase(fpToken.getFPToken()))
//					isValidate = true;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			log.error("validateFPToken fail", e);
//			throw e;
//		}
//		return isValidate;
//	}
	/**
	 * Validate Admin User By Referer of request
	 * 
	 * @param FPToken
	 * @return
	 * @throws Exception
	 */
	public static boolean validateAdmin(HttpServletRequest request) throws Exception {
		boolean isValidate = false;
		/*** get user referer from Header **/
		String FPToken = FPSessionUtil.getHeader(request, LoginConst.LOGIN_AUTHORIZATION);	    
		if (!StringUtil.isEmptyOrSpace(FPToken)) {
			try {			
				FPSessionInfo tempFPS =(FPSessionInfo)FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
				if(!StringUtil.isEmptyOrSpace(tempFPS)){
				isValidate =isAdmin(tempFPS.getUserId());//request.getLocalAddr()+LoginConst.ADMIN_URL).equalsIgnoreCase(FPReferer)?false:true;
				}
			} catch (Exception e) {
				isValidate = false;
				e.printStackTrace();
				log.error("validateFPToken Fail", e);
			}
		}
		else{
			/** token is null **/
			isValidate =true;
		}
		return isValidate ;
	}

	/**
	 * Get Host Name
	 * 
	 * @return host name
	 */
	private String getHostName() {
		String host = null;
		try {
			host = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			log.error("getHostName fail", e);
		}
		return host;
	}

	private static String getHostAddress() {
		String host = null;
		try {
			host = InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			log.error("getHostAddress fail", e);
		}
		return host;
	}
	private final static String MANAGER_ID = "FruitpayAdmin";

	public static boolean isAdmin(String userId) {
		if(userId.equals(MANAGER_ID))
			return true;
		else
			return false;
	}
}
