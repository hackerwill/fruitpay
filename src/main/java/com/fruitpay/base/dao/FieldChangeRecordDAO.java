package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.FieldChangeRecord;


public interface FieldChangeRecordDAO extends JpaRepository<FieldChangeRecord, Integer> {
	
	public FieldChangeRecord findFirst1ByPkIdAndTableNameAndFieldNameOrderByFieldChangeRecordIdDesc(
			int pkId, String tableName, String fieldName);

}
