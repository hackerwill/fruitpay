package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class CouponControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private DataUtil dataUtil;
	@Inject
	CouponService couponService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
    }
	
	@Test
	@Rollback(true)
	@Transactional
	public void addCouponGetByCouponName() throws Exception {
		Coupon coupon = dataUtil.getByPercentageCoupon(10);
		
		//add
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(coupon)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getByPercentageCoupon(10).getCouponName())))
	   		.andExpect(jsonPath("$.value", is(coupon.getValue())));
		
		//find
		this.mockMvc.perform(get("/couponCtrl/coupon/name/" + coupon.getCouponName()))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(coupon.getCouponName())));
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCouponByAmount100Calculation() throws Exception {
		Coupon coupon = dataUtil.getByAmountCoupon(100);
		int price = 400;
		
		double discountPercentage = couponService.countDiscountPercentage(coupon, price);
		Assert.assertEquals(0.75, discountPercentage, 0.0001);
		
		double discountPercentOff = couponService.countDiscountPercentOff(coupon, price);
		Assert.assertEquals(0.25, discountPercentOff, 0.0001);
		
		int discountAmount = couponService.countDiscountAmount(coupon, price);
		Assert.assertEquals(100, discountAmount);
		
		int finalPrice = couponService.countFinalPrice(coupon, price);
		Assert.assertEquals(300, finalPrice);
		
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCouponByAmount88Calculation() throws Exception {
		Coupon coupon = dataUtil.getByAmountCoupon(88);
		int price = 499;
		
		double discountPercentage = couponService.countDiscountPercentage(coupon, price);
		Assert.assertEquals(0.82, discountPercentage, 0.0001);
		
		double discountPercentOff = couponService.countDiscountPercentOff(coupon, price);
		Assert.assertEquals(0.18, discountPercentOff, 0.0001);
		
		int discountAmount = couponService.countDiscountAmount(coupon, price);
		Assert.assertEquals(88, discountAmount);
		
		int finalPrice = couponService.countFinalPrice(coupon, price);
		Assert.assertEquals(411, finalPrice);
		
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCouponByPercetage10Calculation() throws Exception {
		Coupon coupon = dataUtil.getByPercentageCoupon(10);
		int price = 400;
		
		double discountPercentage = couponService.countDiscountPercentage(coupon, price);
		Assert.assertEquals(0.9, discountPercentage, 0.0001);
		
		double discountPercentOff = couponService.countDiscountPercentOff(coupon, price);
		Assert.assertEquals(0.1, discountPercentOff, 0.0001);
		
		int discountAmount = couponService.countDiscountAmount(coupon, price);
		Assert.assertEquals(40, discountAmount);
		
		int finalPrice = couponService.countFinalPrice(coupon, price);
		Assert.assertEquals(360, finalPrice);
		
	}
	
	@Test
	@Rollback(true)
	@Transactional
	public void testCouponByPercetage12Calculation() throws Exception {
		Coupon coupon = dataUtil.getByPercentageCoupon(12);
		int price = 499;
		
		double discountPercentage = couponService.countDiscountPercentage(coupon, price);
		Assert.assertEquals(0.88, discountPercentage, 0.0001);
		
		double discountPercentOff = couponService.countDiscountPercentOff(coupon, price);
		Assert.assertEquals(0.12, discountPercentOff, 0.0001);
		
		int discountAmount = couponService.countDiscountAmount(coupon, price);
		Assert.assertEquals(60, discountAmount);
		
		int finalPrice = couponService.countFinalPrice(coupon, price);
		Assert.assertEquals(439, finalPrice);
	}
	
	
	
	/*@Test
	@Rollback(true)
	@Transactional*/
	public void addCouponAndUpdateAndDelete() throws Exception {
		
		Coupon coupon = dataUtil.getByPercentageCoupon(10);
		
		//add
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(coupon)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getByPercentageCoupon(10).getCouponName())))
	   		.andExpect(jsonPath("$.value", is(dataUtil.getByPercentageCoupon(10).getValue())));
		
		//findall
		this.mockMvc.perform(get("/couponCtrl/coupons"))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(1)));
		
		Page<Coupon> coupons = couponService.findAll(10, 10);
		Coupon updateOne = coupons.getContent().get(0);
		updateOne.setCouponName("UPDATE_TEST");
		
		//update
		this.mockMvc.perform(put("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updateOne)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(updateOne.getCouponName())))
	   		.andExpect(jsonPath("$.value", is(dataUtil.getByPercentageCoupon(10).getValue())));
		
		//delete
		this.mockMvc.perform(delete("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updateOne)))
	   		.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/couponCtrl/coupons"))
   		.andExpect(status().isOk())
   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
   		.andExpect(jsonPath("$", hasSize(0)));
		
	}
	
}
