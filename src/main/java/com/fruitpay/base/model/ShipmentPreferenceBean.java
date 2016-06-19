package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShipmentPreferenceBean implements Serializable {
	
	private int shipmentRecordId;
	private Date date;
	private List<ShipmentInfoBean> shipmentInfoBeans;
	private List<ChosenProductBean> chosenProductBeans;
	
	
	public ShipmentPreferenceBean(int shipmentRecordId, Date date, List<ShipmentInfoBean> shipmentInfoBeans,
			List<ChosenProductBean> chosenProductBeans) {
		super();
		this.shipmentRecordId = shipmentRecordId;
		this.date = date;
		this.shipmentInfoBeans = shipmentInfoBeans;
		this.chosenProductBeans = chosenProductBeans;
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
	public List<ShipmentInfoBean> getShipmentInfoBeans() {
		return shipmentInfoBeans;
	}
	public List<ChosenProductBean> getChosenProductBeans() {
		return chosenProductBeans;
	}
	public void setChosenProductBeans(List<ChosenProductBean> chosenProductBeans) {
		this.chosenProductBeans = chosenProductBeans;
	}
	
	

}
