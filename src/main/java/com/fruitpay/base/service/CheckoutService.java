package com.fruitpay.base.service;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.AllpayOrder;
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
	public CustomerOrder updateOrderStatus(int orderId, OrderStatus orderStatus, AllpayOrder allpayOrder);
	
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
