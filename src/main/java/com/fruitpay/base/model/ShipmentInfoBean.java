package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

public class ShipmentInfoBean implements Serializable {

	private int orderId;
	private int shipmentRecordId;
	private String name;
	private int actualNum;
	private Integer requiredAmount;
	private String errorStatus;
	private List<OrderPreference> orderPreferences;
	private String allowForeignFruits;
	private int programId;
	
	public ShipmentInfoBean() {
		
	}
	
	public ShipmentInfoBean(int orderId, int shipmentRecordId, String name, int actualNum, Integer requiredAmount, 
			List<OrderPreference> orderPreferences, String allowForeignFruits, int programId) {
		super();
		this.orderId = orderId;
		this.shipmentRecordId = shipmentRecordId;
		this.name = name;
		this.actualNum = actualNum;
		this.requiredAmount = requiredAmount;
		this.orderPreferences = orderPreferences;
		this.allowForeignFruits = allowForeignFruits;
		this.programId = programId;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getAllowForeignFruits() {
		return allowForeignFruits;
	}
	public void setAllowForeignFruits(String allowForeignFruits) {
		this.allowForeignFruits = allowForeignFruits;
	}
	public List<OrderPreference> getOrderPreferences() {
		return orderPreferences;
	}

	public void setOrderPreferences(List<OrderPreference> orderPreferences) {
		this.orderPreferences = orderPreferences;
	}

	public String getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
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
