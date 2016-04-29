package com.fruitpay.backend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.backend.model.Manager;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.model.Role;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.session.model.FPSessionInfo;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.StringUtil;

@Controller
@RequestMapping("adminloginCtrl")
public class AdminLoginController {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final String MANAGER_ID = "FruitpayAdmin";
	private final String PASSWORD = "FruitpayGreat";
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Manager login(@RequestBody Manager manager, HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(manager.getManagerId()) || 
				AssertUtils.isEmpty(manager.getPassword()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		if(!isMatched(manager))
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailPasswordNotMatch.getReturnMessage());

		/*** normal login: create user token ***/
		try {
			String token = FPSessionUtil.logonGetToken(new Role(manager), request, LoginConst.NORMAL);
			manager.setToken(token);
		} catch (Exception e) {
			logger.error("login error when FPSessionUtil.logonGetSession: " + e);
		}
		
		return manager;
	}
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
	public @ResponseBody Boolean validate(@RequestBody Manager manager, HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(manager.getManagerId()) || 
				AssertUtils.isEmpty(manager.getPassword()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		boolean validate = false;
		try {
			validate = FPSessionUtil.getInfoAndVlidateToken(new Role(manager), request, LoginConst.LOGINBYID);
		} catch (Exception e) {
			logger.debug("LoginController.validateToken failed");
		}
		return validate;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody Boolean logout(HttpServletRequest request, HttpServletResponse response){
		String FPToken = FPSessionUtil.getHeader(request, LoginConst.LOGIN_AUTHORIZATION);	
        boolean cleanSessionStatus= false;
		if (!StringUtil.isEmptyOrSpace(FPToken)) {
			try {			
				if(!StringUtil.isEmptyOrSpace(FPSessionFactory.getInstance().getFPSessionMap().get(FPToken))){
					FPSessionInfo tempFPS =(FPSessionInfo)FPSessionFactory.getInstance().getFPSessionMap().get(FPToken);
				logger.debug("FPToken==[" + FPToken + "]");
				logger.debug("=======================FP Session Logout Start=======================");
				FPSessionFactory.getInstance().getFPSessionMap().remove(FPToken);
				FPSessionFactory.getInstance().getLogonMap().put(tempFPS.getUserId(), null);
				cleanSessionStatus = true;
				logger.debug("=======================FP Session Logout End=======================");
				}
			} catch (Exception e) {
				cleanSessionStatus = false;
				logger.error("ClearSessionServlet Fail", e);
			}
		}
		else{
			/** token is null **/
			cleanSessionStatus =true;
		}
		return cleanSessionStatus;
	}
	
	private boolean isMatched(Manager manager){
		return MANAGER_ID.equals(manager.getManagerId()) &&
				PASSWORD.equals(manager.getPassword());
	}
	

}
