package com.fruitpay.base.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;

@Service
public class CheckoutImpl implements CheckoutService {

	@Inject
	private CustomerOrderDAO customerOrderDAO;
	
	@Override
	@Transactional
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder) {
		customer.setCustomerId(2);
		customerOrder.setCustomer(customer);
		customerOrder = customerOrderDAO.create(customerOrder);
		return customerOrder;
	}

}
