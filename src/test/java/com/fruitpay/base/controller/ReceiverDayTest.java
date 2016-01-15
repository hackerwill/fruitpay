package com.fruitpay.base.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.DayOfWeek;
import java.util.Calendar;

import javax.inject.Inject;

@WebAppConfiguration
public class ReceiverDayTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Mock
	private EmailSendService emailSendServiceMock;
	
	@Inject
	DataUtil dataUtil;
	@Inject 
	private StaticDataService staticDataService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        //this.mockMvc = MockMvcBuilders.standaloneSetup(checkoutControllerMock)
        		.build();
 
    }

	@Test
	public void getReceiveDay() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.set(2015, 11, 20, 0, 0);
		cal.getTime();
		String nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.WEDNESDAY);
		Assert.assertEquals("12-30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 21, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.WEDNESDAY);
		Assert.assertEquals("12-30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 22, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.WEDNESDAY);
		Assert.assertEquals("12-30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 25, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.WEDNESDAY);
		Assert.assertEquals("12-30", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2016, 00, 03, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.WEDNESDAY);
		Assert.assertEquals("01-13", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2016, 00, 03, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.MONDAY);
		Assert.assertEquals("01-11", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 31, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.MONDAY);
		Assert.assertEquals("01-11", nextReceiveDay);
		
		cal = Calendar.getInstance();
		cal.set(2015, 11, 30, 0, 0);
		cal.getTime();
		nextReceiveDay = staticDataService.getNextReceiveDay(cal.getTime(), DayOfWeek.MONDAY);
		Assert.assertEquals("01-04", nextReceiveDay);
		
	}

}
