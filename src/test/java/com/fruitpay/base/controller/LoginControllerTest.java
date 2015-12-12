package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import javax.inject.Inject;

@WebAppConfiguration
public class LoginControllerTest extends AbstractSpringJnitTest{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	//@InjectMocks
	//LoginController loginController;
	
	@Inject
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        //this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }

	//@Test
	public void loginByEmail() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		
		
		//when(staticDataServiceMock.getAllCounties()).thenReturn(Arrays.asList(first, second));
	
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
			//logger.info(returnCustomer.getObject().getCustomerId());
		
		//verify(staticDataServiceMock, times(1)).getAllCounties();
	    //verifyNoMoreInteractions(staticDataServiceMock);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void signupTest() throws Exception {
		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		
		//when(staticDataServiceMock.getAllCounties()).thenReturn(Arrays.asList(first, second));
	
		this.mockMvc.perform(post("/loginCtrl/signup")
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
		
			//logger.info(returnCustomer.getObject().getCustomerId());
		
		//verify(staticDataServiceMock, times(1)).getAllCounties();
	    //verifyNoMoreInteractions(staticDataServiceMock);
	}
}
