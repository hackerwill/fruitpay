package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentRecordDetail;

public interface ShipmentRecordDetailDAO extends JpaRepository<ShipmentRecordDetail, Integer> {

	public List<ShipmentRecordDetail> findByCustomerOrderAndValidFlag(CustomerOrder customerOrder, int validFlag);

}
