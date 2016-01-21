package com.fruitpay.comm.session.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.utils.StringUtil;

public class ClearSessionServlet extends HttpServlet{
	private static transient Logger log = LogManager.getLogger(ClearSessionServlet.class);
	// The Information Security Log add by Iris 20120522
	//private static final Log infoLog = LogFactory.getLog("VPOINT_SECURITY");
	
	private static transient Logger infoLog = LogManager.getLogger("SECURITY.logger");
	private static transient Logger sysLog = LogManager.getLogger("syslog.logger");
	public ClearSessionServlet() {
	}

	protected void doGet( HttpServletRequest req ,HttpServletResponse resp ) throws ServletException ,IOException {
		doProcess( req ,resp );
	}
   
	protected void doPost( HttpServletRequest req ,HttpServletResponse resp ) throws ServletException ,IOException{
		doProcess( req ,resp );
	}

	protected void doProcess( HttpServletRequest pReq ,HttpServletResponse pResp ) throws ServletException ,IOException{
		
		
	    if(pReq.getProtocol().equals("HTTP/1.0")) { // HTTP/1.0的話
	    	pResp.setHeader("Pragma", "no-cache");
	    }
	    else if(pReq.getProtocol().equals("HTTP/1.1")) {
	    	pResp.setHeader("Cache-Control", "no-cache");
	    }
	    
//	    String FPToken = pReq.getParameter(FPConst.FP_SESSION_SECURITY_KEY);
//	    String thirdParty = pReq.getParameter(FPConst.FP_THIRD_PARTY_LOGOUT);
//	    
//	    if(!StringUtil.isEmptyOrSpace(FPToken)){
//	    	try{
//	    		HttpSession session = pReq.getSession(false);
//	    		if(session != null)
//	    			session.invalidate();
//	    		for(int i = 0; i < pReq.getCookies().length; i++) {
//	    			pReq.getCookies()[i].setMaxAge(0);
//	    		}	    		
//	    		Map<String,String> paramMap = new HashMap<String,String>();
//	    		paramMap.put(FPConst.FP_SESSION_SECURITY_KEY, FPToken);	    		
//	    		
//	    		if(!StringUtil.isEmptyOrSpace(thirdParty)){
//	    			log.debug("=======================Third Part Session Logout Start=======================");
//	    			log.debug("thirdParty==["+thirdParty+"]");
//	    			log.debug("FPToken==["+FPToken+"]");
//	    			if(thirdParty.equalsIgnoreCase(FPConst.FP_BO_REPORT_ENGINER)){
//		    			String trustedAuthConfigFilePath = pReq.getSession().getServletContext().getRealPath(FPBusinessObjectConst.FP_BO_TRUST_CONFIG_FILE);
//		    			if(null!=trustedAuthConfigFilePath && trustedAuthConfigFilePath.length()>0){
//		    				log.debug("The SAP BusinessObjects TrustedPrincipal Config File Path:"+trustedAuthConfigFilePath);
//			    			paramMap.put(FPConst.FP_BO_TRUST_CONFIG_FILE_PATH, trustedAuthConfigFilePath);
//			    			BusinessObjectEngine.logoffSession(paramMap);
//		    			}else{
//		    				log.error("The SAP BusinessObjects TrustedPrincipal Config File Path is null");
//		    				throw new Exception("The SAP BusinessObjects TrustedPrincipal Config File Path is null");
//		    			}
//		    			
//		    		}else{
//		    			log.debug("The other third party tool support not yet");
//		    		}
//					log.debug("=======================Third Part Session Logout End=======================");
//	    		}    		
//	    		
//				log.debug("=======================FP Session Logout Start=======================");	    		
//	    		FPSessionFactory.getInstance().getFPSessionMap().remove(FPToken);
//				log.debug("=======================FP Session Logout End=======================");				
//	    	}catch(Exception e){
//	    		log.error("ClearSessionServlet Fail",e);
//	    	}	    	
//	    }  
	}
}

