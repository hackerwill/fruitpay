package com.fruitpay.base.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fruitpay.comm.annotation.NeedRecord;
import com.fruitpay.comm.utils.DateUtil;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CustomerOrder database table.
 * 
 */
@Entity
@NamedQuery(name="CustomerOrder.findAll", query="SELECT c FROM CustomerOrder c")
public class CustomerOrder extends AbstractEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer orderId;

	@Column(name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;

	@Column(name="receiver_address")
	private String receiverAddress;

	@Column(name="receiver_first_name")
	private String receiverFirstName;

	@Column(name="receiver_gender")
	private String receiverGender;

	@Column(name="receiver_last_name")
	private String receiverLastName;

	@Column(name="receiver_cellphone")
	private String receiverCellphone;
	
	@Column(name="receiver_house_phone")
	private String receiverHousePhone;

	@Column(columnDefinition = "TEXT")
	private String remark;

	@Column(name="tax_id")
	private String taxId;

	@Column(name="tax_title")
	private String taxTitle;
	
	@Column(name="allow_foreign_fruits")
	private String allowForeignFruits;
	
	@Column(name="receipt_title")
	private String receiptTitle;
	
	@Column(name="receipt_vat_number")
	private String receiptVatNumber;

	@ManyToOne
	@JoinColumn(name="post_id")
	private PostalCode postalCode;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonBackReference("customer")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name="platform_id")
	private OrderPlatform orderPlatform;

	@ManyToOne
	@JoinColumn(name="program_id")
	private OrderProgram orderProgram;
	
	@Column(name="program_num")
	private Integer programNum;

	@ManyToOne
	@JoinColumn(name="order_status_id")
	@NeedRecord
	private OrderStatus orderStatus;

	@ManyToOne
	@JoinColumn(name="payment_mode_id")
	@NeedRecord
	private PaymentMode paymentMode;
	
	//bi-directional many-to-one association to ShipmentPeriod
	@ManyToOne
	@JoinColumn(name="period_id")
	private ShipmentPeriod shipmentPeriod;

	@ManyToOne
	@JoinColumn(name="shipment_days_id")
	private ShipmentDay shipmentDay;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="customerOrder")
	@JsonIgnore
	private List<Shipment> shipments;
	
	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="customerOrder", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JsonManagedReference
	private List<OrderPreference> orderPreferences;
	
	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="customerOrder", fetch = FetchType.LAZY)
	private List<ShipmentChange> shipmentChanges;
	
	@ManyToOne
	@JoinColumn(name="receive_way")
	@JsonProperty("receiveWay")
	private ConstantOption receiveWay;
	
	@ManyToOne
	@JoinColumn(name="shipment_time")
	@JsonProperty("shipmentTime")
	private ConstantOption shipmentTime;
	
	@ManyToOne
	@JoinColumn(name="coming_from")
	@JsonProperty("comingFrom")
	private ConstantOption comingFrom;
	
	@ManyToOne
	@JoinColumn(name="receipt_way")
	@JsonProperty("receiptWay")
	private ConstantOption receiptWay;
	
	@ManyToOne
	@JoinColumn(name="delivery_day")
	@JsonProperty("deliveryDay")
	@NeedRecord
	private ConstantOption deliveryDay;
	
	@Column(name="shipping_cost")
	private Integer shippingCost;
	
	@Column(name="total_price")
	@NeedRecord
	private Integer totalPrice;
	
	@ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(
	      name="CouponRecord",
	      joinColumns={@JoinColumn(name="order_id", referencedColumnName="order_id")},
	      inverseJoinColumns={@JoinColumn(name="coupon_id", referencedColumnName="coupon_id")})
	private List<Coupon> coupons;
	
	@Column(name="valid_flag")
	private Integer validFlag;
	
	@Column(name="allpay_rtn_code")
	private String allpayRtnCode;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name="allpay_order_id")
	private AllpayOrder allpayOrder;
	
	@OneToMany(mappedBy="customerOrder", fetch = FetchType.LAZY)
	@JsonManagedReference
	List<OrderComment> orderComments;
	
	@Column(name="shipment_count")
	private Integer shipmentCount;

	public CustomerOrder() {
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}
	
	public LocalDate getOrderLocalDate() {
		return DateUtil.toLocalDate(this.orderDate);
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

	public String getReceiverCellphone() {
		return receiverCellphone;
	}

	public void setReceiverCellphone(String receiverCellphone) {
		this.receiverCellphone = receiverCellphone;
	}

	public String getReceiverHousePhone() {
		return receiverHousePhone;
	}

	public void setReceiverHousePhone(String receiverHousePhone) {
		this.receiverHousePhone = receiverHousePhone;
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
	
	public List<OrderPreference> getOrderPreferences() {
		return this.orderPreferences;
	}

	public void setOrderPreferences(List<OrderPreference> orderPreferences) {
		this.orderPreferences = orderPreferences;
	}

	public OrderPreference addOrderPreference(OrderPreference orderPreference) {
		getOrderPreferences().add(orderPreference);
		orderPreference.setCustomerOrder(this);

		return orderPreference;
	}

	public OrderPreference removeShipment(OrderPreference orderPreference) {
		getOrderPreferences().remove(orderPreference);
		orderPreference.setCustomerOrder(null);

		return orderPreference;
	}
	
	public ShipmentPeriod getShipmentPeriod() {
		return this.shipmentPeriod;
	}

	public void setShipmentPeriod(ShipmentPeriod shipmentPeriod) {
		this.shipmentPeriod = shipmentPeriod;
	}

	public ConstantOption getReceiveWay() {
		return receiveWay;
	}

	public void setReceiveWay(ConstantOption receiveWay) {
		this.receiveWay = receiveWay;
	}

	public ConstantOption getShipmentTime() {
		return shipmentTime;
	}

	public void setShipmentTime(ConstantOption shipmentTime) {
		this.shipmentTime = shipmentTime;
	}

	public ConstantOption getComingFrom() {
		return comingFrom;
	}

	public void setComingFrom(ConstantOption comingFrom) {
		this.comingFrom = comingFrom;
	}

	public ConstantOption getReceiptWay() {
		return receiptWay;
	}

	public void setReceiptWay(ConstantOption receiptWay) {
		this.receiptWay = receiptWay;
	}

	public String getAllowForeignFruits() {
		return allowForeignFruits;
	}

	public void setAllowForeignFruits(String allowForeignFruits) {
		this.allowForeignFruits = allowForeignFruits;
	}

	public PostalCode getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(PostalCode postalCode) {
		this.postalCode = postalCode;
	}

	public String getReceiptTitle() {
		return receiptTitle;
	}

	public void setReceiptTitle(String receiptTitle) {
		this.receiptTitle = receiptTitle;
	}

	public String getReceiptVatNumber() {
		return receiptVatNumber;
	}

	public void setReceiptVatNumber(String receiptVatNumber) {
		this.receiptVatNumber = receiptVatNumber;
	}

	public Integer getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(Integer shippingCost) {
		this.shippingCost = shippingCost;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getProgramNum() {
		return programNum;
	}

	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}

	public ConstantOption getDeliveryDay() {
		return deliveryDay;
	}

	public void setDeliveryDay(ConstantOption deliveryDay) {
		this.deliveryDay = deliveryDay;
	}
	
	public List<Coupon> getCoupons() {
		return this.coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public Coupon addCoupon(Coupon coupon) {
		getCoupons().add(coupon);
		return coupon;
	}

	public Coupon removeCoupon(Coupon coupon) {
		getCoupons().remove(coupon);
		return coupon;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public String getAllpayRtnCode() {
		return allpayRtnCode;
	}

	public void setAllpayRtnCode(String allpayRtnCode) {
		this.allpayRtnCode = allpayRtnCode;
	}

	public AllpayOrder getAllpayOrder() {
		return allpayOrder;
	}

	public void setAllpayOrder(AllpayOrder allpayOrder) {
		this.allpayOrder = allpayOrder;
	}

	public List<ShipmentChange> getShipmentChanges() {
		return shipmentChanges;
	}

	public void setShipmentChanges(List<ShipmentChange> shipmentChanges) {
		this.shipmentChanges = shipmentChanges;
	}

	public List<OrderComment> getOrderComments() {
		return orderComments;
	}

	public void setOrderComments(List<OrderComment> orderComments) {
		this.orderComments = orderComments;
	}

	public Integer getShipmentCount() {
		return shipmentCount;
	}

	public void setShipmentCount(Integer shipmentCount) {
		this.shipmentCount = shipmentCount;
	}
	
}