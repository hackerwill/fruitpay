package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Customer;

public interface CustomerService {
	
	public Customer update(Customer customer);
	
	public Boolean isEmailExisted(String email);
	
	public List<Customer> findAllCustomer();
	
	public Customer findCustomer(int customerId);
	
	public Customer findByEmail(String email);
	
	public Customer saveCustomer(Customer customer);
	
	public Customer updateCustomer(Customer customer);

}
