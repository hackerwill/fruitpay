package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderService {

	public CustomerOrder getCustomerOrder(Integer orderId);
	
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId);

}
