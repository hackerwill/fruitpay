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

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
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
public class CustomerOrderControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private
	CustomerService customerService;
	@Inject
	private
	CustomerOrderService customerOrderService;
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
	public void addOrder() throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/orderCtrl/addOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder order = customerOrders.get(0);
		int orderId = order.getOrderId();
		CustomerOrder searchOrder = new CustomerOrder();
		searchOrder.setOrderId(orderId);
		this.mockMvc.perform(post("/orderCtrl/getOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(searchOrder)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.orderId", is(order.getOrderId())));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addOrderAndGetOrders() throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/orderCtrl/addOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		this.mockMvc.perform(post("/orderCtrl/getOrders"))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void updateOrder() throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/orderCtrl/addOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder order = customerOrders.get(0);
		
		String testAddress = "TESTUPDATE";
		order.setReceiverAddress(testAddress);
		
		this.mockMvc.perform(put("/orderCtrl/updateOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(order)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverAddress", is(order.getReceiverAddress())));
		
	}
	
	@Test(expected = HttpServiceException.class )
	@Transactional
	@Rollback(true)
	public void deleteOrder() throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		CustomerOrder customerOrder = dataUtil.getCustomerOrder();
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/orderCtrl/addOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder order = customerOrders.get(0);
		
		this.mockMvc.perform(delete("/orderCtrl/deleteOrder")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(order)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		
	}
	
	
	
	
}
