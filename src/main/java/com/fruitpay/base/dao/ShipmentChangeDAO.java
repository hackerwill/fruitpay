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
	
	public List<ShipmentChange> findByCustomerOrderInAndValidFlag(List<CustomerOrder> customerOrders, int validFlag);
	
	public List<ShipmentChange> findByCustomerOrderAndValidFlag(CustomerOrder customerOrder, int validFlag);
	
	public List<ShipmentChange> findByApplyDateAndShipmentChangeTypeAndValidFlag(Date applyDate, ConstantOption shipmentChangeType,int validFlag);

	@Query("FROM ShipmentChange s where "
			+ " s.applyDate BETWEEN :deliveryStartDate AND :deliveryEndDate "
			+ " AND s.updateDate BETWEEN :updateStartDate AND :updateEndDate "
			+ " AND STR(s.validFlag) like %:validFlag% "
			+ " AND ( s.customerOrder.receiverLastName LIKE %:name% OR s.customerOrder.receiverFirstName LIKE %:name% ) "
			+ " AND CAST(s.customerOrder.orderId as string) LIKE %:orderId% "
			+ " AND s.shipmentChangeType.optionDesc LIKE %:shipmentChangeType% "
			+ " AND s.customerOrder.receiverCellphone LIKE %:receiverCellphone% ")
	public Page<ShipmentChange> findByConditions(
			@Param("deliveryStartDate") Date deliveryStartDate, 
			@Param("deliveryEndDate") Date deliveryEndDate,
			@Param("updateStartDate") Date updateStartDate, 
			@Param("updateEndDate") Date updateEndDate,
			@Param("validFlag") String validFlag, 
			@Param("orderId") String orderId, 
			@Param("name") String name, 
			@Param("receiverCellphone") String receiverCellphone,
			@Param("shipmentChangeType") String shipmentChangeType,
			Pageable pageable);
	
	@Query("FROM ShipmentChange s where "
			+ " s.applyDate BETWEEN :deliveryStartDate AND :deliveryEndDate "
			+ " AND s.updateDate BETWEEN :updateStartDate AND :updateEndDate "
			+ " AND STR(s.validFlag) like %:validFlag% "
			+ " AND ( s.customerOrder.receiverLastName LIKE %:name% OR s.customerOrder.receiverFirstName LIKE %:name% ) "
			+ " AND CAST(s.customerOrder.orderId as string) LIKE %:orderId% "
			+ " AND s.shipmentChangeType.optionDesc LIKE %:shipmentChangeType% "
			+ " AND s.customerOrder.receiverCellphone LIKE %:receiverCellphone% "
			+ " ORDER BY s.updateDate DESC ")
	public List<ShipmentChange> findByConditions(
			@Param("deliveryStartDate") Date deliveryStartDate, 
			@Param("deliveryEndDate") Date deliveryEndDate,
			@Param("updateStartDate") Date updateStartDate, 
			@Param("updateEndDate") Date updateEndDate,
			@Param("validFlag") String validFlag,
			@Param("orderId") String orderId, 
			@Param("name") String name, 
			@Param("receiverCellphone") String receiverCellphone,
			@Param("shipmentChangeType") String shipmentChangeType);
}
