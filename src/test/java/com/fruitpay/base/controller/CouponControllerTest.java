package com.fruitpay.base.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import javax.inject.Inject;

public class CouponControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	DataUtil dataUtil;
	
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
	public void addCouponAndDelete() throws Exception {
		
		Coupon coupon = dataUtil.getCoupon();
		
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(coupon)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getCoupon().getCouponName())))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getCoupon().getValue())));
		
	}
}
