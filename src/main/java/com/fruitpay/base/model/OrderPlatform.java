package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the OrderPlatform database table.
 * 
 */
@Entity
@NamedQuery(name="OrderPlatform.findAll", query="SELECT o FROM OrderPlatform o")
public class OrderPlatform extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="platform_id")
	private int platformId;

	@Column(name="platform_desc")
	private String platformDesc;

	@Column(name="platform_name")
	private String platformName;

	//bi-directional many-to-one association to CustomerOrder
	@OneToMany(mappedBy="orderPlatform")
	@JsonManagedReference("orderPlatform")
	private List<CustomerOrder> customerOrders;

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

	public List<CustomerOrder> getCustomerOrders() {
		return this.customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().add(customerOrder);
		customerOrder.setOrderPlatform(this);

		return customerOrder;
	}

	public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().remove(customerOrder);
		customerOrder.setOrderPlatform(null);

		return customerOrder;
	}

}