package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;




@Entity
@NamedQuery(name="CustomerClaimStatus.findAll", query="SELECT c FROM CustomerClaimStatus c")
public class CustomerClaimStatus extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="claim_status_id")
	private Integer claimStatusId;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="claim_status_type")
	@JsonProperty("claimStatusType")
	private ConstantOption claimStatusType;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="claim_id")
	@JsonBackReference(value="customerClaim-customerClaimStatuses")
	private CustomerClaim customerClaim;
	
	@Column(name="note")
	private String note;

	public Integer getClaimStatusId() {
		return claimStatusId;
	}

	public void setClaimStatusId(Integer claimStatusId) {
		this.claimStatusId = claimStatusId;
	}

	public ConstantOption getClaimStatusType() {
		return claimStatusType;
	}

	public void setClaimStatusType(ConstantOption claimStatusType) {
		this.claimStatusType = claimStatusType;
	}

	public CustomerClaim getCustomerClaim() {
		return customerClaim;
	}

	public void setCustomerClaim(CustomerClaim customerClaim) {
		this.customerClaim = customerClaim;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}