package com.fruitpay.comm;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.service.StaticDataService;

@Component
public class DataUtil {
	
	@Inject
	StaticDataService staticDataService;
	
	
	public Coupon getCoupon(){
		Constant couponTypes = staticDataService.getConstant(8);
		Constant yesOrNo = staticDataService.getConstant(7);
		
		Coupon coupon = new Coupon();
		coupon.setCouponName("test");
		coupon.setCouponType(couponTypes.getConstOptions().get(0));
		coupon.setExpiryDay(new Date());
		coupon.setValue(10);
		coupon.setUsageIndividually(yesOrNo.getConstOptions().get(0));
		return coupon;
	}
	
	public Customer getSignupCustomer(){
		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		return customer;
	}
	
	public Customer getCheckoutCustomer(){
		PostalCode postalCode = staticDataService.getPostalCode(100);

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setPostalCode(postalCode);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		
		return customer;
	}
	
	public Customer getBackgroundCustomer(){
		PostalCode postalCode = staticDataService.getPostalCode(100);

		Customer customer = new Customer();
		customer.setEmail("u9734017@gmail.com");
		customer.setPassword("123456");
		customer.setFirstName("瑋志");
		customer.setLastName("徐");
		customer.setGender("M");
		customer.setPostalCode(postalCode);
		customer.setAddress("同安村西畔巷66弄40號");
		customer.setCellphone("0933370691");
		customer.setHousePhone("048238111");
		customer.setPassword("123456");
		
		return customer;
	}
	
	public CustomerOrder getCustomerOrder(){
		
		PostalCode postalCode = staticDataService.getPostalCode(100);
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
		
		Constant deliveryDays =  staticDataService.getConstant(6);
		ConstantOption deliveryDay = deliveryDays.getConstOptions().get(0);
		
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderPlatform(orderPlatform);
		customerOrder.setOrderProgram(orderProgram);
		customerOrder.setReceiverLastName("徐");
		customerOrder.setReceiverFirstName("瑋志");
		customerOrder.setReceiverGender("M");
		customerOrder.setReceiverCellphone("0933370691");
		customerOrder.setReceiverHousePhone("048238111");
		customerOrder.setPostalCode(postalCode);
		customerOrder.setReceiverAddress("同安村西畔巷66弄40號");
		customerOrder.setReceiptTitle("抬頭");
		customerOrder.setReceiptVatNumber("123456789");
		customerOrder.setPaymentMode(paymentMode);
		customerOrder.setShipmentPeriod(shipmentPeriod);
		customerOrder.setShipmentDay(shipmentDay);
		customerOrder.setReceiveWay(receiveWay);
		customerOrder.setShipmentTime(shipmentTime);
		customerOrder.setComingFrom(comingFrom);
		customerOrder.setReceiptWay(receiptWay);
		customerOrder.setDeliveryDay(deliveryDay);
		customerOrder.setAllowForeignFruits("Y");
		customerOrder.setProgramNum(1);
		
		int shippingCost = paymentMode.getPaymentExtraPrice();
		int totalPrice = orderProgram.getPrice() * customerOrder.getProgramNum() + shippingCost;	
		customerOrder.setShippingCost(shippingCost);
		customerOrder.setTotalPrice(totalPrice);
		
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
