package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.comm.utils.DateUtil;

public class ShipmentChangeCondition implements Serializable {
	
	private Date startDate;
	private Date endDate;
	private String validFlag;
	
	public ShipmentChangeCondition(Date startDate, Date endDate) {
		super();
		
		if(startDate == null){
			startDate = new Date(0L);
		}
		
		if(endDate == null) endDate = DateUtil.getMaxDate();
		
		this.startDate = startDate;
		this.endDate = endDate;
		this.validFlag = String.valueOf(VALID_FLAG.VALID.value());
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
}
