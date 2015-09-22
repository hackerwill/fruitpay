package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the PaymentMode database table.
 * 
 */
@Entity
@NamedQuery(name="PaymentMode.findAll", query="SELECT p FROM PaymentMode p")
public class PaymentMode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="payment_mode_id")
	private int paymentModeId;

	@Column(name="payment_mode_desc")
	private String paymentModeDesc;

	@Column(name="payment_mode_name")
	private String paymentModeName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="paymentMode")
	private List<Order> orders;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="paymentMode")
	private List<Shipment> shipments;

	public PaymentMode() {
	}

	public int getPaymentModeId() {
		return this.paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getPaymentModeDesc() {
		return this.paymentModeDesc;
	}

	public void setPaymentModeDesc(String paymentModeDesc) {
		this.paymentModeDesc = paymentModeDesc;
	}

	public String getPaymentModeName() {
		return this.paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setPaymentMode(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setPaymentMode(null);

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
		shipment.setPaymentMode(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setPaymentMode(null);

		return shipment;
	}

}