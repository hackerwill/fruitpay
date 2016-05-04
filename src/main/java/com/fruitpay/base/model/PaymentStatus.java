package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="PaymentStatus.findAll", query="SELECT p FROM PaymentStatus p")
public class PaymentStatus extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="payment_status_id")
	private Integer paymentStatusId;

	@Column(name="payment_status_desc")
	private String paymentStatusDesc;

	@Column(name="payment_status_name")
	private String paymentStatusName;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="paymentStatus")
	private List<Shipment> shipments;

	public PaymentStatus() {
	}

	public Integer getPaymentStatusId() {
		return this.paymentStatusId;
	}

	public void setPaymentStatusId(Integer paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
	}

	public String getPaymentStatusDesc() {
		return this.paymentStatusDesc;
	}

	public void setPaymentStatusDesc(String paymentStatusDesc) {
		this.paymentStatusDesc = paymentStatusDesc;
	}

	public String getPaymentStatusName() {
		return this.paymentStatusName;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setPaymentStatus(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setPaymentStatus(null);

		return shipment;
	}

}