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

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class CustomerControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
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
	public void addCustomerAndGetByCustomerId() throws Exception {
		
		Village village = staticDataService.getVillage("1000402-002");

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setVillage(village);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
		Customer newCustomer = new Customer();
		newCustomer.setEmail("u9734017@gmail.com");
		newCustomer.setPassword("123456");
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(newCustomer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addCustomerAndGetByEmail() throws Exception {
		
		Village village = staticDataService.getVillage("1000402-002");

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setVillage(village);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
		Customer newCustomer = new Customer();
		newCustomer.setEmail("u9734017@gmail.com");
		
		this.mockMvc.perform(post("/customerDataCtrl/customers/email")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(newCustomer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
	}


}
