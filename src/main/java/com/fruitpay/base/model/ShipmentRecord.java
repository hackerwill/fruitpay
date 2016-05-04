package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="ShipmentRecord.findAll", query="SELECT r FROM ShipmentRecord r")
public class ShipmentRecord extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shipment_record_id")
	private Integer shipmentRecordId;
	
	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;
	
	@Column(name="date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="shipment_type")
	@JsonProperty("shipmentType")
	private ConstantOption shipmentType;
	
	@Column(name="valid_flag")
	private Integer validFlag;

	public Integer getShipmentRecordId() {
		return shipmentRecordId;
	}

	public void setShipmentRecordId(Integer shipmentRecordId) {
		this.shipmentRecordId = shipmentRecordId;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ConstantOption getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(ConstantOption shipmentType) {
		this.shipmentType = shipmentType;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}
	
}
