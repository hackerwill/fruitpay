package com.fruitpay.base.model;

import java.io.Serializable;

public class StatusInteger implements Serializable {
	
	public enum Status{
		fixed,
		none;
	}
	
	private String status;
	private Integer integer;
	
	public StatusInteger() {
		super();
	}
	
	public StatusInteger(String status, Integer integer) {
		super();
		this.status = status;
		this.integer = integer;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getInteger() {
		return integer;
	}
	public void setInteger(Integer integer) {
		this.integer = integer;
	}

}
