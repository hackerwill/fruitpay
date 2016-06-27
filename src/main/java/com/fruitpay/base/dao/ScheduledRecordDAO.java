package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fruitpay.base.model.ScheduledRecord;

public interface ScheduledRecordDAO extends JpaRepository<ScheduledRecord, Integer> {
	
	
}
