package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Customer database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="customer_id")
	private int customerId;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthday;

	private String cellphone;

	private String email;

	@Column(name="first_name")
	private String firstName;

	private String gender;

	@Column(name="house_phone")
	private String housePhone;

	@Column(name="last_name")
	private String lastName;

	@Column(name="office_phone")
	private String officePhone;

	private String password;

	//bi-directional many-to-one association to CreditCardInfo
	@OneToMany(mappedBy="customer")
	private List<CreditCardInfo> creditCardInfos;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="referenced_id")
	private Customer customer;

	//bi-directional many-to-one association to Customer
	@OneToMany(mappedBy="customer")
	private List<Customer> customers;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JoinColumn(name="area_id")
	private Area area;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="customer")
	private List<Order> orders;

	public Customer() {
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCellphone() {
		return this.cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHousePhone() {
		return this.housePhone;
	}

	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<CreditCardInfo> getCreditCardInfos() {
		return this.creditCardInfos;
	}

	public void setCreditCardInfos(List<CreditCardInfo> creditCardInfos) {
		this.creditCardInfos = creditCardInfos;
	}

	public CreditCardInfo addCreditCardInfo(CreditCardInfo creditCardInfo) {
		getCreditCardInfos().add(creditCardInfo);
		creditCardInfo.setCustomer(this);

		return creditCardInfo;
	}

	public CreditCardInfo removeCreditCardInfo(CreditCardInfo creditCardInfo) {
		getCreditCardInfos().remove(creditCardInfo);
		creditCardInfo.setCustomer(null);

		return creditCardInfo;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Customer> getCustomers() {
		return this.customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setCustomer(this);

		return customer;
	}

	public Customer removeCustomer(Customer customer) {
		getCustomers().remove(customer);
		customer.setCustomer(null);

		return customer;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setCustomer(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setCustomer(null);

		return order;
	}

}