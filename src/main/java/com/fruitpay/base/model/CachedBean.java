package com.fruitpay.base.model;

import java.util.Date;

public class CachedBean<V> {
	
	private V value;
	
	private Date date;
	
	public CachedBean() {
		super();
	}
	
	public CachedBean(V value) {
		super();
		this.value = value;
		this.date = new Date();
	}
	
	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
