package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderDAO extends JpaRepository<CustomerOrder, Integer> {
	
	public List<CustomerOrder> findByCustomerAndValidFlag(Customer customer, int validFlag);
	
	public Page<CustomerOrder> findByValidFlag(int validFlag, Pageable pageable);
	
	public List<CustomerOrder> findByValidFlag(int validFlag);
	
	public CustomerOrder findByOrderIdAndValidFlag(int orderId, int validFlag);
	
	@Query("FROM CustomerOrder o where CAST(o.orderId as string) LIKE %:orderId% "
			+ " AND ( o.receiverLastName LIKE %:name% OR o.receiverFirstName LIKE %:name% ) "
			+ " AND o.orderDate BETWEEN :startDate AND :endDate "
			+ " AND STR(o.validFlag) like %:validFlag% " 
			+ " AND o.allowForeignFruits like %:allowForeignFruits% "
			+ " AND STR(o.orderStatus.orderStatusId) like %:orderStatusId% ")
	public Page<CustomerOrder> findByConditions(
			@Param("name") String name, 
			@Param("orderId") String orderId, 
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag, 
			@Param("allowForeignFruits") String allowForeignFruits,
			@Param("orderStatusId") String orderStatusId,
			Pageable pageable);
	
	@Query("FROM CustomerOrder o where CAST(o.orderId as string) LIKE %:orderId% "
			+ " AND ( o.receiverLastName LIKE %:name% OR o.receiverFirstName LIKE %:name% ) "
			+ " AND o.orderDate BETWEEN :startDate AND :endDate "
			+ " AND STR(o.validFlag) like %:validFlag% " 
			+ " AND o.allowForeignFruits like %:allowForeignFruits% "
			+ " AND STR(o.orderStatus.orderStatusId) like %:orderStatusId% ")
	public List<CustomerOrder> findByConditions(
			@Param("name") String name, 
			@Param("orderId") String orderId, 
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag, 
			@Param("allowForeignFruits") String allowForeignFruits,
			@Param("orderStatusId") String orderStatusId);

}
