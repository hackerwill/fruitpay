package com.fruitpay.base.service;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

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

import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerClaimStatus;
import com.fruitpay.base.model.CustomerOrder;

@WebAppConfiguration
public class CustomerClaimServiceTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private CustomerClaimService customerClaimService;
	@Inject
	private CustomerService customerService;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject 
	private DataUtil dataUtil;
	
	private MockMvc mockMvc;
	
	@Before
	@Transactional
	@Rollback(true)
    public void setup() throws Exception {
		// Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
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
		customerClaim = customerClaimService.add(customerClaim);
		
		Assert.assertTrue(customerClaim.getClaimId() != null);
		Assert.assertTrue(customerClaim.getCustomerClaimStatuses().get(0).getClaimStatusId() != null);
		
		//Add new Status
		customerClaim.add(dataUtil.getCustomerClaimStatus());
		customerClaim = customerClaimService.update(customerClaim);
		List<CustomerClaimStatus> customerClaimStatuses = customerClaim.getCustomerClaimStatuses();
		customerClaimStatuses.forEach(status -> {
			Assert.assertTrue(status.getClaimStatusId() != null);
		});
	}

}
