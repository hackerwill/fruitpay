package com.fruitpay.base.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;

public interface StaticDataService {
	
	public List<PostalCode> getAllPostalCodes();
	
	public PostalCode getPostalCode(Integer postId);
	
	public List<Product> getAllProducts();
	
	public List<OrderPlatform> getAllOrderPlatform();
	
	public OrderPlatform getOrderPlatform(Integer platformId);
	
	public List<OrderProgram> getAllOrderProgram();
	
	public OrderProgram getOrderProgram(Integer programId);
	
	public List<OrderStatus> getAllOrderStatus();
	
	public OrderStatus getOrderStatus(Integer orderStatusId);
	
	public List<PaymentMode> getAllPaymentMode();
	
	public PaymentMode getPaymentMode(Integer paymentModeId);
	
	public List<ShipmentPeriod> getAllShipmentPeriod();
	
	public ShipmentPeriod getShipmentPeriod(Integer periodId);
	
	public List<Constant> getAllConstants();
	
	public Constant getConstant(Integer constId);
	
	public ConstantOption getConstantOptionByName(String optionName);
	
	public List<ShipmentDay> getAllShipmentDays();
	
	public ShipmentDay getShipmentDay(Integer shipmentDaysId);
	
	public String getNextReceiveDayStr(Date nowTime, DayOfWeek dayOfWeek);
	
	public LocalDate getNextReceiveDay(Date nowTime, DayOfWeek dayOfWeek);


}
