package com.fruitpay.comm.model;

import java.io.Serializable;

import com.fruitpay.backend.model.Manager;
import com.fruitpay.base.model.Customer;

public class Role implements Serializable {
	
	private String charactor;
	private String userId;
	private String userName;
	
	public Role(String charactor, String userId, String userName) {
		super();
		this.charactor = charactor;
		this.userId = userId;
		this.userName = userName;
	}
	
	public Role(Customer customer){
		super();
		this.charactor = "customer";
		this.userId = String.valueOf(customer.getCustomerId());
		this.userName = customer.getFirstName();
	}
	
	public Role(Manager manager){
		super();
		this.charactor = "manager";
		this.userId = manager.getManagerId();
		this.userName = manager.getManagerId();
	}
	
	public String getCharactor() {
		return charactor;
	}
	public void setCharactor(String charactor) {
		this.charactor = charactor;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
