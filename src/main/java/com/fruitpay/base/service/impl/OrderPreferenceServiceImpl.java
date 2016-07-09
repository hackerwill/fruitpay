package com.fruitpay.base.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.OrderPreferenceService;

@Service
public class OrderPreferenceServiceImpl implements OrderPreferenceService {
	
	@Inject
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject
	private CustomerOrderService customerOrderService;
	

	@Override
	public List<OrderPreference> findByCustomerOrder(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		return orderPreferenceDAO.findByCustomerOrder(customerOrder);
	}


	@Override
	@Transactional
	public List<OrderPreference> updateOrderPreferences(int orderId, List<OrderPreference> orderPreferences) {
		//add newly added, remove deleted, and update existed
		customerOrderService.findOneIncludingOrderPreference(orderId);
		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(orderId);
		List<OrderPreference> origins = orderPreferenceDAO.findByCustomerOrder(customerOrder);
		
		List<OrderPreference> adds = orderPreferences.stream().filter(orderPreference -> {
			return !origins.stream().anyMatch(compare -> compare.getPreferenceId().equals(orderPreference.getPreferenceId()));
		}).map(orderPreference -> {
			orderPreference.setCustomerOrder(customerOrder);
			return orderPreference;
		}).collect(Collectors.toList());
		
		List<OrderPreference> deletes = origins.stream().filter(origin -> {
			return !orderPreferences.stream().anyMatch(compare -> origin.getPreferenceId().equals(compare.getPreferenceId()));
		}).collect(Collectors.toList());
		
		List<OrderPreference> originUpdates = origins.stream().filter(origin -> {
			return orderPreferences.stream().anyMatch(compare -> compare.getPreferenceId() != null && 
					origin.getPreferenceId().equals(compare.getPreferenceId()));
		}).collect(Collectors.toList());
		
		List<OrderPreference> updates = orderPreferences.stream().filter(orderPreference -> {
			return orderPreference.getPreferenceId() != null && 
					originUpdates.stream().anyMatch(compare -> compare.getPreferenceId().equals(orderPreference.getPreferenceId()));
		}).map(orderPreference -> {
			orderPreference.setCustomerOrder(customerOrder);
			return orderPreference;
		}).collect(Collectors.toList());
		
		if(!adds.isEmpty()) {
			orderPreferenceDAO.save(adds);
		}
		
		if(!deletes.isEmpty()) {
			orderPreferenceDAO.deleteInBatch(deletes);
		}
		
		if(!originUpdates.isEmpty()) {
			int i = 0;
			for(OrderPreference orignUpdate : originUpdates) {
				BeanUtils.copyProperties(updates.get(i), orignUpdate);
				orderPreferenceDAO.save(originUpdates);
				i ++;
			}
			
		}
		
		List<OrderPreference> returnOrderPreferences = orderPreferenceDAO.findByCustomerOrder(customerOrder);
		
		return returnOrderPreferences;
	}

}
