package com.fruitpay.base.controller;

import java.util.Iterator;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	@Inject
	private LoginService loginService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody ReturnData checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		
		if(customer == null || customerOrder == null)
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		logger.debug("add a customer, email is " + customer.getEmail());
		ReturnData<Customer> returnData = loginService.signup(customer);
		if(!"0".equals(returnData.getErrorCode()))
			return returnData;
		
		customer = returnData.getObject();
		logger.debug("customer Id : " + customer.getCustomerId());
		customerOrder.setCustomer(customer);
		customerOrder = checkoutService.checkoutOrder(customerOrder);
		
		logger.debug("order Id : " + customerOrder.getOrderId());
		return new ReturnObject<Integer>(ReturnMessageEnum.Common.Success.getReturnMessage(), 
				customerOrder.getOrderId());
	}

}
