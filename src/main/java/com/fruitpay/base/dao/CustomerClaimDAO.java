package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentRecordDetail;


public interface CustomerClaimDAO extends JpaRepository<CustomerClaim, Integer> {

	@Query(value = "SELECT DISTINCT cc.claim_id FROM CustomerClaim cc "
			+ " LEFT JOIN CustomerOrder o ON cc.order_id = o.order_id "
			+ " LEFT JOIN Customer c ON cc.customer_id = c.customer_id "
			+ " WHERE (o.receiver_last_name LIKE %?1% OR o.receiver_first_name LIKE %?1%) "
			+ " AND CAST(o.order_id as CHAR(11)) LIKE %?2% "
			+ " AND cc.update_date BETWEEN ?3 AND ?4 "
			+ " AND CAST(cc.valid_flag as CHAR(1)) like %?5% " 
			+ " AND o.receiver_cellphone LIKE %?6% "
			+ " AND CASE WHEN '' NOT LIKE %?7% THEN (c.email LIKE %?7%) ELSE TRUE END  "
			+ " ORDER BY cc.update_date desc ",
			nativeQuery = true)
	public List<Integer> findByCondition(
			String name, 
			String orderId, 
			Date startDate, 
			Date endDate,
			String validFlag, 
			String receiverCellphone,
			String email);
	
	public List<CustomerClaim> findByClaimIdIn(List<Integer> customerClaimIds);
	
	public Page<CustomerClaim> findByClaimIdIn(List<Integer> customerClaimIds, Pageable pageable);
	
	public List<CustomerClaim> findByCustomer(Customer customer);
	
	public List<CustomerClaim> findByCustomerOrder(CustomerOrder customerOrder);
	
	public List<CustomerClaim> findByShipmentRecordDetail(ShipmentRecordDetail shipmentRecordDetail);
}
