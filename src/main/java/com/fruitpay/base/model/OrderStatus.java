package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the OrderStatus database table.
 * 
 */
@Entity
@NamedQuery(name="OrderStatus.findAll", query="SELECT o FROM OrderStatus o")
public class OrderStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="order_status_id")
	private int orderStatusId;

	@Column(name="order_status_desc")
	private String orderStatusDesc;

	@Column(name="order_status_name")
	private String orderStatusName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="orderStatus")
	private List<Order> orders;

	public OrderStatus() {
	}

	public int getOrderStatusId() {
		return this.orderStatusId;
	}

	public void setOrderStatusId(int orderStatusId) {
		this.orderStatusId = orderStatusId;
	}

	public String getOrderStatusDesc() {
		return this.orderStatusDesc;
	}

	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getOrderStatusName() {
		return this.orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setOrderStatus(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setOrderStatus(null);

		return order;
	}

}