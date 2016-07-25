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
	
	public List<CustomerOrder> findByValidFlagAndOrderStatusIn(int validFlag, List<OrderStatus> orderStatues);
	
	public CustomerOrder findByOrderIdAndValidFlag(int orderId, int validFlag);
	
	@Query(value="SELECT DISTINCT o.order_id FROM CustomerOrder o "
			+ " LEFT JOIN ShipmentChange s ON o.order_id = s.order_id "
			+ " LEFT JOIN Customer c ON o.customer_id = c.customer_id "
			+ " WHERE CAST(o.order_id as CHAR(11)) LIKE %?2% "
			+ " AND (o.receiver_last_name LIKE %?1% OR o.receiver_first_name LIKE %?1%) "
			+ " AND o.order_date BETWEEN ?3 AND ?4 "
			+ " AND CAST(o.valid_flag as CHAR(1)) like %?5% " 
			+ " AND o.allow_foreign_fruits like %?6% "
			+ " AND CAST(o.order_status_id as CHAR(2)) like %?7% "
			+ " AND o.receiver_cellphone LIKE %?8% "
			+ " AND CASE WHEN '' NOT LIKE %?9% THEN (c.email LIKE %?9%) ELSE TRUE END  "
			+ " AND CASE WHEN '' NOT LIKE %?10% THEN (s.reason LIKE %?10%) ELSE TRUE END  "
			+ " ORDER BY o.order_id DESC ",
			nativeQuery = true)
	public List<Integer> findByConditions(
			String name, 
			String orderId, 
			Date startDate, 
			Date endDate,
			String validFlag, 
			String allowForeignFruits,
			String orderStatusId,
			String receiverCellphone,
			String email,
			String shipmentChangeReason);
}
