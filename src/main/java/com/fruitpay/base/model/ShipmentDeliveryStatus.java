package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

public class ShipmentDeliveryStatus implements Serializable {
	
	private Date applyDate;
	
	private ConstantOption shipmentChangeType;

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public ConstantOption getShipmentChangeType() {
		return shipmentChangeType;
	}

	public void setShipmentChangeType(ConstantOption shipmentChangeType) {
		this.shipmentChangeType = shipmentChangeType;
	}

}
