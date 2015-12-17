package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;
import com.fruitpay.comm.utils.Md5Util;
@CrossOrigin(origins = "http://localhost:8888", maxAge = 3600)
@Controller
@RequestMapping("customerDataCtrl")
public class CustomerDataController {
	
	@Inject
	CustomerOrderService customerOrderService;
	@Inject
	CustomerService customerService;
	@Inject
	AuthenticationUtil authenticationUtil;
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Customer update(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		
		if(AssertUtils.isEmpty(customer) || 
				AssertUtils.isEmpty(customer.getCustomerId()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		Customer returnData = customerService.update(customer);
		return returnData;
	}
	
	@RequestMapping(value = "/{customerId}/getOrder", method = RequestMethod.GET)
	public @ResponseBody List<CustomerOrder> getOrder(
			@PathVariable int customerId, 
			HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(customerId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		List<CustomerOrder> customerOrders = 
				customerOrderService.getCustomerOrdersByCustomerId(customerId);
		
		return customerOrders;
	}
	
	@RequestMapping(value = "/isEmailExisted/{email}/", method = RequestMethod.GET)
	public @ResponseBody Boolean isEmailExisted(@PathVariable String email){
		if(AssertUtils.anyIsEmpty(email))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		return customerService.isEmailExisted(email.trim());
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public @ResponseBody List<Customer> getAllCustomer(){
		List<Customer> customers = customerService.findAllCustomer();
		return customers;
	}
	
	@RequestMapping(value = "/customers/id", method = RequestMethod.POST)
	public @ResponseBody Customer getCustomerByCustomerId(@RequestBody Customer customer){
		customer = customerService.findCustomer(customer.getCustomerId());
		return customer;
	}
	
	@RequestMapping(value = "/customers/email", method = RequestMethod.POST)
	public @ResponseBody Customer getCustomerByEmail(@RequestBody Customer customer){
		
		customer = customerService.findByEmail(customer.getEmail());
		return customer;
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public @ResponseBody Customer addCustomer(@RequestBody Customer customer){
		
		//密碼加密
		customer.setPassword(Md5Util.getMd5(customer.getPassword())); 
		customer = customerService.saveCustomer(customer);
		return customer;
	}
	
}
