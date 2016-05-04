package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="PostalCode.findAll", query="SELECT p FROM PostalCode p")
public class PostalCode extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="post_id")
	private Integer postId;
	
	@Column(name="post_code")
	private String postCode;

	@Column(name="county_name")
	private String countyName;
	
	@Column(name="towership_name")
	private String towershipName;

	@Column(name="allow_shipment")
	private String allowShipment;
	
	@Column(name="full_name")
	private String fullName;

	public PostalCode() {
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getTowershipName() {
		return towershipName;
	}

	public void setTowershipName(String towershipName) {
		this.towershipName = towershipName;
	}

	public String getAllowShipment() {
		return allowShipment;
	}

	public void setAllowShipment(String allowShipment) {
		this.allowShipment = allowShipment;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString(){
		return this.fullName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

}