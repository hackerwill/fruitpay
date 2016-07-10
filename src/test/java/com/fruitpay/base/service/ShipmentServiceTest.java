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

import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ProductItem;
import com.fruitpay.base.model.ProductStatusBean;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.model.StatusInteger;
import com.fruitpay.comm.utils.DateUtil;
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
	public void testCalculateDailyRecord() throws Exception {
		boolean flag = customerOrderService.calculateDailyRecord(LocalDate.now());
		Assert.assertTrue(flag);
	}
	
	@Test
	@Transactional
	public void testGetOneOrderNextShipmentDate() throws Exception {
		Customer customer = customerService.findByEmail(dataUtil.getBackgroundCustomer().getEmail());
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder customerOrder = customerOrders.get(0);
		DayOfWeek dayOfWeek = DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()));
		int orderId = customerOrder.getOrderId();
		List<ShipmentChange> shipmentChanges = shipmentService.findShipmentChangesByOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentService.findShipmentRecordDetailsByOrderId(orderId);
		LocalDate nextDate = shipmentService.getNextNeedShipmentDate(customerOrder, shipmentChanges, shipmentRecordDetails);
		Assert.assertTrue(nextDate.getDayOfWeek().equals(dayOfWeek));
	}
	
	@Test
	@Transactional
	public void testGetOneOrderNextShipmentDateAndSetTodayAsShipmentDate() throws Exception {
		ConstantOption shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		Customer customer = customerService.findByEmail(dataUtil.getBackgroundCustomer().getEmail());
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder customerOrder = customerOrders.get(0);
		LocalDate now = LocalDate.now();
		ShipmentChange shipmentChange = new ShipmentChange();
		shipmentChange.setShipmentChangeType(shipmentDeliver);
		shipmentChange.setApplyDate(DateUtil.toDate(now));
		shipmentChange.setCustomerOrder(customerOrder);
		shipmentService.add(shipmentChange);
		int orderId = customerOrder.getOrderId();
		List<ShipmentChange> shipmentChanges = shipmentService.findShipmentChangesByOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentService.findShipmentRecordDetailsByOrderId(orderId);
		LocalDate nextDate = shipmentService.getNextNeedShipmentDate(customerOrder, shipmentChanges, shipmentRecordDetails);
		Assert.assertTrue(nextDate.equals(now));
	}
	
	@Test
	@Transactional
	public void testWithCountShipmentTotal() throws Exception {
		ConstantOption shipmentDelivered = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDelivered.toString());
		Customer customer = customerService.findByEmail(dataUtil.getBackgroundCustomer().getEmail());
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder customerOrder = customerOrders.get(0);
		//Add three shipment delivered
		LocalDate now = LocalDate.now();
		LocalDate nextWeek = now.plusDays(7);
		LocalDate nextTwoWeek = now.plusDays(14);
		LocalDate nextThreeWeekLastWeek = now.plusDays(21);
		ShipmentChange shipmentChangeNextWeek = new ShipmentChange();
		shipmentChangeNextWeek.setShipmentChangeType(shipmentDelivered);
		shipmentChangeNextWeek.setApplyDate(DateUtil.toDate(nextWeek));
		shipmentChangeNextWeek.setCustomerOrder(customerOrder);
		shipmentService.add(shipmentChangeNextWeek);
		
		ShipmentChange shipmentChangeNextTwoWeek = new ShipmentChange();
		shipmentChangeNextTwoWeek.setShipmentChangeType(shipmentDelivered);
		shipmentChangeNextTwoWeek.setApplyDate(DateUtil.toDate(nextTwoWeek));
		shipmentChangeNextTwoWeek.setCustomerOrder(customerOrder);
		shipmentService.add(shipmentChangeNextTwoWeek);
		
		ShipmentChange shipmentChangeNextThreeWeek = new ShipmentChange();
		shipmentChangeNextThreeWeek.setShipmentChangeType(shipmentDelivered);
		shipmentChangeNextThreeWeek.setApplyDate(DateUtil.toDate(nextThreeWeekLastWeek));
		shipmentChangeNextThreeWeek.setCustomerOrder(customerOrder);
		shipmentService.add(shipmentChangeNextThreeWeek);
		
		int shipmentTimes = shipmentService.countShipmentTimes(customerOrder);
		Assert.assertEquals(3, shipmentTimes);
		
		
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
	public void testWithCalculateShipmentPreferencesAndModifiedStates() throws Exception {
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
		Integer num = 2;
		requiredAmount.setInteger(num);
		productStatusBeans.get(0).setRequiredAmount(requiredAmount);
		
		shipmentPreferenceBean = shipmentService.calculate(shipmentPreferenceBean, productIds);
		Assert.assertEquals(num, productStatusBeans.get(0).getRequiredAmount().getInteger());
		
		shipmentPreferenceBean = shipmentService.calculate(shipmentPreferenceBean, productIds);
		Assert.assertTrue(shipmentPreferenceBean.getChosenProductItemBeans().get(0).getActualTotal() >= num);
	
	}
	
}
