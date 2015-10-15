package com.fruitpay.base.controller;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.AssertUtils;
import com.fruitpay.base.comm.Md5Util;
import com.fruitpay.base.comm.returndata.LoginReturnMessage;
import com.fruitpay.base.comm.returndata.ReturnData;
import com.fruitpay.base.comm.returndata.ReturnObject;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.LoginService;

@Controller
@RequestMapping("loginCtrl")
public class LoginController {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST )
	public @ResponseBody ReturnData loginAsOneCustomer(@RequestBody Customer customer){
		
		if(AssertUtils.isEmpty(customer.getEmail())){
			return LoginReturnMessage.RequiredFieldsIsEmpty.getReturnMessage();
		}
		
		logger.info("email: " + customer.getEmail());
		
		ReturnData lrm = loginService.login(customer.getEmail(), customer.getPassword());
		
		if("0".equals(lrm.getErrorCode())){
			ReturnObject<String> ro = new ReturnObject<String>(lrm, "mainPage");
			return ro;
		}else{
			return lrm;
		}
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST )
	public @ResponseBody ReturnData SignupAsOneCustomer(@RequestBody Customer customer){
		
		if(AssertUtils.anyIsEmpty(
				customer.getEmail(), customer.getPassword(),
				customer.getFirstName(), customer.getLastName())){
			return LoginReturnMessage.RequiredFieldsIsEmpty.getReturnMessage(); 
		}
		
		customer.setPassword(encodePassword(customer.getPassword()));
		
		ReturnData lrm = loginService.signup(customer);
		
		return lrm;
	}
	
	private String encodePassword(String password){
		return Md5Util.getMd5(password);
	}

}
