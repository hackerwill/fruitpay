package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.DailyOrderRecord;

public interface DailyOrderRecordDAO extends JpaRepository<DailyOrderRecord, Integer> {
	
}
