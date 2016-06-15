package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.comm.utils.DateUtil;

public class ShipmentChangeCondition implements Serializable {
	
	private Date startDate;
	private Date endDate;
	private String validFlag;
	private String orderId;
	private String name;
	private String receiverCellphone;
	
	public ShipmentChangeCondition(Date startDate, Date endDate, String validFlag, String orderId, String name, String receiverCellphone) {
		super();
		
		if(startDate == null){
			startDate = new Date(0L);
		}
		
		if(endDate == null) endDate = DateUtil.getMaxDate();
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.validFlag = validFlag != null ? validFlag : String.valueOf(VALID_FLAG.VALID.value());
		this.orderId = orderId;
		this.name = name;
		this.receiverCellphone = receiverCellphone;
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
