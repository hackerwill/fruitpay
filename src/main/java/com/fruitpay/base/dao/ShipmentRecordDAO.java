package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentRecord;

public interface ShipmentRecordDAO extends JpaRepository<ShipmentRecord, Integer> {

	public List<ShipmentRecord> findByCustomerOrderAndValidFlag(CustomerOrder customerOrder, int validFlag);

}
