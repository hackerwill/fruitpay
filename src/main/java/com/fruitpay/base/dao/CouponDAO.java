package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Coupon;

public interface CouponDAO extends JpaRepository<Coupon, Integer> {
	
	public Coupon findByCouponName(String couponName);

}
