package com.fruitpay.base.model;

import java.io.Serializable;

public class ShipmentInfoBean implements Serializable {

	private int orderId;
	private int shipmentRecordId;
	private String name;
	private int actualNum;
	private Integer requiredAmount;
	
	public ShipmentInfoBean(int orderId, int shipmentRecordId, String name, int actualNum,
			Integer requiredAmount) {
		super();
		this.orderId = orderId;
		this.shipmentRecordId = shipmentRecordId;
		this.name = name;
		this.actualNum = actualNum;
		this.requiredAmount = requiredAmount;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getShipmentRecordId() {
		return shipmentRecordId;
	}
	public void setShipmentRecordId(int shipmentRecordId) {
		this.shipmentRecordId = shipmentRecordId;
	}

	public int getActualNum() {
		return actualNum;
	}

	public void setActualNum(int actualNum) {
		this.actualNum = actualNum;
	}

	public Integer getRequiredAmount() {
		return requiredAmount;
	}

	public void setRequiredAmount(Integer requiredAmount) {
		this.requiredAmount = requiredAmount;
	}

}
