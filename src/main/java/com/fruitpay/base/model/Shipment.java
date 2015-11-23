package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Shipment database table.
 * 
 */
@Entity
@NamedQuery(name="Shipment.findAll", query="SELECT s FROM Shipment s")
public class Shipment extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="shipment_id")
	private String shipmentId;

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

	@Column(name="record_created_date")
	private Timestamp recordCreatedDate;

	@Lob
	private String remark;

	@Column(name="week_of_year")
	private byte weekOfYear;

	@Temporal(TemporalType.DATE)
	private Date year;

	//bi-directional many-to-one association to Village
	@ManyToOne
	@JoinColumn(name="village_Code")
	private Village village;

	//bi-directional many-to-one association to CustomerOrder
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;

	//bi-directional many-to-one association to PaymentMode
	@ManyToOne
	@JoinColumn(name="payment_mode_id")
	private PaymentMode paymentMode;

	//bi-directional many-to-one association to PaymentStatus
	@ManyToOne
	@JoinColumn(name="payment_status_id")
	private PaymentStatus paymentStatus;

	//bi-directional many-to-one association to ShipmentDay
	@ManyToOne
	@JoinColumn(name="shipment_days_id")
	private ShipmentDay shipmentDay;

	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="period_id")
	private ShipmentPeriod shipmentPeriod;

	//bi-directional many-to-one association to ShipmentStatus
	@ManyToOne
	@JoinColumn(name="shipment_status_id")
	private ShipmentStatus shipmentStatus;

	//bi-directional many-to-one association to ShipmentDetail
	@OneToMany(mappedBy="shipment")
	private List<ShipmentDetail> shipmentDetails;

	public Shipment() {
	}

	public String getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
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

	public Timestamp getRecordCreatedDate() {
		return this.recordCreatedDate;
	}

	public void setRecordCreatedDate(Timestamp recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public byte getWeekOfYear() {
		return this.weekOfYear;
	}

	public void setWeekOfYear(byte weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public Date getYear() {
		return this.year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public Village getVillage() {
		return this.village;
	}

	public void setVillage(Village village) {
		this.village = village;
	}

	public CustomerOrder getCustomerOrder() {
		return this.customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public PaymentMode getPaymentMode() {
		return this.paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ShipmentDay getShipmentDay() {
		return this.shipmentDay;
	}

	public void setShipmentDay(ShipmentDay shipmentDay) {
		this.shipmentDay = shipmentDay;
	}

	public ShipmentPeriod getShipmentPeriod() {
		return this.shipmentPeriod;
	}

	public void setShipmentPeriod(ShipmentPeriod shipmentPeriod) {
		this.shipmentPeriod = shipmentPeriod;
	}

	public ShipmentStatus getShipmentStatus() {
		return this.shipmentStatus;
	}

	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public List<ShipmentDetail> getShipmentDetails() {
		return this.shipmentDetails;
	}

	public void setShipmentDetails(List<ShipmentDetail> shipmentDetails) {
		this.shipmentDetails = shipmentDetails;
	}

	public ShipmentDetail addShipmentDetail(ShipmentDetail shipmentDetail) {
		getShipmentDetails().add(shipmentDetail);
		shipmentDetail.setShipment(this);

		return shipmentDetail;
	}

	public ShipmentDetail removeShipmentDetail(ShipmentDetail shipmentDetail) {
		getShipmentDetails().remove(shipmentDetail);
		shipmentDetail.setShipment(null);

		return shipmentDetail;
	}

}