package com.fruitpay.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.Shipment;
import com.fruitpay.base.service.CustomerOrderService;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject 
	CustomerOrderDAO customerOrderDAO;
	@Inject 
	CustomerDAO customerDAO;
	
	@Override
	public CustomerOrder getCustomerOrder(Integer orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		return customerOrder;
	}

	@Override
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId) {
		Customer customer = customerDAO.findOne(customerId);
		if(customer == null)
			throw new HttpServiceException(ReturnMessageEnum.Login.AccountNotFound.getReturnMessage());
		List<CustomerOrder> customerOrders = customerOrderDAO.findByCustomer(customer);
		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		return customerOrders;
	}

	@Override
	@Transactional
	public CustomerOrder updateCustomerOrder(CustomerOrder customerOrder) {
		CustomerOrder origin = customerOrderDAO.findOne(customerOrder.getOrderId());
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
	
		BeanUtils.copyProperties(customerOrder, origin);
		origin = customerOrderDAO.save(origin);
		return origin;
	}

	@Override
	@Transactional
	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		customerOrder = customerOrderDAO.saveAndFlush(customerOrder);
		return customerOrder;
	}

	@Override
	public Page<CustomerOrder> getAllCustomerOrder(int page , int size) {
		Page<CustomerOrder> customerOrders = customerOrderDAO.findAll(new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		return customerOrders;
	}

	@Override
	@Transactional
	public void deleteOrder(CustomerOrder customerOrder) {
		
		customerOrderDAO.delete(customerOrder);
	}

}
