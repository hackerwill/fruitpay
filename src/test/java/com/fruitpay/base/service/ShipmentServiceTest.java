package com.fruitpay.base.service;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ProductItem;
import com.fruitpay.base.model.ProductStatusBean;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.StatusInteger;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

@WebAppConfiguration
public class ShipmentServiceTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private ShipmentService shipmentService;
	@Inject 
	private CustomerService customerService;
	@Inject
	private StaticDataService staticDataService;
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
        addOrders();
	}
	
	@Transactional
	private void addOrders() throws Exception {
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
		
		checkoutPostBean.setCustomerOrder(dataUtil.getCustomerOrder());
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
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
	public void testWithListAllOrdersByDate() throws Exception {
		LocalDate nextShipmentMonday = staticDataService.getNextReceiveDay(new Date(), DayOfWeek.MONDAY);
		List<Integer> customerOrders =  shipmentService.listAllOrderIdsByDate(nextShipmentMonday);
		Assert.assertTrue(customerOrders.size() > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWithExportShipmentPreferences() throws Exception {
		List<ProductItem> productItems = staticDataService.getAllProductItems();
		LocalDate nextShipmentMonday = staticDataService.getNextReceiveDay(new Date(), DayOfWeek.MONDAY);
		List<String> productIds = productItems.stream()
			.map(productItem -> {
				if(Math.random() < 0.2) {
					return productItem.getCategoryItemId();
				} else {
					return null;
				}
			})
			.filter(product -> product != null)
			.collect(Collectors.toList());
		
		ShipmentPreferenceBean shipmentPreferenceBean =  shipmentService.findInitialShipmentPreference(nextShipmentMonday, productIds);
		
		Assert.assertTrue(shipmentPreferenceBean.getChosenProductItemBeans().size() > 0);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWithCalculateShipmentPreferences() throws Exception {
		List<ProductItem> productItems = staticDataService.getAllProductItems();
		LocalDate nextShipmentMonday = staticDataService.getNextReceiveDay(new Date(), DayOfWeek.MONDAY);
		List<String> productIds = productItems.stream()
			.map(productItem -> {
				if(Math.random() < 0.2) {
					return productItem.getCategoryItemId();
				} else {
					return null;
				}
			})
			.filter(product -> product != null)
			.collect(Collectors.toList());
		
		ShipmentPreferenceBean shipmentPreferenceBean =  shipmentService.findInitialShipmentPreference(nextShipmentMonday, productIds);

		List<ProductStatusBean> productStatusBeans = shipmentPreferenceBean.getChosenProductItemBeans().get(0).getProductStatusBeans();
		StatusInteger requiredAmount = productStatusBeans.get(0).getRequiredAmount();
		requiredAmount.setStatus(StatusInteger.Status.fixed.toString());
		if(requiredAmount.getInteger().equals(0)) {
			requiredAmount.setInteger(1);
		} else {
			requiredAmount.setInteger(0);
		}
		productStatusBeans.get(0).setRequiredAmount(requiredAmount);
		
		shipmentService.calculate(shipmentPreferenceBean);
		
	}
	
}
