package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Product;

public interface ProductDAO extends JpaRepository<Product, Integer> {
	
}
