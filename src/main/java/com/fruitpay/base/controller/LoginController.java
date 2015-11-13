package com.fruitpay.base.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;

@Controller
@RequestMapping("loginCtrl")
public class LoginController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	MessageSendController messageSendController;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> loginAsOneCustomer(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		logger.debug("LoginController#loginAsOneCustomer email: " + customer.getEmail());
		if(AssertUtils.anyIsEmpty(customer.getEmail(), customer.getPassword())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		}
		ReturnData<Customer> returnData = loginService.login(customer.getEmail(), customer.getPassword());
		if(returnData.getObject() != null)
			AuthenticationUtil.setSessionCustomer(request, (Customer)returnData.getObject());
		return loginService.login(customer.getEmail(), customer.getPassword());
	}
	
	@RequestMapping(value = "/loginById", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> loginByIdAsOneCustomer(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		logger.debug("LoginController#loginAsOneCustomer customerId: " + customer.getCustomerId());
		if(AssertUtils.anyIsEmpty(String.valueOf(customer.getCustomerId()), customer.getPassword())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		}
		ReturnData<Customer> returnData = 
				loginService.loginByCustomerId(customer.getCustomerId(), customer.getPassword());
		if(returnData.getObject() != null)
			AuthenticationUtil.setSessionCustomer(request, (Customer)returnData.getObject());
		return returnData;
	}
	
	@RequestMapping(value = "/fbLogin", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> fbLoginAsOneCustomer(@RequestBody Customer customer, 
			HttpServletRequest request, HttpServletResponse response){
		
		logger.debug("LoginController#fbLoginAsOneCustomer fbId=" + customer.getFbId());
		if(AssertUtils.anyIsEmpty(customer.getFbId(), customer.getFirstName())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		}
		ReturnData<Customer> returnData = loginService.fbLogin(customer);
		if(returnData.getObject() != null)
			AuthenticationUtil.setSessionCustomer(request, (Customer)returnData.getObject());
		return loginService.fbLogin(customer);
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
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody ReturnData logout(
			HttpServletRequest request, HttpServletResponse response){
		
		AuthenticationUtil.setSessionCustomer(request, null);
		return ReturnMessageEnum.Common.Success.getReturnMessage();
	}
}
