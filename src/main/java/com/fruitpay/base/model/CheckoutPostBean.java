package com.fruitpay.base.model;

import java.io.Serializable;

public class CheckoutPostBean implements Serializable {
	
	private Customer customer;
	private CustomerOrder customerOrder;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}
	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}
}
