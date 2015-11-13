package com.fruitpay.base.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject 
	CustomerOrderDAO customerOrderDAO;
	
	@Override
	public ReturnData<CustomerOrder> getCustomerOrder(Integer orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findById(orderId);
		if(customerOrder == null)
			return ReturnMessageEnum.Order.OrderNotFound.getReturnMessage();
		return new ReturnObject<CustomerOrder>(customerOrder);
	}

	@Override
	public ReturnData<List<CustomerOrder>> getCustomerOrdersByCustomerId(Integer customerId) {
		List<CustomerOrder> customerOrders = customerOrderDAO.findByCustomerId(customerId);
		if(customerOrders.isEmpty())
			return ReturnMessageEnum.Order.OrderNotFound.getReturnMessage();
		return new ReturnObject<List<CustomerOrder>>(customerOrders);
	}

}
