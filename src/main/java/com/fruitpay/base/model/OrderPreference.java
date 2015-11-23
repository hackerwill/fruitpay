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

	@EmbeddedId
	private OrderPreferencePK id;
	
	@ManyToOne
	@JoinColumn(name="order_id", insertable=false, updatable=false)
	@MapsId("orderId")
	@JsonBackReference
	private CustomerOrder customerOrder;
	
	@ManyToOne
	@JoinColumn(name="product_id", insertable=false, updatable=false)
	@MapsId("productId")
	private Product product;

	@Column(name="like_degree")
	private byte likeDegree;

	public OrderPreference() {
	}

	public OrderPreferencePK getId() {
		return this.id;
	}

	public void setId(OrderPreferencePK id) {
		this.id = id;
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