package com.fruitpay.base.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderCondition implements Serializable {
	
	private String orderId;
	private String name;
	private Date startDate;
	private Date endDate;
	private String validFlag;
	private String allowForeignFruits;

	public OrderCondition(String orderId, String name, Date startDate, Date endDate, String validFlag, String allowForeignFruits) {
		super();
		
		if(startDate == null){
			startDate = new Date(0L);
		}
		
		if(endDate == null){
			// only to make sure it's the maxima date
			Date date = null;
			String string = "3000-01-01";
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = format.parse(string);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endDate = date;
		}
		
		this.orderId = orderId;
		this.name = name.toLowerCase();
		this.startDate = startDate;
		this.endDate = endDate;
		this.validFlag = validFlag;
		this.allowForeignFruits = allowForeignFruits;
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
}
