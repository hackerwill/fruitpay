package com.fruitpay.base.dao;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderDAO extends DAO<CustomerOrder> {
	
	/**
	 * 更新某筆訂單的任務狀態
	 * 
	 * */
	public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus);
	
}
