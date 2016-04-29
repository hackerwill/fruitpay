package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;

public interface OrderPreferenceDAO extends JpaRepository<OrderPreference, Integer> {
	
	public List<OrderPreference> findByCustomerOrder(CustomerOrder CustomerOrder);
	
	public List<OrderPreference> findByCustomerOrderAndLikeDegree(CustomerOrder CustomerOrder, byte likeDegree);

}
