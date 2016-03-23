package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

public class OrderCondition implements Serializable {
	
	private String orderId;
	private String name;
	private Date startDate;
	private Date endDate;

	public OrderCondition(String orderId, String name, Date startDate, Date endDate) {
		super();
		
		if(startDate == null){
			startDate = new Date(Long.MIN_VALUE);
		}
		
		if(endDate == null){
			endDate = new Date(Long.MAX_VALUE);
		}
		
		this.orderId = orderId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
