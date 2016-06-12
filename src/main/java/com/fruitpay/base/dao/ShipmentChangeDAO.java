package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;

public interface ShipmentChangeDAO extends JpaRepository<ShipmentChange, Integer> {

	public Page<ShipmentChange> findByValidFlag(int validFlag, Pageable pageable);
	
	public List<ShipmentChange> findByCustomerOrderAndValidFlag(CustomerOrder customerOrder, int validFlag);
	
	public List<ShipmentChange> findByApplyDateAndShipmentChangeTypeAndValidFlag(Date applyDate, ConstantOption shipmentChangeType,int validFlag);

	@Query("FROM ShipmentChange s where "
			+ " s.applyDate BETWEEN :startDate AND :endDate "
			+ " AND STR(s.validFlag) like %:validFlag% ")
	public Page<ShipmentChange> findByConditions(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag, 
			Pageable pageable);
	
	@Query("FROM ShipmentChange s where "
			+ " s.applyDate BETWEEN :startDate AND :endDate "
			+ " AND STR(s.validFlag) like %:validFlag% ")
	public List<ShipmentChange> findByConditions(
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag);
}
