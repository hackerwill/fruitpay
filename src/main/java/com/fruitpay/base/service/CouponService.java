package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Coupon;

public interface CouponService {
	
	public List<Coupon> findAll();
	
	public Coupon findById(Integer couponId);
	
	public Coupon add(Coupon coupon);
	
	public Coupon update(Coupon coupon);
	
	public void delete(Coupon coupon);

}
