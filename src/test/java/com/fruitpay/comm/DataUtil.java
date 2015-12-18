package com.fruitpay.comm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.model.Towership;
import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.StaticDataService;

@Component
public class DataUtil {
	
	@Inject
	StaticDataService staticDataService;
	
	public Customer getSignupCustomer(){
		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		return customer;
	}
	
	public Customer getCheckoutCustomer(){
		Village village = staticDataService.getVillage("1000402-002");
		Towership towership = staticDataService.getTowership("1000402");

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setVillage(village);
		customer.setTowership(towership);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		return customer;
	}
	
	public Customer getBackgroundCustomer(){
		Village village = staticDataService.getVillage("1000402-002");
		Towership towership = staticDataService.getTowership("1000402");

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setVillage(village);
		customer.setTowership(towership);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		customer.setPassword("123456");
		
		return customer;
	}
	
	public CustomerOrder getCustomerOrder(){
		
		Towership towership = staticDataService.getTowership("1000402");
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
		
		CustomerOrder customerOrder = new CustomerOrder();
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
		customerOrder.setTowership(towership);
		
		List<OrderPreference> orderPreferences = new ArrayList<>();
		for (Iterator<Product> iterator = products.iterator(); iterator.hasNext();) {
			Product product = iterator.next();
			OrderPreference orderPreference = new OrderPreference();
			orderPreference.setProduct(product);
			orderPreference.setLikeDegree(Byte.parseByte("5"));
			orderPreference.setCustomerOrder(customerOrder);
			orderPreferences.add(orderPreference);
		}

		customerOrder.setOrderPreferences(orderPreferences);
		
		return customerOrder;
	}

}
