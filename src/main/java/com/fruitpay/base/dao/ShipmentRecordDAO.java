package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ShipmentRecord;

public interface ShipmentRecordDAO extends JpaRepository<ShipmentRecord, Integer> {

}

