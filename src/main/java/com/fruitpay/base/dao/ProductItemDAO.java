package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ProductItem;

public interface ProductItemDAO extends JpaRepository<ProductItem, Integer> {
	
	List<ProductItem> findByProductIn(List<Product> products);
	
}
