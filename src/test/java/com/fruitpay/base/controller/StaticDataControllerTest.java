package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fruitpay.base.model.PostalCode;
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

public class StaticDataControllerTest extends AbstractSpringJnitTest{
	
	@InjectMocks
	StaticDataController staticDataController;
	@Mock
	StaticDataService staticDataServiceMock;
	@Inject
	StaticDataService staticDataService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(staticDataController).build();
 
    }
	
	@Test
	public void findAllTowerships() throws Exception {
		List<PostalCode> postCodes = staticDataService.getAllPostalCodes();
		Assert.assertEquals(367, postCodes.size());
	}
}
