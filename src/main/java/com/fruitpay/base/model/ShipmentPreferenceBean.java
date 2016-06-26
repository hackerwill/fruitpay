package com.fruitpay.base.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ShipmentPreferenceBean implements Serializable {
	
	private int shipmentRecordId;
	private Date date;
	private List<ShipmentInfoBean> shipmentInfoBeans;
	private List<ChosenProductItemBean> chosenProductItemBeans;
	
	public ShipmentPreferenceBean() {
		super();
	}
	
	public ShipmentPreferenceBean(int shipmentRecordId, Date date, List<ShipmentInfoBean> shipmentInfoBeans,
			List<ChosenProductItemBean> chosenProductItemBeans) {
		super();
		this.shipmentRecordId = shipmentRecordId;
		this.date = date;
		this.shipmentInfoBeans = shipmentInfoBeans;
		this.chosenProductItemBeans = chosenProductItemBeans;
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

	public List<ChosenProductItemBean> getChosenProductItemBeans() {
		return chosenProductItemBeans;
	}

	public void setChosenProductItemBeans(List<ChosenProductItemBean> chosenProductItemBeans) {
		this.chosenProductItemBeans = chosenProductItemBeans;
	}
	
	/**
	 * This method makes a "deep clone" of any Java object it is given.
	 */
	 public static Object deepClone(Object object) {
	   try {
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     ObjectOutputStream oos = new ObjectOutputStream(baos);
	     oos.writeObject(object);
	     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
	     ObjectInputStream ois = new ObjectInputStream(bais);
	     return ois.readObject();
	   }
	   catch (Exception e) {
	     e.printStackTrace();
	     return null;
	   }
	 }
}
