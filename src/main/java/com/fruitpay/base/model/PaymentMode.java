package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;


/**
 * The persistent class for the PaymentMode database table.
 * 
 */
@Entity
@NamedQuery(name="PaymentMode.findAll", query="SELECT p FROM PaymentMode p")
public class PaymentMode extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="payment_mode_id")
	private int paymentModeId;

	@Column(name="payment_mode_desc")
	private String paymentModeDesc;

	@Column(name="payment_mode_name")
	private String paymentModeName;

	//bi-directional many-to-one association to CustomerOrder
	@OneToMany(mappedBy="paymentMode")
	@JsonBackReference("paymentMode")
	private List<CustomerOrder> customerOrders;

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

	public List<CustomerOrder> getCustomerOrders() {
		return this.customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().add(customerOrder);
		customerOrder.setPaymentMode(this);

		return customerOrder;
	}

	public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().remove(customerOrder);
		customerOrder.setPaymentMode(null);

		return customerOrder;
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