package com.fruitpay.base.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerClaim;
import com.fruitpay.base.model.CustomerClaimStatus;


public interface CustomerClaimStatusDAO extends JpaRepository<CustomerClaimStatus, Integer> {
	
	List<CustomerClaimStatus> findByCustomerClaimIn(List<CustomerClaim> customerClaims);
	
}
