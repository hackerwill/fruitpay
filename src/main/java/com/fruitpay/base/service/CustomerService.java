package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.model.ReturnData;

public interface CustomerService {
	
	public ReturnData<Customer> update(Customer customer);
	
	public ReturnData<Boolean> isEmailExisted(String email);
	
	public ReturnData<List<Customer>> findAllCustomer();
	
	public ReturnData<Customer> findCustomer(int customerId);
	
	public ReturnData<Customer> findByEmail(String email);
	
	public ReturnData<Customer> saveCustomer(Customer customer);
	
	public ReturnData<Customer> updateCustomer(Customer customer);

}
