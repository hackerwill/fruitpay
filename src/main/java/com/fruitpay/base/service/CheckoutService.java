package com.fruitpay.base.service;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

public interface CheckoutService {
	
	/**
	 * 由訂單ID得到顧客訂單
	 * 
	 * */
	public CustomerOrder getCustomerOrder(Integer customerId, Integer orderId);
	
	/**
	 * 更新某筆訂單的狀態
	 * 
	 * */
	public Boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus, int allpayRtnCode);
	
	/**
	 * 增加顧客，並且增加訂單
	 * 
	 * */
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder);
	
	/**
	 * 計算產品總價格
	 * 
	 * */
	public int getTotalPrice(CustomerOrder customerOrder);
	
	/**
	 * 計算產品總價格(不包含運費)
	 * 
	 * */
	public int getTotalPriceWithoutShipment(CustomerOrder customerOrder);
}
