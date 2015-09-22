package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the OrderProgram database table.
 * 
 */
@Entity
@NamedQuery(name="OrderProgram.findAll", query="SELECT o FROM OrderProgram o")
public class OrderProgram implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="program_id")
	private int programId;

	@Column(name="program_desc")
	private String programDesc;

	@Column(name="program_name")
	private String programName;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="orderProgram")
	private List<Order> orders;

	public OrderProgram() {
	}

	public int getProgramId() {
		return this.programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramDesc() {
		return this.programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

	public String getProgramName() {
		return this.programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setOrderProgram(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setOrderProgram(null);

		return order;
	}

}