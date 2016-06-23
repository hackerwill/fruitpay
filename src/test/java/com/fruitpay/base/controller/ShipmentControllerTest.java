package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.model.ShipmentRecordPostBean;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@WebAppConfiguration
public class ShipmentControllerTest extends AbstractSpringJnitTest {
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private
	CustomerService customerService;
	@Inject
	private CustomerOrderService customerOrderService;
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
	@Transactional
	@Rollback(true)
	public void addOrderAndAddShipmentRecord() throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		customerOrder.setCustomer(customer);
		customerOrder = customerOrderService.addCustomerOrder(customerOrder);
		
		Assert.assertNotNull(customerOrder.getOrderId());
		
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		customerOrders.add(customerOrder);
		ShipmentRecord shipmentRecord = dataUtil.getshipmentRecord();
		ShipmentRecordPostBean shipmentRecordPostBean = new ShipmentRecordPostBean();
		shipmentRecordPostBean.setShipmentRecord(shipmentRecord);
		shipmentRecordPostBean.setOrderIds(customerOrders.stream().map(order -> order.getOrderId()).collect(Collectors.toList()));
		
		this.mockMvc.perform(post("/shipmentCtrl/shipmentRecord")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(shipmentRecordPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentRecordId", notNullValue()));
		
		String date = DateUtil.parseDate(new Date(), "yyyy-MM-dd");
		this.mockMvc.perform(get("/shipmentCtrl/shipmentRecord/date/" + date)
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentRecordId", notNullValue()));
		
		//再插入資料一次 系統應該將原本資料改成無效
		this.mockMvc.perform(post("/shipmentCtrl/shipmentRecord")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(shipmentRecordPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentRecordId", notNullValue()));
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentRecord/date/" + date)
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentRecordId", notNullValue()));
		
	}
	
}
