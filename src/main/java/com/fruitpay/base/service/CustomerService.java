package com.fruitpay.base.service;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.model.ReturnData;

public interface CustomerService {
	
	public ReturnData<Customer> update(Customer customer);
	
	public ReturnData<Boolean> isEmailExisted(String email);

}
