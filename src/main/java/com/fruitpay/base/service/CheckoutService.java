package com.fruitpay.base.service;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

public interface CheckoutService {
	
	/**
	 * 註冊顧客並且新增訂單
	 * 
	 * */
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder);

	public CustomerOrder getCustomerOrder(Integer orderId);
}
