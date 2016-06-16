package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShipmentPreferenceBean implements Serializable {
	
	private int shipmentRecordId;
	private Date date;
	
	private List<ShipmentInfoBean> shipmentInfoBeans;
	
	private List<ChosenProductBean> chosenProductBean;

	public List<ShipmentInfoBean> getShipmentInfoBeans() {
		return shipmentInfoBeans;
	}
	
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getShipmentRecordId() {
		return shipmentRecordId;
	}
	public void setShipmentRecordId(int shipmentRecordId) {
		this.shipmentRecordId = shipmentRecordId;
	}
	public void setShipmentInfoBeans(List<ShipmentInfoBean> shipmentInfoBeans) {
		this.shipmentInfoBeans = shipmentInfoBeans;
	}

	public List<ChosenProductBean> getChosenProductBean() {
		return chosenProductBean;
	}

	public void setChosenProductBean(List<ChosenProductBean> chosenProductBean) {
		this.chosenProductBean = chosenProductBean;
	}
	
	

}
