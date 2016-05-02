package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Constant;

public interface FieldChangeRecordDAO extends JpaRepository<Constant, Integer> {

}
