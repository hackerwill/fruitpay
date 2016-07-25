package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;



@Entity
@NamedQuery(name="CustomerClaimOptionalField.findAll", query="SELECT c FROM CustomerClaimOptionalField c")
public class CustomerClaimOptionalField extends AbstractEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="optional_field_id")
	private Integer optionalFeildId;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="customer_claim_id")
	@JsonBackReference(value="customerClaim-customerClaimOptionalFields")
	private CustomerClaim customerClaim;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="field_key")
	@JsonProperty("fieldKey")
	private ConstantOption fieldKey;
	
	@Column(name="value_type", nullable=false)
	private int valueType;

	@Column(name="value", nullable=false)
	private String value;

	public Integer getOptionalFeildId() {
		return optionalFeildId;
	}

	public void setOptionalFeildId(Integer optionalFeildId) {
		this.optionalFeildId = optionalFeildId;
	}

	public CustomerClaim getCustomerClaim() {
		return customerClaim;
	}

	public void setCustomerClaim(CustomerClaim customerClaim) {
		this.customerClaim = customerClaim;
	}

	public ConstantOption getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(ConstantOption fieldKey) {
		this.fieldKey = fieldKey;
	}

	public int getValueType() {
		return valueType;
	}

	public void setValueType(int valueType) {
		this.valueType = valueType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
