package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ShipmentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="ShipmentStatus.findAll", query="SELECT s FROM ShipmentStatus s")
public class ShipmentStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shipment_status_id")
	private int shipmentStatusId;

	@Column(name="shipment_status_desc")
	private String shipmentStatusDesc;

	@Column(name="shipment_status_name")
	private String shipmentStatusName;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="shipmentStatus")
	private List<Shipment> shipments;

	public ShipmentStatus() {
	}

	public int getShipmentStatusId() {
		return this.shipmentStatusId;
	}

	public void setShipmentStatusId(int shipmentStatusId) {
		this.shipmentStatusId = shipmentStatusId;
	}

	public String getShipmentStatusDesc() {
		return this.shipmentStatusDesc;
	}

	public void setShipmentStatusDesc(String shipmentStatusDesc) {
		this.shipmentStatusDesc = shipmentStatusDesc;
	}

	public String getShipmentStatusName() {
		return this.shipmentStatusName;
	}

	public void setShipmentStatusName(String shipmentStatusName) {
		this.shipmentStatusName = shipmentStatusName;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setShipmentStatus(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setShipmentStatus(null);

		return shipment;
	}

}