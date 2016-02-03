package com.fruitpay.base.controller;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;

@Controller
@RequestMapping("couponCtrl")
public class CouponController {
	
	@Inject
	private CouponService couponService;
	
	@RequestMapping(value = "/coupons", method = RequestMethod.GET)
	public @ResponseBody Page<Coupon> getAllCoupons(			
			@RequestParam(value = "page", required = false, defaultValue = "0") int page ,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size ){
		Page<Coupon> coupons = couponService.findAll(page,size);
		return coupons;
	}
	
	@RequestMapping(value = "/coupon/{couponId}", method = RequestMethod.GET)
	public @ResponseBody Coupon getCoupon(@PathVariable Integer couponId){
		Coupon coupon = couponService.findById(couponId);
		return coupon;
	}
	
	@RequestMapping(value = "/coupon/name/{couponName}", method = RequestMethod.GET)
	public @ResponseBody Coupon getCouponName(@PathVariable String couponName){
		Coupon coupon = couponService.findByCouponName(couponName);
		return coupon;
	}
	
	@RequestMapping(value = "/coupon", method = RequestMethod.POST)
	public @ResponseBody Coupon addCoupon(@RequestBody Coupon coupon){
		coupon = couponService.add(coupon);
		return coupon;
	}
	
	@RequestMapping(value = "/coupon", method = RequestMethod.PUT)
	public @ResponseBody Coupon updateCoupon(@RequestBody Coupon coupon){
		coupon = couponService.update(coupon);
		return coupon;
	}
	
	@RequestMapping(value = "/coupon", method = RequestMethod.DELETE)
	public @ResponseBody String deleteCoupon(@RequestBody Coupon coupon){
		couponService.delete(coupon);
		return "true";
	}

}
