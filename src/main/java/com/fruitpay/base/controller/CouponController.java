package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;

@Controller
@RequestMapping("couponCtrl")
public class CouponController {
	
	@Inject
	private CouponService couponService;
	
	@RequestMapping(value = "/coupons", method = RequestMethod.GET)
	public @ResponseBody List<Coupon> getAllCoupons(){
		List<Coupon> coupons = couponService.findAll();
		return coupons;
	}
	
	@RequestMapping(value = "/coupon/{couponId}", method = RequestMethod.GET)
	public @ResponseBody Coupon getCoupon(Integer couponId){
		Coupon coupon = couponService.findById(couponId);
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
