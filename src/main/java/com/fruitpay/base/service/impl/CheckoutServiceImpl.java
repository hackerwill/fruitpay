package com.fruitpay.base.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.service.CheckoutService;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private CustomerDAO customerDAO;
	@Inject
	private OrderProgramDAO orderProgramDAO;
	
	@Override
	@Transactional
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder) {
		logger.debug("add a customer, email is " + customer.getEmail());
		customer = customerDAO.create(customer);
		
		logger.debug("add a customerOrder, email is " + customer.getEmail());
		customerOrder.setCustomer(customer);
		customerOrder = customerOrderDAO.create(customerOrder);
		
		OrderProgram orderProgram = orderProgramDAO.findById(customerOrder.getOrderProgram().getProgramId());
		customerOrder.setOrderProgram(orderProgram);
		
		return customerOrder;
	}

	@Override
	public CustomerOrder getCustomerOrder(Integer orderId) {
		return customerOrderDAO.findById(orderId);
	}

}
