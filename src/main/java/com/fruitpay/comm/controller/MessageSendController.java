package com.fruitpay.comm.controller;


import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.service.impl.EmailContentFactory;

@Controller
@RequestMapping("emailCtrl")
public class MessageSendController {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	EmailContentFactory emailContentFactory;
	
	
	
	@RequestMapping(value = "/sendTo", method = RequestMethod.GET )
	public @ResponseBody ReturnData sendTo(
			@RequestParam(value = "emails") String emails){
		
		/*if(AssertUtils.isEmpty(customer.getEmail())){
			return LoginReturnMessage.RequiredFieldsIsEmpty.getReturnMessage();
		}
		*/
		return ReturnMessageEnum.Common.Success.getReturnMessage();
	}
	
}
