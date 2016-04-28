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

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;
import org.junit.Assert;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

@WebAppConfiguration
public class LoginControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Inject
	private DataUtil dataUtil;
	@Inject
	private LoginService loginService;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }

	@Test
	@Transactional
	@Rollback(true)
	public void signupAndLoignTest() throws Exception {
		
		Customer loginCustomer = loginService.signup(dataUtil.getSignupCustomer());
		Assert.assertEquals(loginCustomer.getEmail(), dataUtil.getSignupCustomer().getEmail());
	}
	
	@Test(expected = HttpServiceException.class)  
	@Transactional
	@Rollback(true)
	public void forgetPassword() throws Exception {
		
		Customer loginCustomer = loginService.signup(dataUtil.getSignupCustomer());
		
		this.mockMvc.perform(post("/loginCtrl/forgetPassword")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isOk());
		
		loginCustomer = loginService.login(dataUtil.getSignupCustomer().getEmail(), dataUtil.getSignupCustomer().getPassword());
		
	}
	
}
