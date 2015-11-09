package com.fruitpay.base.controller;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.comm.model.ReturnData;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody ReturnData checkout(
			@RequestBody CheckoutPostBean checkoutPostBean){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		logger.debug(customer);
		logger.debug(customerOrder);
		logger.info(1111);;
		customerOrder = checkoutService.checkoutOrder(customer, customerOrder);
		ShipmentDay sss = customerOrder.getShipmentDay();
		logger.debug(sss);
		//logger.debug(customerOrder);
		return null;
	}

}
