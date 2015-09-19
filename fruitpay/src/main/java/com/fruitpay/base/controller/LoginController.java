package com.fruitpay.base.controller;

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.PageConst;
import com.fruitpay.base.model.LoginBean;
import com.fruitpay.base.service.LoginService;

@Controller
public class LoginController {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String enterLoginPage(ModelMap model){
		
		return PageConst.LOGIN_PAGE.toString();
	}
	
	@RequestMapping(value = "/sendLoginData", method = RequestMethod.POST )
	public @ResponseBody LoginBean sendLoginData(@RequestBody LoginBean loginBean){
		
		logger.info(loginBean.getEmail());
		
		boolean isEmailExisted = false;
		if(loginService.isEmailExisted(loginBean)){
			isEmailExisted = true;
		}
		
		if(isEmailExisted){
			
		}
		
		loginBean.setIsEmailExisted(isEmailExisted);
		return loginBean;
	}
	
	@RequestMapping(value = "/redirectTo", method = RequestMethod.POST )
	public String redirectTo(@RequestBody LoginBean loginBean){
		
		if(loginService.isEmailExisted(loginBean)){
			return PageConst.MAIN_PAGE.toString();
		}else{
			return PageConst.LOGIN_PAGE.toString();
		}
		
	}

}
