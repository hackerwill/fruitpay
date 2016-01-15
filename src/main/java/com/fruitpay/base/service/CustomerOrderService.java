package com.fruitpay.base.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderService {

	public CustomerOrder getCustomerOrder(Integer orderId);
	
	public CustomerOrder updateCustomerOrder(CustomerOrder customerOrder);
	
	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder);
	
	public Page<CustomerOrder> getAllCustomerOrder(int page , int size);
	
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId);
	
	public void deleteOrder(CustomerOrder customerOrder);

}
