package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerClaimOptionalField;


public interface CustomerServiceOptionalFieldDAO extends JpaRepository<CustomerClaimOptionalField, Integer> {

	
}
