package com.fruitpay.base.controller;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.CouponService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class CheckoutControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Mock
	private EmailSendService emailSendServiceMock;
	
	@Inject
	DataUtil dataUtil;
	@Inject
	CouponService couponService;
	@Inject
	CheckoutService checkoutService;
	@Inject
	CustomerService customerService;
	@Inject
	private LoginService loginService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        //this.mockMvc = MockMvcBuilders.standaloneSetup(checkoutControllerMock)
        		.build();
 
    }

	@Test
	@Transactional
	@Rollback(true)
	public void checkout() throws Exception {
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getCheckoutCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getCheckoutCustomer().getEmail())));
		
		Customer customer = customerService.findByEmail(dataUtil.getCheckoutCustomer().getEmail());
		
		customerOrder.setCoupons(new ArrayList<Coupon>());
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/checkoutCtrl/checkout")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(dataUtil.getCustomerOrder().getReceiverCellphone())));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void checkoutWithCoupon() throws Exception {
		
		//add
		this.mockMvc.perform(post("/couponCtrl/coupon")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getByPercentageCoupon(10))))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.couponName", is(dataUtil.getByPercentageCoupon(10).getCouponName())))
	   		.andExpect(jsonPath("$.value", is(dataUtil.getByPercentageCoupon(10).getValue())));
		
		Coupon coupon = couponService.findByCouponName(dataUtil.getByPercentageCoupon(10).getCouponName());
		List<Coupon> coupons = new ArrayList<Coupon>() ;
		coupons.add(coupon);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		customerOrder.setCoupons(coupons);
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getCheckoutCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getCheckoutCustomer().getEmail())));
		
		Customer customer = customerService.findByEmail(dataUtil.getCheckoutCustomer().getEmail());
		
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		double discount = (100 - customerOrder.getCoupons().get(0).getValue()) / 100.0;
		int total = (int)((customerOrder.getTotalPrice() * (CommConst.CREDIT_CARD_PERIOD.PERIOD.value() / customerOrder.getShipmentPeriod().getDuration()) * discount));
		this.mockMvc.perform(post("/checkoutCtrl/checkout")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(dataUtil.getCustomerOrder().getReceiverCellphone())))
	   		.andExpect(jsonPath("$.totalPrice", is(total)));
		
	}
	
	@Test 
	public void calculateTotalPriceWithCoupon() throws Exception{
		
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		customerOrder.setCoupons(dataUtil.getCouponList());
		double discount = (100 - customerOrder.getCoupons().get(0).getValue()) / 100.0;
		int total = (int)((customerOrder.getTotalPrice() * (CommConst.CREDIT_CARD_PERIOD.PERIOD.value() / customerOrder.getShipmentPeriod().getDuration()) * discount));
		this.mockMvc.perform(post("/checkoutCtrl/totalPrice")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customerOrder)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().string(String.valueOf(total)));
	} 

}
