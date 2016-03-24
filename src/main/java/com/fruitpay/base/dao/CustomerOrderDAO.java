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
			+ " AND o.validFlag = :validFlag " 
			+ " AND o.allowForeignFruits like %:allowForeignFruits% ")
	public Page<CustomerOrder> findByConditions(
			@Param("name") String name, 
			@Param("orderId") String orderId, 
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") int validFlag, 
			@Param("allowForeignFruits") String allowForeignFruits,
			Pageable pageable);

}
