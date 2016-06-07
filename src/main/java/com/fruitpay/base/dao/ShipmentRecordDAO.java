package com.fruitpay.base.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ShipmentRecord;

public interface ShipmentRecordDAO extends JpaRepository<ShipmentRecord, Integer> {
	
	Page<ShipmentRecord> findByDateAndValidFlag(Date date, int validFlag, Pageable pageable);
	
	List<ShipmentRecord> findByDateEqualsAndValidFlag(Date date, int validFlag);

}

