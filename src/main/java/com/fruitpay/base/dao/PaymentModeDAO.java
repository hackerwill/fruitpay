package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.PaymentMode;

public interface PaymentModeDAO extends JpaRepository<PaymentMode, Integer> {

}
