package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the PaymentMode database table.
 * 
 */
@Entity
@NamedQuery(name="PaymentMode.findAll", query="SELECT p FROM PaymentMode p")
public class PaymentMode extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="payment_mode_id")
	private int paymentModeId;

	@Column(name="payment_mode_desc")
	private String paymentModeDesc;

	@Column(name="payment_mode_name")
	private String paymentModeName;
	
	@Column(name="payment_extra_price")
	private int paymentExtraPrice;

	public PaymentMode() {
	}

	public int getPaymentModeId() {
		return this.paymentModeId;
	}

	public void setPaymentModeId(int paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public String getPaymentModeDesc() {
		return this.paymentModeDesc;
	}

	public void setPaymentModeDesc(String paymentModeDesc) {
		this.paymentModeDesc = paymentModeDesc;
	}

	public String getPaymentModeName() {
		return this.paymentModeName;
	}

	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}

	public int getPaymentExtraPrice() {
		return paymentExtraPrice;
	}

	public void setPaymentExtraPrice(int paymentExtraPrice) {
		this.paymentExtraPrice = paymentExtraPrice;
	}

}