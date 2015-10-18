package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.controller.MessageSendController;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.service.impl.EmailContentFactory;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.AssertUtils;

@Controller
@RequestMapping("loginCtrl")
public class LoginController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MessageSendController messageSendController;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST )
	public @ResponseBody ReturnData loginAsOneCustomer(@RequestBody Customer customer){
		
		if(AssertUtils.isEmpty(customer.getEmail())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
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
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage(); 
		}
		
		ReturnData lrm = loginService.signup(customer);
		
		if("0".equals(lrm.getErrorCode())){
			messageSendController.sendTo(MailType.NEW_MEMBER, customer.getEmail(), customer);	
		}
		
		return lrm;
		
	}

}
