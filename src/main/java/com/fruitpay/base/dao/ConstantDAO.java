package com.fruitpay.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Constant;

public interface ConstantDAO extends JpaRepository<Constant, Integer> {
	
	Page<Constant> findAll(Pageable pageable);

}
