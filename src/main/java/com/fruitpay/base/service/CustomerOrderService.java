package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.ReturnData;

public interface CustomerOrderService {

	public ReturnData<CustomerOrder> getCustomerOrder(Integer orderId);
	
	public ReturnData<List<CustomerOrder>> getCustomerOrdersByCustomerId(Integer customerId);

}
