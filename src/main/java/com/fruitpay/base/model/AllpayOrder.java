package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="AllpayOrder.findAll", query="SELECT a FROM AllpayOrder a")
@Cacheable(false)
public class AllpayOrder implements Serializable {
	
	@Id
	@Column(name="allpay_order_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int allpayOrderId;

	@Column(name="payment_date")
	private String paymentDate;
	
	@Column(name="rtn_code")
	private String rtnCode;
	
	@Column(name="rtn_message")
	private String rtnMessage;

	public int getAllpayOrderId() {
		return allpayOrderId;
	}

	public void setAllpayOrderId(int allpayOrderId) {
		this.allpayOrderId = allpayOrderId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

}
