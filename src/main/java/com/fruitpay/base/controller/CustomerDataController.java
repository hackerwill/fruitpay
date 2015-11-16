package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;

@Controller
@RequestMapping("CustomerDataCtrl")
public class CustomerDataController {
	
	@Inject
	CustomerOrderService customerOrderService;
	
	@RequestMapping(value = "/{customerId}/getOrder", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrder(
			@PathVariable int customerId, 
			HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(customerId))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		Customer customer = AuthenticationUtil.getSessionCustomer(request, customerId);
		if(customer == null)
			return ReturnMessageEnum.Common.AuthenticationFailed.getReturnMessage();
		
		ReturnData<List<CustomerOrder>> customerOrders = 
				customerOrderService.getCustomerOrdersByCustomerId(customerId);
		
		return customerOrders;
	}
	
}
