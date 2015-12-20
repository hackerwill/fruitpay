package com.fruitpay.base.controller;


import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.Domain;
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.utils.RadomValueUtil;

@CrossOrigin(origins = {Domain.FRONTEND, Domain.BACKEND}, maxAge = 3600)
@Controller
@RequestMapping("orderCtrl")
public class CustomerOrderController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder addOrder(
			@RequestBody CheckoutPostBean checkoutPostBean){
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		Customer customer = checkoutPostBean.getCustomer();

		customerOrder.setCustomer(customer);
		customerOrder.setOrderDate(Calendar.getInstance().getTime());
		customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreadyCheckout.getStatus()));
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(DayOfWeek.TUESDAY.getValue()));
		
		if(customer == null || customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		String randomPassword = RadomValueUtil.getRandomPassword();
		customer.setPassword(randomPassword);
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			orderPreference.setCustomerOrder(customerOrder);
		}
		customerOrder = customerOrderService.addCustomerOrder(customerOrder);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/updateOrder", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder updateOrder(
			@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
	
		customerOrder = customerOrderService.updateCustomerOrder(customerOrder);
		
		return customerOrder;
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public @ResponseBody List<CustomerOrder> orders(){
	
		List<CustomerOrder> customerOrders = customerOrderService.getAllCustomerOrder();
		
		return customerOrders;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder getOrder(
			@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
	
		customerOrder = customerOrderService.getCustomerOrder(customerOrder.getOrderId());
		
		return customerOrder;
	}
	
	


}
