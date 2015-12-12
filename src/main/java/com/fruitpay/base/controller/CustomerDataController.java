package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;
import com.fruitpay.comm.utils.Md5Util;

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
	public @ResponseBody ReturnData<Customer> update(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		
		if(AssertUtils.isEmpty(customer) || 
				AssertUtils.isEmpty(customer.getCustomerId()))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		ReturnData<Customer> returnData = customerService.update(customer);
		//if(returnData.getObject() != null)
		//	authenticationUtil.setSessionCustomer(request, returnData.getObject().getCustomerId());
		return returnData;
	}
	
	@RequestMapping(value = "/{customerId}/getOrder", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrder(
			@PathVariable int customerId, 
			HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(customerId))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		ReturnData<List<CustomerOrder>> customerOrders = 
				customerOrderService.getCustomerOrdersByCustomerId(customerId);
		
		return customerOrders;
	}
	
	@RequestMapping(value = "/isEmailExisted/{email}/", method = RequestMethod.GET)
	public @ResponseBody ReturnData<Boolean> isEmailExisted(@PathVariable String email){
		if(AssertUtils.anyIsEmpty(email))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		return customerService.isEmailExisted(email.trim());
	}
	
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public @ResponseBody ReturnData<List<Customer>> getAllCustomer(){
		ReturnData<List<Customer>> returnObject = customerService.findAllCustomer();
		return returnObject;
	}
	
	@RequestMapping(value = "/customers/id", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> getCustomerByCustomerId(@RequestBody Customer customer){
		ReturnData<Customer> returnObject = customerService.findCustomer(customer.getCustomerId());
		return returnObject;
	}
	
	@RequestMapping(value = "/customers/email", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> getCustomerByEmail(@RequestBody Customer customer){
		
		ReturnData<Customer> returnObject = customerService.findByEmail(customer.getEmail());
		return returnObject;
	}
	
	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> addCustomer(@RequestBody Customer customer){
		
		//密碼加密
		customer.setPassword(Md5Util.getMd5(customer.getPassword()));
		 
		ReturnData<Customer> returnObject = customerService.saveCustomer(customer);
		return returnObject;
	}
	
}
