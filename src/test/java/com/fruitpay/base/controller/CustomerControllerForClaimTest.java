package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.service.CustomerClaimService;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.OrderPreferenceService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class CustomerControllerForClaimTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private
	CustomerService customerService;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private ShipmentService shipmentService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private OrderPreferenceService orderPreferenceService;
	@Inject
	private CustomerClaimService customerClaimService;
	@Inject
	private DataUtil dataUtil;
	
	private MockMvc mockMvc;
	
	@Before
	@Transactional
	@Rollback(true)
    public void setup() throws Exception {
 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
        addOrder();
    }
	
	@Transactional
	private void addOrder() throws Exception {
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(dataUtil.getCustomerOrder());
		
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddCustomerClaimAndAddNewStatusType() throws Exception {
		Customer customer = customerService.findByEmail(dataUtil.getBackgroundCustomer().getEmail());
		CustomerOrder customerOrder = customerOrderService
				.getCustomerOrdersByCustomerId(customer.getCustomerId())
				.get(0);
		CustomerClaim customerClaim = dataUtil.getCustomerClaim();
		customerClaim.setCustomerOrder(customerOrder);
		customerClaim.setCustomer(customer);
		
		this.mockMvc.perform(post("/customerDataCtrl/customerClaim")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customerClaim)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.claimId", notNullValue()))
	   		.andExpect(jsonPath("$.customerClaimStatuses", hasSize(1)));
		
		List<CustomerClaim> customerClaims = customerClaimService.findByCustomerIdIncludingStatuses(customer.getCustomerId());
		Assert.assertTrue(customerClaims.size() > 0); 
		customerClaim = customerClaims.get(0);
		
		//Add new status
		customerClaim.add(dataUtil.getCustomerClaimStatus());
		
		this.mockMvc.perform(put("/customerDataCtrl/customerClaim")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customerClaim)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.customerClaimStatuses", hasSize(2)));
		
	}
	
}
