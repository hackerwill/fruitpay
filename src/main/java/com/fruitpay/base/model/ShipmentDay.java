package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the ShipmentDays database table.
 * 
 */
@Entity
@Table(name="ShipmentDays")
@NamedQuery(name="ShipmentDay.findAll", query="SELECT s FROM ShipmentDay s")
public class ShipmentDay extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shipment_days_id")
	private int shipmentDaysId;

	@Column(name="shipment_days_desc")
	private String shipmentDaysDesc;

	@Column(name="shipment_days_name")
	private String shipmentDaysName;

	public ShipmentDay() {
	}

	public int getShipmentDaysId() {
		return this.shipmentDaysId;
	}

	public void setShipmentDaysId(int shipmentDaysId) {
		this.shipmentDaysId = shipmentDaysId;
	}

	public String getShipmentDaysDesc() {
		return this.shipmentDaysDesc;
	}

	public void setShipmentDaysDesc(String shipmentDaysDesc) {
		this.shipmentDaysDesc = shipmentDaysDesc;
	}

	public String getShipmentDaysName() {
		return this.shipmentDaysName;
	}

	public void setShipmentDaysName(String shipmentDaysName) {
		this.shipmentDaysName = shipmentDaysName;
	}
}