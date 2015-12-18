package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderService {

	public CustomerOrder getCustomerOrder(Integer orderId);
	
	public CustomerOrder updateCustomerOrder(CustomerOrder customerOrder);
	
	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder);
	
	public List<CustomerOrder> getAllCustomerOrder();
	
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId);

}
