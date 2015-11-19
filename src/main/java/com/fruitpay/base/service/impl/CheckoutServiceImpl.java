package com.fruitpay.base.service.impl;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderPreferencePK;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.service.CheckoutService;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private OrderProgramDAO orderProgramDAO;
	@Inject
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject
	private ProductDAO productDAO;
	
	@Override
	public CustomerOrder checkoutOrder(CustomerOrder customerOrder) {
		logger.debug("add a customerOrder, email is " + customerOrder.getCustomer().getEmail());

		customerOrder = customerOrderDAO.create(customerOrder);
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			OrderPreferencePK id = new OrderPreferencePK();
			orderPreference.setProduct(productDAO.findById(orderPreference.getId().getProductId()));
			orderPreference.setCustomerOrder(customerOrder);
			id.setOrderId(orderPreference.getCustomerOrder().getOrderId());
			orderPreference.setId(id);
			
			orderPreferenceDAO.create(orderPreference);
		}
		
		
		//OrderProgram orderProgram = orderProgramDAO.findById(customerOrder.getOrderProgram().getProgramId());
		//customerOrder.setOrderProgram(orderProgram);
		
		return customerOrder;
	}

	@Override
	public CustomerOrder getCustomerOrder(Integer orderId) {
		return customerOrderDAO.findById(orderId);
	}

	@Override
	@Transactional
	public Boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		return customerOrderDAO.updateOrderStatus(orderId, orderStatus);
	}

}
