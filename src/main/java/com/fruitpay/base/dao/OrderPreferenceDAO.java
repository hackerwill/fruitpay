package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.OrderPreference;

public interface OrderPreferenceDAO extends JpaRepository<OrderPreference, Integer> {

}
