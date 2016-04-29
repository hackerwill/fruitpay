package com.fruitpay.base.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fruitpay.comm.utils.DateUtil;

public class OrderCondition implements Serializable {
	
	private String orderId;
	private String name;
	private Date startDate;
	private Date endDate;
	private String validFlag;
	private String allowForeignFruits;
	private String orderStatusId;

	public OrderCondition(String orderId, String name, Date startDate, Date endDate, String validFlag, String allowForeignFruits, String orderStatusId) {
		super();
		
		if(startDate == null){
			startDate = new Date(0L);
		}
		
		if(endDate == null) endDate = DateUtil.getMaxDate();
		
		this.orderId = orderId;
		this.name = name.toLowerCase();
		this.startDate = startDate;
		this.endDate = endDate;
		this.validFlag = validFlag;
		this.allowForeignFruits = allowForeignFruits;
		this.orderStatusId = orderStatusId;
		
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

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getAllowForeignFruits() {
		return allowForeignFruits;
	}

	public void setAllowForeignFruits(String allowForeignFruits) {
		this.allowForeignFruits = allowForeignFruits;
	}

	public String getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(String orderStatusId) {
		this.orderStatusId = orderStatusId;
	}
}
