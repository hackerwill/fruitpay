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
public class OrderProgram extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="program_id")
	private int programId;

	@Column(name="program_desc")
	private String programDesc;

	@Column(name="program_name")
	private String programName;

	//bi-directional many-to-one association to CustomerOrder
	@OneToMany(mappedBy="orderProgram")
	private List<CustomerOrder> customerOrders;

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

	public List<CustomerOrder> getCustomerOrders() {
		return this.customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().add(customerOrder);
		customerOrder.setOrderProgram(this);

		return customerOrder;
	}

	public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().remove(customerOrder);
		customerOrder.setOrderProgram(null);

		return customerOrder;
	}

}