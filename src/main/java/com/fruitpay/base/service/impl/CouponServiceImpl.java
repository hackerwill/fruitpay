package com.fruitpay.base.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CouponDAO;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {
	
	/**
	 * 打折類型
	 * 	1. By Percentage
	 * 		依照打折數做打折，如果value是10的話，代表原本價格的90%，打九折
	 *  2. By Amount
	 *  	依照數量做打折，如果value的值是100的話，代表折價100元
	 * 
	 * */
	private enum TYPE{
		byPercentage,
		byAmount;
	}

	@Inject
	private CouponDAO couponDAO;

	@Override
	public Page<Coupon> findAll(int page ,int size) {
		Page<Coupon> coupons = couponDAO.findAll(new PageRequest(page, size, new Sort(Sort.Direction.DESC, "couponId")));
		return coupons;
	}

	@Override
	public Coupon findById(Integer couponId) {
		Coupon coupon = couponDAO.findOne(couponId);
		return coupon;
	}
	
	@Override
	public Coupon findByCouponName(String couponName) {
		Coupon coupon = couponDAO.findByCouponName(couponName);
		return coupon;
	}

	@Override
	@Transactional
	public Coupon add(Coupon coupon) {
		coupon = couponDAO.save(coupon);
		return coupon;
	}

	@Override
	@Transactional
	public Coupon update(Coupon coupon) {
		Coupon origin = couponDAO.findOne(coupon.getCouponId());
		
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Coupon.CouponNotFound.getReturnMessage());
		
		BeanUtils.copyProperties(coupon, origin);
		
		return origin;
	}

	@Override
	@Transactional
	public void delete(Coupon coupon) {
		couponDAO.delete(coupon);
	}

	@Override
	public Double countDiscountPercentage(Coupon coupon, int price) {
		double percentege = 1.0;
		
		switch(getType(coupon)){
		//打折比例
		case byPercentage: 
			percentege = (100 - coupon.getValue()) / 100.0;
			break;
		//依照打折數
		case byAmount:
			percentege = countFinalPrice(coupon, price) / (double)price; 
			break;
		}
		
		return new BigDecimal(percentege)
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	@Override
	public Double countDiscountPercentOff(Coupon coupon, int price) {
		double percentoff = 1.0 - countDiscountPercentage(coupon, price);
		return new BigDecimal(percentoff)
				.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	@Override
	public Integer countDiscountAmount(Coupon coupon, int price) {
		int discountPrice = -1;
		
		switch(getType(coupon)){
			//打折比例
			case byPercentage: 
				discountPrice = (int)(Math.round(price * countDiscountPercentOff(coupon, price)));
				break;
			//依照打折數
			case byAmount:
				if(coupon.getValue() > price )
					throw new IllegalArgumentException("The discount amount " + coupon.getValue() + "is greater than price" + price);
				discountPrice = coupon.getValue();
				break;
		}
	
		return discountPrice;
	}

	@Override
	public Integer countFinalPrice(Coupon coupon, int price) {
		return price - countDiscountAmount(coupon, price);
	}
	
	private TYPE getType(Coupon coupon){
		TYPE returnType = null;
		for (int i = 0; i < TYPE.values().length; i++) {
			TYPE type = TYPE.values()[i];
			if(coupon.getCouponType().getOptionName().toLowerCase()
					.contains(type.name().toLowerCase()))
				returnType =  type;
		}
		return returnType;
	}

	

}
