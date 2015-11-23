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
import com.fruitpay.comm.model.MessageBean;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.service.EmailContentService;
import com.fruitpay.comm.service.impl.EmailConsumer;
import com.fruitpay.comm.service.impl.EmailContentFactory;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.FileReadUtil;

@Controller
@RequestMapping("emailCtrl")
public class MessageSendController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	EmailConsumer emailConsumer;
	@Autowired
	EmailContentFactory emailContentFactory;
	
	@PostConstruct
	public void init(){
		emailConsumer.runConsume();
	}
	
	@RequestMapping(value = "/sendTo", method = RequestMethod.GET )
	public @ResponseBody ReturnData sendTo(
			@RequestParam(value = "emails") String emails){
		
		String text = FileReadUtil.getResourceFile("template/email/emailTemplate.html");
		emailConsumer.add(new MessageBean());
		
		/*if(AssertUtils.isEmpty(customer.getEmail())){
			return LoginReturnMessage.RequiredFieldsIsEmpty.getReturnMessage();
		}
		*/
		return ReturnMessageEnum.Common.Success.getReturnMessage();
	}
	
	public <T> boolean sendTo(MailType maiType, String sendTo, T t){
		
		EmailContentService<T> emailContentervice = emailContentFactory.getEmailContentServiceImpl(MailType.NEW_MEMBER, t);
		emailConsumer.add(emailContentervice.getEmailMessageBean(t, sendTo));
		return true;
	}
	
	
	
}
