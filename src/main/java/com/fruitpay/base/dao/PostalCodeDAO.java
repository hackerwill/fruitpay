package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.PostalCode;

public interface PostalCodeDAO extends JpaRepository<PostalCode, Integer> {
	
}
