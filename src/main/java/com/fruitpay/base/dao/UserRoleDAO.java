package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Integer> {
	
	public List<UserRole> findByCustomer(Customer customer);

}
