package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ShipmentDay;

public interface ShipmentDayDAO extends JpaRepository<ShipmentDay, Integer> {

}
