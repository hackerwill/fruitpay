package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.util.AbstractSpringJnitTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

@WebAppConfiguration
public class ServiceExceptionControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
    }
	
	@Test
	public void testHttpServiceException() throws Exception {
		this.mockMvc.perform(get("/staticDataCtrl/exceptionHandleTest"))
	   		.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void exceptionHandleArithmeticExceptionTest() throws Exception {
		this.mockMvc.perform(get("/staticDataCtrl/exceptionHandleArithmeticExceptionTest"))
	   		.andExpect(status().isInternalServerError());
	}

}
