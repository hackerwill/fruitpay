package com.fruitpay.base.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
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
		Coupon coupon = dataUtil.getCoupon();
		
		//add
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(coupon)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getCoupon().getCouponName())))
	   		.andExpect(jsonPath("$.value", is(coupon.getValue())));
		
		//find
		this.mockMvc.perform(get("/couponCtrl/coupon/name/" + coupon.getCouponName()))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(coupon.getCouponName())));
		
	}
	
	/*@Test
	@Rollback(true)
	@Transactional*/
	public void addCouponAndUpdateAndDelete() throws Exception {
		
		Coupon coupon = dataUtil.getCoupon();
		
		//add
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(coupon)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getCoupon().getCouponName())))
	   		.andExpect(jsonPath("$.value", is(dataUtil.getCoupon().getValue())));
		
		//findall
		this.mockMvc.perform(get("/couponCtrl/coupons"))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(1)));
		
		List<Coupon> coupons = couponService.findAll();
		Coupon updateOne = coupons.get(0);
		updateOne.setCouponName("UPDATE_TEST");
		
		//update
		this.mockMvc.perform(put("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(updateOne)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(updateOne.getCouponName())))
	   		.andExpect(jsonPath("$.value", is(dataUtil.getCoupon().getValue())));
		
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
