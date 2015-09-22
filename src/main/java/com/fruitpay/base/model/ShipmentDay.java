package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ShipmentDays database table.
 * 
 */
@Entity
@Table(name="ShipmentDays")
@NamedQuery(name="ShipmentDay.findAll", query="SELECT s FROM ShipmentDay s")
public class ShipmentDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shipment_days_id")
	private int shipmentDaysId;

	@Column(name="shipment_days_desc")
	private String shipmentDaysDesc;

	@Column(name="shipment_days_name")
	private String shipmentDaysName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="shipmentDay")
	private List<Order> orders;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="shipmentDay")
	private List<Shipment> shipments;

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

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setShipmentDay(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setShipmentDay(null);

		return order;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setShipmentDay(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setShipmentDay(null);

		return shipment;
	}

}