package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderStatus;

public interface CustomerOrderDAO extends JpaRepository<CustomerOrder, Integer> {
	
	public List<CustomerOrder> findByCustomerAndValidFlag(Customer customer, int validFlag);
	
	public Page<CustomerOrder> findByValidFlag(int validFlag, Pageable pageable);
	
	public Page<CustomerOrder> findByOrderIdIn(List<Integer> orderIds, Pageable pageable);
	
	public List<CustomerOrder> findByOrderIdIn(List<Integer> orderIds);
	
	public List<CustomerOrder> findByValidFlag(int validFlag);
	
	public List<CustomerOrder> findByValidFlagAndDeliveryDayAndOrderStatusIn(int validFlag, ConstantOption deliveryDay, List<OrderStatus> orderStatues);
	
	public CustomerOrder findByOrderIdAndValidFlag(int orderId, int validFlag);
	
	@Query("SELECT DISTINCT o FROM CustomerOrder o "
			+ " LEFT JOIN o.shipmentChanges s "
			+ " where CAST(o.orderId as string) LIKE %:orderId% "
			+ " AND ( o.receiverLastName LIKE %:name% OR o.receiverFirstName LIKE %:name% ) "
			+ " AND o.orderDate BETWEEN :startDate AND :endDate "
			+ " AND STR(o.validFlag) like %:validFlag% " 
			+ " AND o.allowForeignFruits like %:allowForeignFruits% "
			+ " AND STR(o.orderStatus.orderStatusId) like %:orderStatusId% "
			+ " AND o.receiverCellphone LIKE %:receiverCellphone% "
			+ " AND (o.customer.email LIKE %:email% OR o.customer.email IS NULL)"
			+ " AND (s.reason LIKE %:shipmentChangeReason% OR s.reason IS NULL)")
	public Page<CustomerOrder> findByConditions(
			@Param("name") String name, 
			@Param("orderId") String orderId, 
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag, 
			@Param("allowForeignFruits") String allowForeignFruits,
			@Param("orderStatusId") String orderStatusId,
			@Param("receiverCellphone") String receiverCellphone,
			@Param("email") String email,
			@Param("shipmentChangeReason") String shipmentChangeReason,
			Pageable pageable);
	
	
	@Query("SELECT DISTINCT o FROM CustomerOrder o "
			+ " LEFT JOIN o.shipmentChanges s "
			+ " where CAST(o.orderId as string) LIKE %:orderId% "
			+ " AND ( o.receiverLastName LIKE %:name% OR o.receiverFirstName LIKE %:name% ) "
			+ " AND o.orderDate BETWEEN :startDate AND :endDate "
			+ " AND STR(o.validFlag) like %:validFlag% " 
			+ " AND o.allowForeignFruits like %:allowForeignFruits% "
			+ " AND STR(o.orderStatus.orderStatusId) like %:orderStatusId% "
			+ " AND o.receiverCellphone LIKE %:receiverCellphone% "
			+ " AND (o.customer.email LIKE %:email% OR o.customer.email IS NULL)"
			+ " AND (s.reason LIKE %:shipmentChangeReason% OR s.reason IS NULL)")
	public List<CustomerOrder> findByConditions(
			@Param("name") String name, 
			@Param("orderId") String orderId, 
			@Param("startDate") Date startDate, 
			@Param("endDate") Date endDate,
			@Param("validFlag") String validFlag, 
			@Param("allowForeignFruits") String allowForeignFruits,
			@Param("orderStatusId") String orderStatusId,
			@Param("receiverCellphone") String receiverCellphone,
			@Param("email") String email,
			@Param("shipmentChangeReason") String shipmentChangeReason);
}
