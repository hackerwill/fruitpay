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
public class ShipmentChange extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
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
	
	@Column(name="valid_flag")
	private Integer validFlag;
	
	@Column(name="reason")
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="status", nullable=true)
	@JsonProperty("status")
	private ConstantOption status;

	public ConstantOption getStatus() {
		return status;
	}

	public void setStatus(ConstantOption status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
