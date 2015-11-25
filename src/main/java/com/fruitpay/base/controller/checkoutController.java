package com.fruitpay.base.controller;


import java.util.Calendar;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.ShipmentDay;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.utils.RadomValueUtil;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	@Inject
	private StaticDataService staticDataService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody ReturnData<CustomerOrder> checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		
		customerOrder.setOrderDate(Calendar.getInstance().getTime());
		customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreadyCheckout.getStatus()));
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(ShipmentDay.Tuesday.getDay()));
		
		if(customer == null || customerOrder == null)
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		ReturnData<CustomerOrder> returnData = checkoutService.checkoutOrder(customer, customerOrder);
		if(returnData.getErrorCode().equals(ReturnMessageEnum.Status.Failed.getStatus()))
			return returnData;
		
		logger.debug("order Id : " + customerOrder.getOrderId());
		return new ReturnObject<Integer>(ReturnMessageEnum.Common.Success.getReturnMessage(), 
				customerOrder.getOrderId());
	}

}
