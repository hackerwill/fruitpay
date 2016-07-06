package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.comm.utils.DateUtil;

public class ShipmentChangeCondition implements Serializable {
	
	private Date deliverStartDate;
	private Date deliverEndDate;
	private Date updateStartDate;
	private Date updateEndDate;
	private String validFlag;
	private String orderId;
	private String name;
	private String receiverCellphone;
	private String shipmentChangeType;
	
	public ShipmentChangeCondition(String shipmentChangeType, Date deliverStartDate, Date deliverEndDate, 
			Date updateStartDate, Date updateEndDate, String validFlag, String orderId, String name, String receiverCellphone) {
		super();
		
		if(deliverStartDate == null){
			deliverStartDate = new Date(0L);
		}
		if(updateStartDate == null){
			updateStartDate = new Date(0L);
		}
		
		if(deliverEndDate == null) deliverEndDate = DateUtil.getMaxDate();
		if(updateEndDate == null) updateEndDate = DateUtil.getMaxDate();
		
		this.deliverStartDate = deliverStartDate;
		this.deliverEndDate = deliverEndDate;
		this.updateStartDate = updateStartDate;
		this.updateEndDate = updateEndDate;
		this.validFlag = validFlag != null ? validFlag : String.valueOf(VALID_FLAG.VALID.value());
		this.orderId = orderId;
		this.name = name;
		this.receiverCellphone = receiverCellphone;
		this.shipmentChangeType = shipmentChangeType;
	}

	public String getShipmentChangeType() {
		return shipmentChangeType;
	}

	public void setShipmentChangeType(String shipmentChangeType) {
		this.shipmentChangeType = shipmentChangeType;
	}

	public Date getDeliverStartDate() {
		return deliverStartDate;
	}

	public void setDeliverStartDate(Date deliverStartDate) {
		this.deliverStartDate = deliverStartDate;
	}

	public Date getDeliverEndDate() {
		return deliverEndDate;
	}

	public void setDeliverEndDate(Date deliverEndDate) {
		this.deliverEndDate = deliverEndDate;
	}

	public Date getUpdateStartDate() {
		return updateStartDate;
	}

	public void setUpdateStartDate(Date updateStartDate) {
		this.updateStartDate = updateStartDate;
	}

	public Date getUpdateEndDate() {
		return updateEndDate;
	}

	public void setUpdateEndDate(Date updateEndDate) {
		this.updateEndDate = updateEndDate;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
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

	public String getReceiverCellphone() {
		return receiverCellphone;
	}

	public void setReceiverCellphone(String receiverCellphone) {
		this.receiverCellphone = receiverCellphone;
	}
}
