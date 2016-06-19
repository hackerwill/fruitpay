package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ExceptionLog;

public interface ExceptionLogDAO extends JpaRepository<ExceptionLog, Integer> {

}
