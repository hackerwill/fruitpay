package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

public interface CustomerOrderDAO extends JpaRepository<CustomerOrder, Integer> {
	
	public List<CustomerOrder> findByCustomerAndValidFlag(Customer customer, int validFlag);
	
	public Page<CustomerOrder> findByValidFlag(int validFlag, Pageable pageable);
	
}
