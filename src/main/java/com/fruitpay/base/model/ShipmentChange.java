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
@NamedQuery(name="ShipmentChange.findAll", query="SELECT o FROM ShipmentChange o")
public class ShipmentChange extends AbstractDataBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;
	
	@Column(name="apply_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date applyDate;
	
	@ManyToOne
	@JoinColumn(name="shipment_change_type")
	@JsonProperty("shipmentChangeType")
	private ConstantOption shipmentChangeType;
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public ConstantOption getShipmentChangeType() {
		return shipmentChangeType;
	}

	public void setShipmentChangeType(ConstantOption shipmentExchangeType) {
		this.shipmentChangeType = shipmentExchangeType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
