package com.fruitpay.base.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Calendar;

import javax.inject.Inject;

@WebAppConfiguration
public class CheckoutControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	DataUtil dataUtil;
	@Inject 
	private StaticDataService staticDataService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }

	@Test
	@Transactional
	@Rollback(true)
	public void checkout() throws Exception {
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(dataUtil.getCheckoutCustomer());
		checkoutPostBean.setCustomerOrder(dataUtil.getCustomerOrder());
		
		this.mockMvc.perform(post("/checkoutCtrl/checkout")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(dataUtil.getCustomerOrder().getReceiverCellphone())));
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isForbidden())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.message", is("信箱與密碼不符")));
		
	}
	
	@Test
	public void getReceiveDay() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 11, 20, 0, 0);
		cal.getTime();
		String nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime());
		Assert.assertEquals("12/23", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 21, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime());
		Assert.assertEquals("12/30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 22, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime());
		Assert.assertEquals("12/30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 25, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime());
		Assert.assertEquals("12/30", nextReceiveDay);
	}

}
