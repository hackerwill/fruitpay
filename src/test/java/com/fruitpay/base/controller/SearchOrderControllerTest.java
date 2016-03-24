package com.fruitpay.base.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

@WebAppConfiguration
public class SearchOrderControllerTest extends AbstractSpringJnitTest{
	
	private MockMvc mockMvc;
	
	@Inject
    private WebApplicationContext webApplicationContext;
	
	@Before
    public void setup() {
 
        // Setup Spring test in standalone mode
		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }
	
	@Test
	public void testSearchMethod() throws Exception {
		
		String orderId = "8011";
		String name = "34";
		String page = "0";
		String size = "10000";
		String startDate = "1990-01-01 00:00:00";
		String endDate = "2090-01-01 00:00:00";
		String validFlag = "1";
		String allowFreignFruits = "Y";
		
		String url = "/orderCtrl/orders";
		
		this.mockMvc.perform(get(url)
				.param("orderId", orderId)
				.param("name", name)
				.param("page", page)
				.param("size", size)
				.param("startDate", startDate)
				.param("endDate", endDate)
				)
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
	}
}
