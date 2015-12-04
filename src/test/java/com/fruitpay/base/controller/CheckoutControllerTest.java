package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
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
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderPreferencePK;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;

import javax.inject.Inject;

@WebAppConfiguration
public class CheckoutControllerTest extends AbstractSpringJnitTest{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	private StaticDataService staticDataService;
	
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
	public void checkout() throws Exception {
		
		Village village = staticDataService.getVillage("1000402-002");
		OrderPlatform orderPlatform = staticDataService.getOrderPlatform(1);
		OrderProgram orderProgram = staticDataService.getOrderProgram(1);
		PaymentMode paymentMode = staticDataService.getPaymentMode(1);
		ShipmentPeriod shipmentPeriod = staticDataService.getShipmentPeriod(1);
		ShipmentDay shipmentDay = staticDataService.getShipmentDay(2);
		List<Product> products = staticDataService.getAllProducts();
		
		Constant receiveWays =  staticDataService.getConstant(1);
		ConstantOption receiveWay = receiveWays.getConstOptions().get(0);
		
		Constant shipmentTimes =  staticDataService.getConstant(2);
		ConstantOption shipmentTime = shipmentTimes.getConstOptions().get(0);
		
		Constant comingFroms =  staticDataService.getConstant(3);
		ConstantOption comingFrom = comingFroms.getConstOptions().get(0);
		
		Constant receiptWays =  staticDataService.getConstant(4);
		ConstantOption receiptWay = receiptWays.getConstOptions().get(0);

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setVillage(village);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setCustomer(customer);
		customerOrder.setOrderPlatform(orderPlatform);
		customerOrder.setOrderProgram(orderProgram);
		customerOrder.setReceiverLastName("徐");
		customerOrder.setReceiverFirstName("瑋志");
		customerOrder.setReceiverGender("M");
		customerOrder.setReceiverCellphone("0933370691");
		customerOrder.setReceiverHousePhone("048238111");
		customerOrder.setVillage(village);
		customerOrder.setReceiverAddress("同安村西畔巷66弄40號");
		customerOrder.setPaymentMode(paymentMode);
		customerOrder.setShipmentPeriod(shipmentPeriod);
		customerOrder.setShipmentDay(shipmentDay);
		customerOrder.setReceiveWay(receiveWay);
		customerOrder.setShipmentTime(shipmentTime);
		customerOrder.setComingFrom(comingFrom);
		customerOrder.setReceiptWay(receiptWay);
		customerOrder.setAllowForeignFruits("Y");
		
		List<OrderPreference> orderPreferences = new ArrayList<>();
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			OrderPreference orderPreference = new OrderPreference();
			orderPreference.setProduct(product);
			OrderPreferencePK id = new OrderPreferencePK();
			id.setProductId(product.getProductId());
			orderPreference.setId(id);
			orderPreference.setLikeDegree(Byte.parseByte("5"));
			orderPreferences.add(orderPreference);
		}

		customerOrder.setOrderPreferences(orderPreferences);
		
		CheckoutPostBean checkoutPostBean = new CheckoutPostBean();
		checkoutPostBean.setCustomer(customer);
		checkoutPostBean.setCustomerOrder(customerOrder);
		
		this.mockMvc.perform(post("/checkoutCtrl/checkout")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(checkoutPostBean)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.errorCode", is("0")));
		
		Customer newCustomer = new Customer();
		newCustomer.setEmail("u9734017@gmail.com");
		newCustomer.setPassword("123456");
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(newCustomer)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$.message", is("信箱與密碼不符")));
		
	}

}
