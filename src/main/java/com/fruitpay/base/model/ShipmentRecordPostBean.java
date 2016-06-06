package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

public class ShipmentRecordPostBean implements Serializable {
	
	private ShipmentRecord shipmentRecord;
	private List<Integer> orderIds;
	public ShipmentRecord getShipmentRecord() {
		return shipmentRecord;
	}
	public void setShipmentRecord(ShipmentRecord shipmentRecord) {
		this.shipmentRecord = shipmentRecord;
	}
	public List<Integer> getOrderIds() {
		return orderIds;
	}
	public void setOrderIds(List<Integer> orderIds) {
		this.orderIds = orderIds;
	}

}
