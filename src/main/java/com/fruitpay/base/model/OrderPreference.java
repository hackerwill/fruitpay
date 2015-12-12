package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the OrderPreference database table.
 * 
 */
@Entity
@NamedQuery(name="OrderPreference.findAll", query="SELECT o FROM OrderPreference o")
public class OrderPreference extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="preference_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer preferenceId;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonBackReference
	private CustomerOrder customerOrder;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;

	@Column(name="like_degree")
	private byte likeDegree;

	public OrderPreference() {
	}

	public Integer getPreferenceId() {
		return preferenceId;
	}

	public void setPreferenceId(Integer preferenceId) {
		this.preferenceId = preferenceId;
	}

	public byte getLikeDegree() {
		return this.likeDegree;
	}

	public void setLikeDegree(byte likeDegree) {
		this.likeDegree = likeDegree;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}