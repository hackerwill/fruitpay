package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Village;

public interface VillageDAO extends JpaRepository<Village, String> {
	
}
