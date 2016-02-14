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
	
	public Customer findByOrderId(Integer orderId);
	
	/**
	 * 得到所有顧客的名稱字串
	 * 格式為 姓名(顧客ID), 姓名(顧客ID)
	 * 如"王大明(101), 陳志朋(102)"  
	 * */
	public String getCustomerNamesStr();

}
