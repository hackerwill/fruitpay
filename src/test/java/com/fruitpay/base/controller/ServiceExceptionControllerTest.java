package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.junit.Assert;
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

import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class ServiceExceptionControllerTest extends AbstractSpringJnitTest{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
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
	public void testNullPointerException() throws Exception {
		this.mockMvc.perform(get("/staticDataCtrl/exceptionHandleTest"))
	   		.andExpect(status().isInternalServerError());
	}

}
