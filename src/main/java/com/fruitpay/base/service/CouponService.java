package com.fruitpay.base.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.model.Coupon;

public interface CouponService {
	
	public Page<Coupon> findAll(int page ,int size);
	
	public Coupon findById(Integer couponId);
	
	public Coupon findByCouponName(String couponName);
	
	public Coupon add(Coupon coupon);
	
	public Coupon update(Coupon coupon);
	
	public void delete(Coupon coupon);

}
