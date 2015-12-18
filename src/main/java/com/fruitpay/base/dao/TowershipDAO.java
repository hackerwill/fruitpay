package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Towership;

public interface TowershipDAO extends JpaRepository<Towership, String> {
	
}
