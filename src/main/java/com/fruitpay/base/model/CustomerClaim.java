package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;




@Entity
@NamedQuery(name="CustomerClaim.findAll", query="SELECT c FROM CustomerClaim c")
public class CustomerClaim extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="claim_id")
	private Integer claimId;
	
	@Column(name="claim_platform")
	private String claimPlatform;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@Column(name="date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;
	
	@ManyToOne
	@JoinColumn(name="shipment_record_detail_id")
	private ShipmentRecordDetail shipmentRecordDetail;
	
	@Column(name="claim_type")
	private String claimType;
	
	@OneToMany(mappedBy="customerClaim", fetch = FetchType.LAZY)
	@JsonManagedReference(value="customerClaim-customerClaimOptionalFields")
	private List<CustomerClaimOptionalField> customerClaimOptionalFields;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="customerClaim", fetch = FetchType.LAZY)
	@JsonManagedReference(value="customerClaim-customerClaimStatuses")
	private List<CustomerClaimStatus> customerClaimStatuses;
	
	@Column(name="valid_flag", nullable=false)
	private Integer validFlag;
	
	@Column(name="content")
	private String content;
	
	@Column(name="handleWay")
	private String handleWay;
	
	@Column(name="note")
	private String note;

	public Integer getClaimId() {
		return claimId;
	}

	public void setClaimId(Integer claimId) {
		this.claimId = claimId;
	}

	public String getClaimPlatform() {
		return claimPlatform;
	}

	public void setClaimPlatform(String claimPlatform) {
		this.claimPlatform = claimPlatform;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public ShipmentRecordDetail getShipmentRecordDetail() {
		return shipmentRecordDetail;
	}

	public void setShipmentRecordDetail(ShipmentRecordDetail shipmentRecordDetail) {
		this.shipmentRecordDetail = shipmentRecordDetail;
	}

	public String getClaimType() {
		return claimType;
	}

	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public List<CustomerClaimOptionalField> getCustomerClaimOptionalFields() {
		return customerClaimOptionalFields;
	}

	public void setCustomerClaimOptionalFields(List<CustomerClaimOptionalField> customerClaimOptionalFields) {
		this.customerClaimOptionalFields = customerClaimOptionalFields;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHandleWay() {
		return handleWay;
	}

	public void setHandleWay(String handleWay) {
		this.handleWay = handleWay;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<CustomerClaimStatus> getCustomerClaimStatuses() {
		return customerClaimStatuses;
	}

	public void setCustomerClaimStatuses(List<CustomerClaimStatus> customerClaimStatuses) {
		this.customerClaimStatuses = customerClaimStatuses;
		customerClaimStatuses = customerClaimStatuses.stream().map(customerClaimStatus -> {
			customerClaimStatus.setCustomerClaim(this);
			return customerClaimStatus;
		}).collect(Collectors.toList());
	}
	
	public void add(CustomerClaimStatus customerClaimStatus) {
		if(this.customerClaimStatuses == null) {
			this.customerClaimStatuses = new ArrayList<>();
		}
		this.customerClaimStatuses.add(customerClaimStatus);
		customerClaimStatus.setCustomerClaim(this);
	}
	
}