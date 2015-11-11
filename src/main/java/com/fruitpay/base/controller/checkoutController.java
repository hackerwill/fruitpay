package com.fruitpay.base.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;

import AllPay.Payment.Integration.AllInOne;
import AllPay.Payment.Integration.Decimal;
import AllPay.Payment.Integration.DeviceType;
import AllPay.Payment.Integration.ExtraPaymentInfo;
import AllPay.Payment.Integration.HttpMethod;
import AllPay.Payment.Integration.Item;
import AllPay.Payment.Integration.PaymentMethod;
import AllPay.Payment.Integration.PaymentMethodItem;
import AllPay.Payment.Integration.PeriodType;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody ReturnData checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		logger.debug(customer);
		logger.debug(customerOrder);
		
		if(customer == null || customerOrder == null)
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		customerOrder = checkoutService.checkoutOrder(customer, customerOrder);
		
		logger.debug("order Id : " + customerOrder.getOrderId());
		return new ReturnObject<Integer>(ReturnMessageEnum.Common.Success.getReturnMessage(), 
				customerOrder.getOrderId());
	}

}
