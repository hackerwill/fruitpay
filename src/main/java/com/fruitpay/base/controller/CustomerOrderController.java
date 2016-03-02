package com.fruitpay.base.controller;


import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.UserAuthStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.auth.UserAccessAnnotation;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.RadomValueUtil;

@Controller
@RequestMapping("orderCtrl")
public class CustomerOrderController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder addOrder(
			@RequestBody CheckoutPostBean checkoutPostBean){
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		Customer customer = checkoutPostBean.getCustomer();

		customerOrder.setCustomer(customer);
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(DayOfWeek.TUESDAY.getValue()));
		
		if(customer == null || customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			orderPreference.setCustomerOrder(customerOrder);
		}
		customerOrder = customerOrderService.addCustomerOrder(customerOrder);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.PUT)
	public @ResponseBody CustomerOrder updateOrder(
			@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
	
		customerOrder = customerOrderService.updateCustomerOrder(customerOrder);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	@UserAccessAnnotation(UserAuthStatus.YES)
	public @ResponseBody CustomerOrder getOrder(@PathVariable Integer orderId){
		
		if(AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
	
		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(orderId);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.DELETE)
	public @ResponseBody CustomerOrder deleteOrder(
			@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null || AssertUtils.isEmpty(customerOrder.getOrderId()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
	
		customerOrderService.deleteOrder(customerOrder);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	@UserAccessAnnotation(UserAuthStatus.ADMIN)
	public @ResponseBody Page<CustomerOrder> orders(			
			@RequestParam(value = "page", required = false, defaultValue = "0") int page ,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size ){
	
		Page<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrder(page , size);
		
		return customerOrders;
	}


}
