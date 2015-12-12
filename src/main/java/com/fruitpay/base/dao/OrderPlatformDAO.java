package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.OrderPlatform;

public interface OrderPlatformDAO extends JpaRepository<OrderPlatform, Integer> {

}
