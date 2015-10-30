package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Order database table.
 * 
 */
@Entity
@NamedQuery(name="Order.findAll", query="SELECT o FROM CustomerOrder o")
public class CustomerOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private int orderId;

	@Column(name="order_date")
	private Timestamp orderDate;

	@Column(name="receiver_address")
	private String receiverAddress;

	@Column(name="receiver_first_name")
	private String receiverFirstName;

	@Column(name="receiver_last_name")
	private String receiverLastName;
	
	@Column(name="receiver_gender")
	private String receiverGender;

	@Column(name="receiver_phone")
	private String receiverPhone;

	@Lob
	private String remark;

	@Column(name="tax_id")
	private String taxId;

	@Column(name="tax_title")
	private String taxTitle;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonBackReference
	private Customer customer;

	//bi-directional many-to-one association to OrderPlatform
	@ManyToOne
	@JoinColumn(name="platform_id")
	private OrderPlatform orderPlatform;

	//bi-directional many-to-one association to OrderProgram
	@ManyToOne
	@JoinColumn(name="program_id")
	private OrderProgram orderProgram;

	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="shipment_period_id")
	private ShipmentPeriod shipmentPeriod;

	//bi-directional many-to-one association to PaymentMode
	@ManyToOne
	@JoinColumn(name="payment_mode_id")
	private PaymentMode paymentMode;

	//bi-directional many-to-one association to ShipmentDay
	@ManyToOne
	@JoinColumn(name="shipment_days_id")
	private ShipmentDay shipmentDay;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="receiver_area_id")
	private Area area;

	//bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="order_status_id")
	private OrderStatus orderStatus;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="order")
	private List<Shipment> shipments;

	public CustomerOrder() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Timestamp getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public String getReceiverAddress() {
		return this.receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverFirstName() {
		return this.receiverFirstName;
	}

	public void setReceiverFirstName(String receiverFirstName) {
		this.receiverFirstName = receiverFirstName;
	}

	public String getReceiverLastName() {
		return this.receiverLastName;
	}

	public void setReceiverLastName(String receiverLastName) {
		this.receiverLastName = receiverLastName;
	}

	public String getReceiverPhone() {
		return this.receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaxId() {
		return this.taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getTaxTitle() {
		return this.taxTitle;
	}

	public void setTaxTitle(String taxTitle) {
		this.taxTitle = taxTitle;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public OrderPlatform getOrderPlatform() {
		return this.orderPlatform;
	}

	public void setOrderPlatform(OrderPlatform orderPlatform) {
		this.orderPlatform = orderPlatform;
	}

	public OrderProgram getOrderProgram() {
		return this.orderProgram;
	}

	public void setOrderProgram(OrderProgram orderProgram) {
		this.orderProgram = orderProgram;
	}

	public ShipmentPeriod getShipmentPeriod() {
		return this.shipmentPeriod;
	}

	public void setShipmentPeriod(ShipmentPeriod shipmentPeriod) {
		this.shipmentPeriod = shipmentPeriod;
	}

	public PaymentMode getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public ShipmentDay getShipmentDay() {
		return this.shipmentDay;
	}

	public void setShipmentDay(ShipmentDay shipmentDay) {
		this.shipmentDay = shipmentDay;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setOrder(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setOrder(null);

		return shipment;
	}

	public String getReceiverGender() {
		return receiverGender;
	}

	public void setReceiverGender(String receiverGender) {
		this.receiverGender = receiverGender;
	}

}