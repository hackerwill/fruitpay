package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class Coupon extends AbstractDataBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="coupon_id")
	private int couponId;
	
	@Column(name="coupon_name")
	private String couponName;
	
	@ManyToOne
	@JoinColumn(name="coupon_type")
	@JsonProperty("couponType")
	private ConstantOption couponType;
	
	private int value;
	
	@Column(name="expiry_day")
	@Temporal(TemporalType.DATE)
	private Date expiryDay;
	
	@Column(name="max_limit")
	private int maxLimit;
	
	@Column(name="max_limit")
	private int minLimit;
	
	@Column(name="max_limit")
	private int maxUsagePerCoupon;
	
	@Column(name="max_limit")
	private int maxUsagePerUser;
	
	@ManyToOne
	@JoinColumn(name="usage_individually")
	@JsonProperty("usageIndividually")
	private ConstantOption usageIndividually;

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public ConstantOption getCouponType() {
		return couponType;
	}

	public void setCouponType(ConstantOption couponType) {
		this.couponType = couponType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Date getExpiryDay() {
		return expiryDay;
	}

	public void setExpiryDay(Date expiryDay) {
		this.expiryDay = expiryDay;
	}

	public int getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}

	public int getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(int minLimit) {
		this.minLimit = minLimit;
	}

	public int getMaxUsagePerCoupon() {
		return maxUsagePerCoupon;
	}

	public void setMaxUsagePerCoupon(int maxUsagePerCoupon) {
		this.maxUsagePerCoupon = maxUsagePerCoupon;
	}

	public int getMaxUsagePerUser() {
		return maxUsagePerUser;
	}

	public void setMaxUsagePerUser(int maxUsagePerUser) {
		this.maxUsagePerUser = maxUsagePerUser;
	}

	public ConstantOption getUsageIndividually() {
		return usageIndividually;
	}

	public void setUsageIndividually(ConstantOption usageIndividually) {
		this.usageIndividually = usageIndividually;
	}

}
