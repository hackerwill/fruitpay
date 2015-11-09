package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ShipmentPeriod database table.
 * 
 */
@Entity
@NamedQuery(name="ShipmentPeriod.findAll", query="SELECT s FROM ShipmentPeriod s")
public class ShipmentPeriod extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="period_id")
	private int periodId;

	@Column(name="period_desc")
	private String periodDesc;

	@Column(name="period_name")
	private String periodName;
	
	@Column(name="duration")
	private Integer duration;

	//bi-directional many-to-one association to CustomerOrder
	@OneToMany(mappedBy="shipmentPeriod")
	private List<OrderProgram> orderPrograms;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="shipmentPeriod")
	private List<Shipment> shipments;

	public ShipmentPeriod() {
	}

	public int getPeriodId() {
		return this.periodId;
	}

	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}

	public String getPeriodDesc() {
		return this.periodDesc;
	}

	public void setPeriodDesc(String periodDesc) {
		this.periodDesc = periodDesc;
	}

	public String getPeriodName() {
		return this.periodName;
	}

	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	public List<OrderProgram> getOrderPrograms() {
		return this.orderPrograms;
	}

	public void setOrderPrograms(List<OrderProgram> orderPrograms) {
		this.orderPrograms = orderPrograms;
	}

	public OrderProgram addOrderProgram(OrderProgram orderProgram) {
		getOrderPrograms().add(orderProgram);
		orderProgram.setShipmentPeriod(this);

		return orderProgram;
	}

	public OrderProgram removeOrderProgram(OrderProgram orderProgram) {
		getOrderPrograms().remove(orderProgram);
		orderProgram.setShipmentPeriod(null);

		return orderProgram;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setShipmentPeriod(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setShipmentPeriod(null);

		return shipment;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}