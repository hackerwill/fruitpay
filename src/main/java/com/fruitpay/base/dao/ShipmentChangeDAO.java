package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;

public interface ShipmentChangeDAO extends JpaRepository<ShipmentChange, Integer> {

	public Page<ShipmentChange> findByValidFlag(int validFlag, Pageable pageable);
	
	public List<ShipmentChange> findByCustomerOrderAndValidFlag(CustomerOrder customerOrder, int validFlag);
	
	public List<ShipmentChange> findByApplyDateAndShipmentChangeTypeAndValidFlag(Date applyDate, ConstantOption shipmentChangeType,int validFlag);
}
