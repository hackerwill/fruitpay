package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the Village database table.
 * 
 */
@Entity
@NamedQuery(name="Village.findAll", query="SELECT v FROM Village v")
public class Village extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="village_code")
	private String villageCode;

	@Column(name="county_code")
	private int countyCode;

	@Column(name="county_name")
	private String countyName;

	@Column(name="towership_code")
	private int towershipCode;

	@Column(name="towership_name")
	private String towershipName;

	@Column(name="villige_name")
	private String villigeName;

	//bi-directional many-to-one association to Customer
	@OneToMany(mappedBy="village",  fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Customer> customers;

	//bi-directional many-to-one association to CustomerOrder
	@OneToMany(mappedBy="village",  fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CustomerOrder> customerOrders;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="village",  fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Shipment> shipments;

	public Village() {
	}

	public String getVillageCode() {
		return this.villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public int getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(int countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public int getTowershipCode() {
		return this.towershipCode;
	}

	public void setTowershipCode(int towershipCode) {
		this.towershipCode = towershipCode;
	}

	public String getTowershipName() {
		return this.towershipName;
	}

	public void setTowershipName(String towershipName) {
		this.towershipName = towershipName;
	}

	public String getVilligeName() {
		return this.villigeName;
	}

	public void setVilligeName(String villigeName) {
		this.villigeName = villigeName;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setVillage(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setVillage(null);

		return customer;
	}

	public List<CustomerOrder> getCustomerOrders() {
		return this.customerOrders;
	}

	public void setCustomerOrders(List<CustomerOrder> customerOrders) {
		this.customerOrders = customerOrders;
	}

	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().add(customerOrder);
		customerOrder.setVillage(this);

		return customerOrder;
	}

	public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder) {
		getCustomerOrders().remove(customerOrder);
		customerOrder.setVillage(null);

		return customerOrder;
	}

	public List<Shipment> getShipments() {
		return this.shipments;
	}

	public void setShipments(List<Shipment> shipments) {
		this.shipments = shipments;
	}

	public Shipment addShipment(Shipment shipment) {
		getShipments().add(shipment);
		shipment.setVillage(this);

		return shipment;
	}

	public Shipment removeShipment(Shipment shipment) {
		getShipments().remove(shipment);
		shipment.setVillage(null);

		return shipment;
	}

}