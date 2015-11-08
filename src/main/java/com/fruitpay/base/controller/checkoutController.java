package com.fruitpay.base.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.ReturnData;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody ReturnData checkout(
			@RequestBody Customer customer, @RequestBody CustomerOrder customerOrder){
		logger.debug(customer);
		logger.debug(customer.getVillage().getVillageCode());
		logger.debug(customer.getVillage().getCustomers());
		//logger.debug(customerOrder);
		return null;
	}

}
