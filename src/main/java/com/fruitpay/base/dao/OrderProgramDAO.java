package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.OrderProgram;

public interface OrderProgramDAO extends JpaRepository<OrderProgram, Integer> {

}
