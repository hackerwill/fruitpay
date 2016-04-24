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

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.dao.CouponDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.DataUtil;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
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
public class CustomerOrderControllerTest extends AbstractSpringJnitTest{
	
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
		
		this.mockMvc.perform(post("/orderCtrl/order")
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
		this.mockMvc.perform(get("/orderCtrl/order/" + orderId)
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
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
		
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		//因為只有後台可以登入，因此會是MethodNotAllowed的狀態
		this.mockMvc.perform(post("/orderCtrl/orders"))
	   		.andExpect(status().isMethodNotAllowed());
		
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
		
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder order = customerOrders.get(0);
		
		String testAddress = "TESTUPDATE";
		order.setReceiverAddress(testAddress);
		
		this.mockMvc.perform(put("/orderCtrl/order")
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
		
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		CustomerOrder order = customerOrders.get(0);
		
		this.mockMvc.perform(delete("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(order)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addOrderAndAddShipmentExchage() throws Exception {
		//原本配送日期加上7天
		int plusDay = 7;
		int searchPlusDay = 30;
		Date startDate = new Date();
		String startDateStr = DateUtil.parseDate(startDate);
		Date endDate = DateUtil.toDate(DateUtil.toLocalDate(startDate).plusDays(searchPlusDay));
		String endDateStr = DateUtil.parseDate(endDate);
		
		CustomerOrder customerOrder = addOrderAndGetFirstOne(dataUtil.getCustomerOrder());
		int orderId = customerOrder.getOrderId();
		
		ShipmentChange shipmentChange = dataUtil.getShipmentChangeWithPulse();
		shipmentChange.setCustomerOrder(customerOrder);
		LocalDate pulseDate = customerOrderService.findOrderFirstDeliveryDate(orderId).plusDays(plusDay);
		shipmentChange.setApplyDate(DateUtil.toDate(pulseDate));
		
		this.mockMvc.perform(post("/shipmentCtrl/shipmentChange")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(shipmentChange)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentChangeType.optionName", is(shipmentChange.getShipmentChangeType().getOptionName())));
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentChange/" + orderId))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(1)));
		
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentPeriod/" + orderId +
				"?startDate=" + startDateStr + 
				"&endDate=" + endDateStr))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(
				startDate, endDate, orderId);
		List<ShipmentDeliveryStatus> compareOnes = getCompareList(shipmentChange.getShipmentChangeType(), customerOrder, endDate, plusDay);
		
		compareLeliveryStatusList(shipmentDeliveryStatuses, compareOnes);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addOrderAndAddShipmentExchageAndTestTwoWeek() throws Exception {
		//原本配送日期加上14天
		int plusDay = 14;
		int searchPlusDay = 50;
		Date startDate = new Date();
		String startDateStr = DateUtil.parseDate(startDate);
		Date endDate = DateUtil.toDate(DateUtil.toLocalDate(startDate).plusDays(searchPlusDay));
		String endDateStr = DateUtil.parseDate(endDate);
		
		CustomerOrder customerOrder = addOrderAndGetFirstOne(dataUtil.getCustomerOrder(14));
		int orderId = customerOrder.getOrderId();
		
		ShipmentChange shipmentChange = dataUtil.getShipmentChangeWithPulse();
		shipmentChange.setCustomerOrder(customerOrder);
		LocalDate pulseDate = customerOrderService.findOrderFirstDeliveryDate(orderId).plusDays(plusDay);
		shipmentChange.setApplyDate(DateUtil.toDate(pulseDate));
		
		this.mockMvc.perform(post("/shipmentCtrl/shipmentChange")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(shipmentChange)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentChangeType.optionName", is(shipmentChange.getShipmentChangeType().getOptionName())));
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentChange/" + orderId))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(1)));
		
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentPeriod/" + orderId +
				"?startDate=" + startDateStr + 
				"&endDate=" + endDateStr))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(
				startDate, endDate, orderId);
		List<ShipmentDeliveryStatus> compareOnes = getCompareList(shipmentChange.getShipmentChangeType(), customerOrder, endDate, plusDay);
		
		compareLeliveryStatusList(shipmentDeliveryStatuses, compareOnes);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void addOrderAndAddShipmentExchageAndTestTwoWeekAndAddCancel() throws Exception {
		//原本配送日期加上14天
		int plusDay = 14;
		int searchPlusDay = 50;
		Date startDate = new Date();
		String startDateStr = DateUtil.parseDate(startDate);
		Date endDate = DateUtil.toDate(DateUtil.toLocalDate(startDate).plusDays(searchPlusDay));
		String endDateStr = DateUtil.parseDate(endDate);
		
		CustomerOrder customerOrder = addOrderAndGetFirstOne(dataUtil.getCustomerOrder(14));
		int orderId = customerOrder.getOrderId();
		
		ShipmentChange shipmentChange = dataUtil.getShipmentChangeWithCancel();
		shipmentChange.setCustomerOrder(customerOrder);
		LocalDate cancelDate = customerOrderService.findOrderFirstDeliveryDate(orderId).plusDays(plusDay);
		shipmentChange.setApplyDate(DateUtil.toDate(cancelDate));
		
		this.mockMvc.perform(post("/shipmentCtrl/shipmentChange")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(shipmentChange)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.shipmentChangeType.optionName", is(shipmentChange.getShipmentChangeType().getOptionName())));
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentChange/" + orderId))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(1)));
		
		
		this.mockMvc.perform(get("/shipmentCtrl/shipmentPeriod/" + orderId +
				"?startDate=" + startDateStr + 
				"&endDate=" + endDateStr))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(
				startDate, endDate, orderId);
		List<ShipmentDeliveryStatus> compareOnes = getCompareList(shipmentChange.getShipmentChangeType(), customerOrder, endDate, plusDay);
		
		compareLeliveryStatusList(shipmentDeliveryStatuses, compareOnes);
	}
	
	private CustomerOrder addOrderAndGetFirstOne(CustomerOrder customerOrder) throws Exception {
		
		Customer customer = dataUtil.getBackgroundCustomer();
		customer = customerService.saveCustomer(customer);
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/orderCtrl/order")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.receiverCellphone", is(customerOrder.getReceiverCellphone())));
		
		List<CustomerOrder> customerOrders = customerOrderService.getCustomerOrdersByCustomerId(customer.getCustomerId());
		customerOrder = customerOrders.get(0);
		
		return customerOrder;
	}
	
	private List<ShipmentDeliveryStatus> getCompareList(ConstantOption shipmentWay, CustomerOrder customerOrder, Date endDate, int plusDay){
		ConstantOption shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		ConstantOption shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString());
		ConstantOption shipmentCancel = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentCancel.toString());
		List<ShipmentDeliveryStatus> compareOnes = new ArrayList<ShipmentDeliveryStatus>();
		LocalDate countDate = customerOrderService.findOrderFirstDeliveryDate(customerOrder.getOrderId());
		int duration = customerOrder.getShipmentPeriod().getDuration();
		int count = 0;
		
		while( !DateUtil.toDate(countDate).after(endDate)){
			
			ShipmentDeliveryStatus thisStatus = new ShipmentDeliveryStatus();
			thisStatus.setApplyDate(DateUtil.toDate(countDate));
			
			if(count == plusDay){
				if(shipmentWay.getOptionId() == shipmentPulse.getOptionId()){
					thisStatus.setShipmentChangeType(shipmentPulse);
				}else if(shipmentWay.getOptionId() == shipmentCancel.getOptionId()){
					thisStatus.setShipmentChangeType(shipmentCancel);
				}
				
				//因應雙週配送預設下週配送
				if(duration == 14) duration = 7;
			}else{
				thisStatus.setShipmentChangeType(shipmentDeliver);
			}
			
			compareOnes.add(thisStatus);
			if(count == plusDay && shipmentWay.getOptionId() == shipmentCancel.getOptionId()) break;
			countDate = countDate.plusDays(duration);
			count = count + duration;
			if(count == plusDay + 7){
				//因應雙週配送預設下週配送
				duration = customerOrder.getShipmentPeriod().getDuration();
			}
		}
		return compareOnes;
	}
	
	private void compareLeliveryStatusList(List<ShipmentDeliveryStatus> statuses, List<ShipmentDeliveryStatus> compareOnes){
		Iterator<ShipmentDeliveryStatus> it = compareOnes.iterator();
		statuses.forEach((status) -> {
			ShipmentDeliveryStatus compareOne = it.next();
			Assert.assertEquals(status.getApplyDate(), compareOne.getApplyDate());
			Assert.assertEquals(status.getShipmentChangeType().getOptionId(), 
					compareOne.getShipmentChangeType().getOptionId()
					);
		});
	}

	
}
