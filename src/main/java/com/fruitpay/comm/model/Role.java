package com.fruitpay.comm.model;

import java.io.Serializable;

public class Role implements Serializable {
	
	private String charactor;
	private String userId;
	
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
	
	

}
