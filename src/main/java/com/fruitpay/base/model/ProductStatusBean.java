package com.fruitpay.base.model;

import java.io.Serializable;

public class ProductStatusBean implements Serializable {

	public enum STATUS{
		CHOOSE("1"),
		NO("0"),
		UNLIKE("X");
		
		private String status;
		
		private STATUS(String status) {
			this.status = status;
		}
		
		public String value() {
			return this.status;
		}
		
		@Override
		public String toString() {
			return this.status;
		}
	}
	
	private int shipmentRecordId;
	private String status;
	
	
	public int getShipmentRecordId() {
		return shipmentRecordId;
	}
	public void setShipmentRecordId(int shipmentRecordId) {
		this.shipmentRecordId = shipmentRecordId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
