package com.fruitpay.comm.controller;


import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.model.ReturnMessage;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.Md5Util;
import com.fruitpay.comm.utils.RadomValueUtil;

@Controller
@RequestMapping("emailCtrl")
public class MessageSendController {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Inject
	EmailSendService emailSendService;
	@Inject
	CustomerService customerService;
	
	
	
	@RequestMapping(value = "/sendTo", method = RequestMethod.GET )
	public @ResponseBody ReturnMessage sendTo(
			@RequestParam(value = "emails") String emails){
		
		/*if(AssertUtils.isEmpty(customer.getEmail())){
			return LoginReturnMessage.RequiredFieldsIsEmpty.getReturnMessage();
		}
		*/
		return ReturnMessageEnum.Common.Success.getReturnMessage();
	}
	
	/*@RequestMapping(value = "/sendToWordpressCustomer", method = RequestMethod.GET )
	public @ResponseBody Boolean sendToWordpressCustomer(){
		
		List<Customer> customers = customerService.findall();
		customers = customers.stream()
		.filter(c -> c.getRegisterFrom() != null && 34 == c.getRegisterFrom().getOptionId())
		.collect(Collectors.toList());
		
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = iterator.next();
			if("wade.cw.chou@gmail.com".equals(customer.getEmail()))
				continue;
			String randomPassword = RadomValueUtil.getRandomPassword();
			String newPassword = Md5Util.getMd5(randomPassword);
			customer.setPassword(newPassword); 
			customer = customerService.saveCustomer(customer);
			
			Customer sendCustomer = new Customer();
			sendCustomer.setEmail(customer.getEmail());
			sendCustomer.setFirstName(customer.getFirstName());
			sendCustomer.setLastName(customer.getLastName());
			sendCustomer.setPassword(randomPassword);
			emailSendService.sendTo(MailType.NEW_MEMBER_FROM_WORDPRESS, sendCustomer.getEmail(), sendCustomer);
		}
		
		return true;
	}*/
	
}
