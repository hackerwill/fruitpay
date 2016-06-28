package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.OrderPreference;

public interface OrderPreferenceService {
	
	public List<OrderPreference> findByCustomerOrder(int orderId);
	
	public List<OrderPreference> updateOrderPreferences(int orderId, List<OrderPreference> orderPreferences);

}
