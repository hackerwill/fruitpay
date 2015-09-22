package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the OrderPlatform database table.
 * 
 */
@Entity
@NamedQuery(name="OrderPlatform.findAll", query="SELECT o FROM OrderPlatform o")
public class OrderPlatform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="platform_id")
	private int platformId;

	@Column(name="platform_desc")
	private String platformDesc;

	@Column(name="platform_name")
	private String platformName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="orderPlatform")
	private List<Order> orders;

	public OrderPlatform() {
	}

	public int getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public String getPlatformDesc() {
		return this.platformDesc;
	}

	public void setPlatformDesc(String platformDesc) {
		this.platformDesc = platformDesc;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setOrderPlatform(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setOrderPlatform(null);

		return order;
	}

}