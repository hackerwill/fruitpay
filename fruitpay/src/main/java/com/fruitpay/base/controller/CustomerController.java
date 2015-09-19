package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;

@Controller
public class CustomerController {
	
	@Inject
	CustomerDAO customerDAO;
	
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.POST)
	public @ResponseBody List<Customer> getAllCustomer(){
		
		List<Customer> customers = customerDAO.listAll();
		return customers;
	}

}
