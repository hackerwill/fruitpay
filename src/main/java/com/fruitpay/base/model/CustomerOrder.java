package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CustomerOrder database table.
 * 
 */
@Entity
@NamedQuery(name="CustomerOrder.findAll", query="SELECT c FROM CustomerOrder c")
public class CustomerOrder extends AbstractDataBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	private int orderId;

	
	@Column(name="order_date")
	private Date orderDate;

	@Column(name="receiver_address")
	private String receiverAddress;

	@Column(name="receiver_first_name")
	private String receiverFirstName;

	@Column(name="receiver_gender")
	private String receiverGender;

	@Column(name="receiver_last_name")
	private String receiverLastName;

	@Column(name="receiver_phone")
	private String receiverPhone;

	@Lob
	private String remark;

	@Column(name="tax_id")
	private String taxId;

	@Column(name="tax_title")
	private String taxTitle;

	//bi-directional many-to-one association to Village
	@ManyToOne
	@JoinColumn(name="village_code")
	@JsonBackReference("village")
	private Village village;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonBackReference("customer")
	private Customer customer;

	//bi-directional many-to-one association to OrderPlatform
	@ManyToOne
	@JoinColumn(name="platform_id")
	@JsonBackReference("orderPlatform")
	private OrderPlatform orderPlatform;

	//bi-directional many-to-one association to OrderProgram
	@ManyToOne
	@JoinColumn(name="program_id")
	@JsonBackReference("orderProgram")
	private OrderProgram orderProgram;

	//bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="order_status_id")
	@JsonBackReference("orderStatus")
	private OrderStatus orderStatus;

	//bi-directional many-to-one association to PaymentMode
	@ManyToOne
	@JoinColumn(name="payment_mode_id")
	@JsonBackReference("paymentMode")
	private PaymentMode paymentMode;

	//bi-directional many-to-one association to ShipmentDay
	@ManyToOne
	@JoinColumn(name="shipment_days_id")
	@JsonBackReference("shipmentDay")
	private ShipmentDay shipmentDay;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="customerOrder")
	@JsonIgnore
	private List<Shipment> shipments;

	public CustomerOrder() {
	}

	public int getOrderId() {
		return this.orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
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

	public String getReceiverGender() {
		return this.receiverGender;
	}

	public void setReceiverGender(String receiverGender) {
		this.receiverGender = receiverGender;
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

	public Village getVillage() {
		return this.village;
	}

	public void setVillage(Village village) {
		this.village = village;
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

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
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

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setCustomerOrder(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setCustomerOrder(null);

		return shipment;
	}

}