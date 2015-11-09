package com.fruitpay.base.service;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

public interface CheckoutService {
	
	
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder);

}
