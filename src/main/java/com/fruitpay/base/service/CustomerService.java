package com.fruitpay.base.service;

import org.springframework.data.domain.Page;

import com.fruitpay.base.model.Customer;

public interface CustomerService {
	
	public Customer update(Customer customer);
	
	public Boolean isEmailExisted(String email);
	
	public Page<Customer> findAllCustomer(int page , int size);
	
	public Customer findCustomer(int customerId);
	
	public Customer findByEmail(String email);
	
	public Customer saveCustomer(Customer customer);
	
	public void deleteCustomer(Customer customer);

}
