package com.fruitpay.base.controller;



import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum.Status;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;

@Controller
@RequestMapping("loginCtrl")
public class LoginController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private AuthenticationUtil authenticationUtil;
	
	@Inject
	private LoginService loginService;
	@Inject
	private EmailSendService emailSendService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> loginAsOneCustomer(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		logger.debug("LoginController#loginAsOneCustomer email: " + customer.getEmail());
		if(AssertUtils.anyIsEmpty(customer.getEmail(), customer.getPassword())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		}
		ReturnData<Customer> returnData = loginService.login(customer.getEmail(), customer.getPassword());
		return returnData;
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
		return returnData;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST )
	public @ResponseBody ReturnData SignupAsOneCustomer(@RequestBody Customer customer){
		
		if(AssertUtils.anyIsEmpty(
				customer.getEmail(), customer.getPassword(),
				customer.getFirstName(), customer.getLastName())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage(); 
		}
		
		ReturnData lrm = loginService.signup(customer);
		
		if(Status.Success.getStatus().equals(lrm.getErrorCode())){
			emailSendService.sendTo(MailType.NEW_MEMBER, customer.getEmail(), customer);	
		}
		
		return lrm;
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody ReturnData logout(
			HttpServletRequest request, HttpServletResponse response){
		
		return ReturnMessageEnum.Common.Success.getReturnMessage();
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> changePassword(@RequestBody Pwd pwd,
			HttpServletRequest request, HttpServletResponse response){
		
		if(AssertUtils.anyIsEmpty(
				String.valueOf(pwd.getCustomerId()), pwd.getOldPassword(), pwd.getNewPassword())){
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		}
		
		ReturnData<Customer> returnData = loginService.changePassword(pwd);
		
		return returnData;
	}

}
