package com.fruitpay.base.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionFactory;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.RadomValueUtil;
import com.fruitpay.comm.utils.StringUtil;

@Controller
@RequestMapping("loginCtrl")
public class LoginController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private LoginService loginService;
	@Inject
	private EmailSendService emailSendService;
	
	@RequestMapping(value = "/match", method = RequestMethod.POST)
	public @ResponseBody Boolean checkMatch(@RequestBody Customer customer) {
		if (AssertUtils.anyIsEmpty(customer.getEmail(), customer.getPassword()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		Customer returnCustomer = loginService.login(customer.getEmail(), customer.getPassword());
		if(returnCustomer != null){
			return true;
		}else{
			return false;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Customer loginAsOneRCustomer(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("LoginController#loginAsOneCustomer email: " + customer.getEmail());	
		Customer returnCustomer = null;
		try {
			if (AssertUtils.anyIsEmpty(customer.getEmail(), customer.getPassword()))
				throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
			returnCustomer = loginService.login(customer.getEmail(), customer.getPassword());			
			/*** normal login: create user token ***/
			if (returnCustomer != null) {
				try {
					String token = FPSessionUtil.logonGetToken(returnCustomer, request, LoginConst.NORMAL);
					returnCustomer.setToken(token);
				} catch (Exception e) {
					logger.error("login error when FPSessionUtil.logonGetSession: " + e);
				}
			}
		} catch (HttpServiceException e) {
			logger.error("login error when LoginController: " + e);
			throw e;
		}
		return returnCustomer;
	}

	@RequestMapping(value = "/loginById", method = RequestMethod.POST)
	public @ResponseBody Customer  loginByIdAsOneCustomer(@RequestBody Customer customer, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("LoginController#loginAsOneCustomer customerId: " + customer.getCustomerId());		
		Customer returnCustomer =null;
		try {
			boolean validate = false;
			try {
				validate = FPSessionUtil.getInfoAndVlidateToken(customer, request, LoginConst.LOGINBYID);
			} catch (Exception e) {
				logger.error("login error when FPSessionUtil.logonGetSession: " + e);
			}
			
			if(!validate)
				throw new HttpServiceException(ReturnMessageEnum.Login.RequiredLogin.getReturnMessage()); 
			
			if (AssertUtils.anyIsEmpty(String.valueOf(customer.getCustomerId()))) {
				throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
			}	
			returnCustomer = loginService.loginByCustomerId(customer.getCustomerId());			
			
		} catch (HttpServiceException e) {
			logger.error("login error when LoginController: " + e);
			throw e;
		}
		return returnCustomer;
	}

	@RequestMapping(value = "/fbLogin", method = RequestMethod.POST)
	public @ResponseBody Customer fbLoginAsOneCustomer(@RequestBody Customer customer, HttpServletRequest request,
			HttpServletResponse response) {

		logger.debug("LoginController#fbLoginAsOneCustomer fbId=" + customer.getFbId());
		if (AssertUtils.anyIsEmpty(customer.getFbId(), customer.getFirstName())) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}
		Customer returnCustomer = loginService.fbLogin(customer);
		if (returnCustomer != null) {
			try {
				String token = FPSessionUtil.logonGetToken(returnCustomer, request, LoginConst.NORMAL);
				returnCustomer.setToken(token);
			} catch (Exception e) {
				logger.error("login error when FPSessionUtil.logonGetSession: " + e);
			}
		}
		return returnCustomer;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody Customer SignupAsOneCustomer(@RequestBody Customer customer) {

		if (AssertUtils.anyIsEmpty(customer.getEmail(), customer.getPassword(), customer.getFirstName(),
				customer.getLastName())) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}

		customer = loginService.signup(customer);
		emailSendService.sendTo(MailType.NEW_MEMBER, customer.getEmail(), customer);
		return customer;

	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody boolean logout(HttpServletRequest request, HttpServletResponse response) {
		/*** get user token from Header **/
		String FPToken = FPSessionUtil.getHeader(request, LoginConst.LOGIN_AUTHORIZATION);	
        boolean cleanSessionStatus= false;
		if (!StringUtil.isEmptyOrSpace(FPToken)) {
			try {				
				logger.debug("FPToken==[" + FPToken + "]");
				logger.debug("=======================FP Session Logout Start=======================");
				FPSessionFactory.getInstance().getFPSessionMap().remove(FPToken);
				cleanSessionStatus = true;
				logger.debug("=======================FP Session Logout End=======================");
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

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody Customer changePassword(@RequestBody Pwd pwd, HttpServletRequest request,
			HttpServletResponse response) {

		if (AssertUtils.anyIsEmpty(String.valueOf(pwd.getCustomerId()), pwd.getOldPassword(), pwd.getNewPassword())) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}

		Customer customer = loginService.changePassword(pwd);

		return customer;
	}
	
	@RequestMapping(value = "/validateToken", method = RequestMethod.POST)
	public @ResponseBody Boolean validateToken(@RequestBody Customer customer, HttpServletRequest request,
			HttpServletResponse response) {
		boolean validate = false;
		try {
			validate = FPSessionUtil.getInfoAndVlidateToken(customer, request, LoginConst.LOGINBYID);
		} catch (Exception e) {
			logger.debug("LoginController.validateToken failed");
		}
		return validate;
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public @ResponseBody String forgetPassword(@RequestBody Customer customer) {
		String email = customer.getEmail();
		if (AssertUtils.isEmpty(String.valueOf(email))) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}

		String randomPassword = RadomValueUtil.getRandomPassword();

		customer = loginService.forgetPassword(email, randomPassword);

		Customer sendCustomer = new Customer();
		BeanUtils.copyProperties(customer, sendCustomer);
		sendCustomer.setPassword(randomPassword);
		emailSendService.sendTo(MailType.FORGET_PASSWORD, sendCustomer.getEmail(), sendCustomer);

		return "true";
	}

}
