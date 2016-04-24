package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;

public interface ShipmentChangeDAO extends JpaRepository<ShipmentChange, Integer> {

	public List<ShipmentChange> findByCustomerOrder(CustomerOrder customerOrder);
}
