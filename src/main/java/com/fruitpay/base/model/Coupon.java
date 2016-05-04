package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="Coupon.findAll", query="SELECT c FROM Coupon c")
public class Coupon extends AbstractEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="coupon_id")
	private Integer couponId;
	
	@Column(name="coupon_name")
	private String couponName;
	
	@Column(name="coupon_desc")
	private String couponDesc;
	
	@ManyToOne
	@JoinColumn(name="coupon_type")
	@JsonProperty("couponType")
	private ConstantOption couponType;
	
	@Column(name="value")
	private Integer value;
	
	@Column(name="first_value")
	private Integer firstValue;
	
	@Column(name="expiry_day")
	@Temporal(TemporalType.DATE)
	private Date expiryDay;
	
	@Column(name="max_limit")
	private Integer maxLimit;
	
	@Column(name="min_limit")
	private Integer minLimit;
	
	@Column(name="max_usage_per_coupon")
	private Integer maxUsagePerCoupon;
	
	@Column(name="max_usage_per_user")
	private Integer maxUsagePerUser;
	
	@ManyToOne
	@JoinColumn(name="usage_individually")
	@JsonProperty("usageIndividually")
	private ConstantOption usageIndividually;

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponDesc() {
		return couponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		this.couponDesc = couponDesc;
	}

	public ConstantOption getCouponType() {
		return couponType;
	}

	public void setCouponType(ConstantOption couponType) {
		this.couponType = couponType;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getFirstValue() {
		return firstValue;
	}

	public void setFirstValue(Integer firstValue) {
		this.firstValue = firstValue;
	}

	public Date getExpiryDay() {
		return expiryDay;
	}

	public void setExpiryDay(Date expiryDay) {
		this.expiryDay = expiryDay;
	}

	public Integer getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Integer getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(Integer minLimit) {
		this.minLimit = minLimit;
	}

	public Integer getMaxUsagePerCoupon() {
		return maxUsagePerCoupon;
	}

	public void setMaxUsagePerCoupon(Integer maxUsagePerCoupon) {
		this.maxUsagePerCoupon = maxUsagePerCoupon;
	}

	public Integer getMaxUsagePerUser() {
		return maxUsagePerUser;
	}

	public void setMaxUsagePerUser(Integer maxUsagePerUser) {
		this.maxUsagePerUser = maxUsagePerUser;
	}

	public ConstantOption getUsageIndividually() {
		return usageIndividually;
	}

	public void setUsageIndividually(ConstantOption usageIndividually) {
		this.usageIndividually = usageIndividually;
	}
	
}
