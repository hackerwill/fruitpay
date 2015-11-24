package com.fruitpay.base.service;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.ReturnData;

public interface CheckoutService {
	
	/**
	 * 新增訂單
	 * 
	 * */
	public CustomerOrder checkoutOrder(CustomerOrder customerOrder);
	
	/**
	 * 由訂單ID得到顧客訂單
	 * 
	 * */
	public CustomerOrder getCustomerOrder(Integer orderId);
	
	/**
	 * 更新某筆訂單的狀態
	 * 
	 * */
	public Boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus);
	
	/**
	 * 增加顧客，並且增加訂單
	 * 
	 * */
	public ReturnData<CustomerOrder> checkoutOrder(Customer customer, CustomerOrder customerOrder);
	
}
