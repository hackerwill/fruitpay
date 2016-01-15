package com.fruitpay.base.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class CustomerControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private CustomerService customerService;
	@Inject
	private DataUtil dataUtil;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }
	
	@Test
	public void getAllCustomerList() throws Exception {
		int page =0; 
		int size =10;
		Page<Customer> customers = customerService.findAllCustomer(page,size);
		Assert.assertTrue(customers.getContent().size() > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addCustomerAndGetByLogin() throws Exception {
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getCheckoutCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getCheckoutCustomer().getEmail())));
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getSignupCustomer().getEmail())));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addCustomerAndGetByEmail() throws Exception {
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getCheckoutCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getCheckoutCustomer().getEmail())));
		
		this.mockMvc.perform(post("/customerDataCtrl/customers/email")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getSignupCustomer().getEmail())));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addCustomerAndUpdate() throws Exception {
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getCheckoutCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getCheckoutCustomer().getEmail())));
		
		Customer updatedCustomer = customerService.findByEmail(dataUtil.getCheckoutCustomer().getEmail());
		Customer customer = new Customer();
		BeanUtils.copyProperties(updatedCustomer, customer);
		customer.setFirstName("updateName");
		customer.setCustomerId(updatedCustomer.getCustomerId());
		
		this.mockMvc.perform(put("/customerDataCtrl/updateCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(customer.getEmail())))
	   		.andExpect(jsonPath("$.firstName", is(customer.getFirstName())));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void deleteCustomer() throws Exception {
		
		this.mockMvc.perform(post("/customerDataCtrl/addCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.email", is(dataUtil.getSignupCustomer().getEmail())));
		
		Customer customer = customerService.findByEmail(dataUtil.getSignupCustomer().getEmail());
		
		this.mockMvc.perform(delete("/customerDataCtrl/deleteCustomer")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isNotFound())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		
	}


}
