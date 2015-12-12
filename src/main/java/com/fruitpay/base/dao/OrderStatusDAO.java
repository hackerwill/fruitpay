package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.OrderStatus;

public interface OrderStatusDAO extends JpaRepository<OrderStatus, Integer> {

}
