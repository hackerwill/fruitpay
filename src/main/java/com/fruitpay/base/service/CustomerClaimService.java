package com.fruitpay.base.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerClaimCondition;

public interface CustomerClaimService {
	
	public Page<CustomerClaim> findAllByCondition(CustomerClaimCondition orderCondition, int page , int size);
	
	public List<CustomerClaim> findByCustomerId(int customerId);
	
	public List<CustomerClaim> findByCustomerIdIncludingStatuses(int customerId);
	
	public List<CustomerClaim> findByOrderId(int orderId);
	
	public List<CustomerClaim> findByShipmentRecordDetailId(int shipmentRecordDetailId);
	
	public CustomerClaim add(CustomerClaim customerClaim);
	
	public CustomerClaim update(CustomerClaim customerClaim);
	
	public CustomerClaim invalidate(CustomerClaim customerClaim);

}
