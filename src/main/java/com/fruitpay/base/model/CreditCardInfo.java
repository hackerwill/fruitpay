package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the CreditCardInfo database table.
 * 
 */
@Entity
@NamedQuery(name="CreditCardInfo.findAll", query="SELECT c FROM CreditCardInfo c")
public class CreditCardInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="credit_card_info_id")
	private int creditCardInfoId;

	@Column(name="card_expired_month")
	private String cardExpiredMonth;

	@Column(name="card_expired_year")
	private String cardExpiredYear;

	@Column(name="card_number")
	private String cardNumber;

	@Column(name="card_vendor")
	private String cardVendor;

	@Column(name="security_code")
	private String securityCode;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="customer_id")
	@JsonBackReference
	private Customer customer;

	public CreditCardInfo() {
	}

	public int getCreditCardInfoId() {
		return this.creditCardInfoId;
	}

	public void setCreditCardInfoId(int creditCardInfoId) {
		this.creditCardInfoId = creditCardInfoId;
	}

	public String getCardExpiredMonth() {
		return this.cardExpiredMonth;
	}

	public void setCardExpiredMonth(String cardExpiredMonth) {
		this.cardExpiredMonth = cardExpiredMonth;
	}

	public String getCardExpiredYear() {
		return this.cardExpiredYear;
	}

	public void setCardExpiredYear(String cardExpiredYear) {
		this.cardExpiredYear = cardExpiredYear;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardVendor() {
		return this.cardVendor;
	}

	public void setCardVendor(String cardVendor) {
		this.cardVendor = cardVendor;
	}

	public String getSecurityCode() {
		return this.securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}