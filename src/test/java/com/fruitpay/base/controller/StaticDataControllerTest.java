package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

public class StaticDataControllerTest extends AbstractSpringJnitTest{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@InjectMocks
	StaticDataController staticDataController;
	@Mock
	StaticDataService staticDataServiceMock;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(staticDataController).build();
 
    }

	@Test
	public void findAllCustomer() throws Exception {
		/*Customer newCustomer = new Customer();
		newCustomer.setEmail("u9734017@gmail.com");
		newCustomer.setPassword("123456");
		newCustomer.setFirstName("偉志");*/
		SelectOption first = new SelectOption(1, "123");
		SelectOption second = new SelectOption(2, "345");
		
		
		when(staticDataServiceMock.getAllCounties()).thenReturn(Arrays.asList(first, second));
	
		this.mockMvc.perform(get("/staticDataCtrl/getAllCounties"))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")))
	   		.andExpect(jsonPath("$.object", hasSize(2)))
			.andExpect(jsonPath("$.object[0].id", is("1")))
			.andExpect(jsonPath("$.object[0].name", is("123")))
			.andExpect(jsonPath("$.object[1].id", is("2")))
			.andExpect(jsonPath("$.object[1].name", is("345")));
		
			//logger.info(returnCustomer.getObject().getCustomerId());
		
		verify(staticDataServiceMock, times(1)).getAllCounties();
	    verifyNoMoreInteractions(staticDataServiceMock);
	}
}
