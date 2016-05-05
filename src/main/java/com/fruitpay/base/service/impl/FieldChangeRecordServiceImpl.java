package com.fruitpay.base.service.impl;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.NeedRecordInterface;
import com.fruitpay.base.dao.FieldChangeRecordDAO;
import com.fruitpay.base.model.FieldChangeRecord;
import com.fruitpay.base.service.FieldChangeRecordService;
import com.fruitpay.comm.utils.StrConvertUtil;

@Service
public class FieldChangeRecordServiceImpl implements FieldChangeRecordService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private FieldChangeRecordDAO fieldChangeRecordDAO;
	@Inject
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<FieldChangeRecord> add(List<FieldChangeRecord> fieldChangeRecords) {
		logger.info("Enter add filedChangeRecord");
		logger.info(fieldChangeRecords.size());
		fieldChangeRecords.forEach(fieldRecord -> fieldChangeRecordDAO.saveAndFlush(fieldRecord));
		return fieldChangeRecords;
	}

	@Override
	public <T> T findLastRecord(NeedRecordInterface needRecord, int pkId, Class<T> t) {
		
		String tableName = needRecord.getClass().getSimpleName();
		String fieldName = needRecord.toString();
	
		FieldChangeRecord record = fieldChangeRecordDAO.findFirst1ByPkIdAndTableNameAndFieldNameOrderByFieldChangeRecordIdDesc(
				pkId, tableName, fieldName);
		
		if(record == null)
			throw new NullPointerException("The record is not found in field '" + fieldName + "', table '" + tableName+ "', " + ", id " + pkId );
		
		try {
			
			if(BeanUtils.isSimpleValueType(t)){
				T value = (T)StrConvertUtil.toObject(t, record.getFieldValue());
				return value;
			}else{
				T obj = entityManager.find(t, Integer.parseInt(record.getFieldValue()));
				return obj;
			}
				
		} catch (SecurityException e) {
			throw new NullPointerException("The field '" + fieldName + "' is not found in table '" + tableName+ "'");
		} 
		
	}

}
