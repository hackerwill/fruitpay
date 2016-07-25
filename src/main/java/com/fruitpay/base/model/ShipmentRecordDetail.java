package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="ShipmentRecordDetail.findAll", query="SELECT r FROM ShipmentRecordDetail r")
public class ShipmentRecordDetail extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shipment_record_detail_id")
	private Integer shipmentRecordDetailId;
	
	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;
	
	@ManyToOne
	@JoinColumn(name="shipment_record_id")
	@JsonBackReference
	private ShipmentRecord shipmentRecord;
	
	@Column(name="valid_flag")
	private Integer validFlag;

	public Integer getShipmentRecordDetailId() {
		return shipmentRecordDetailId;
	}

	public void setShipmentRecordDetailId(Integer shipmentRecordDetailId) {
		this.shipmentRecordDetailId = shipmentRecordDetailId;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public ShipmentRecord getShipmentRecord() {
		return shipmentRecord;
	}

	public void setShipmentRecord(ShipmentRecord shipmentRecord) {
		this.shipmentRecord = shipmentRecord;
	}
	
}
