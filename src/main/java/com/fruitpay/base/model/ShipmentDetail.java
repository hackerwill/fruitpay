package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ShipmentDetail database table.
 * 
 */
@Entity
@NamedQuery(name="ShipmentDetail.findAll", query="SELECT s FROM ShipmentDetail s")
public class ShipmentDetail extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shipment_detail_id")
	private String shipmentDetailId;

	@Column(name="eating_priority")
	private byte eatingPriority;

	private int quantity;

	//bi-directional many-to-one association to Shipment
	@ManyToOne
	@JoinColumn(name="shipment_id")
	private Shipment shipment;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	public ShipmentDetail() {
	}

	public String getShipmentDetailId() {
		return this.shipmentDetailId;
	}

	public void setShipmentDetailId(String shipmentDetailId) {
		this.shipmentDetailId = shipmentDetailId;
	}

	public byte getEatingPriority() {
		return this.eatingPriority;
	}

	public void setEatingPriority(byte eatingPriority) {
		this.eatingPriority = eatingPriority;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Shipment getShipment() {
		return this.shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}