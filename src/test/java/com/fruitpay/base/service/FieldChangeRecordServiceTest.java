package com.fruitpay.base.service;



import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.NeedRecordEnum;
import com.fruitpay.base.dao.FieldChangeRecordDAO;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.FieldChangeRecord;
import com.fruitpay.base.service.FieldChangeRecordService;
import com.fruitpay.util.AbstractSpringJnitTest;

public class FieldChangeRecordServiceTest extends AbstractSpringJnitTest{
	
	@Inject
	FieldChangeRecordDAO fieldChangeRecordDAO;
	@Inject
	FieldChangeRecordService fieldChangeRecordService;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWithPrimitiveTypeMustReturnLastOne() throws Exception {
		int pkId = 123;
		String tableName = "CustomerOrder";
		String fieldName = "totalPrice";
		Integer fieldValue1 = 12;
		Integer fieldValue2 = 13;
				
		FieldChangeRecord r1 = new FieldChangeRecord();
		r1.setPkId(pkId);
		r1.setTableName(tableName);
		r1.setFieldName(fieldName);
		r1.setFieldValue(String.valueOf(fieldValue1));
		
		FieldChangeRecord r2 = new FieldChangeRecord();
		r2.setPkId(pkId);
		r2.setTableName(tableName);
		r2.setFieldName(fieldName);
		r2.setFieldValue(String.valueOf(fieldValue2));
		
		fieldChangeRecordDAO.save(r1);
		fieldChangeRecordDAO.save(r2);
		
		Integer value = fieldChangeRecordService.findLastRecord(NeedRecordEnum.CustomerOrder.totalPrice, pkId, Integer.class);
		Assert.assertEquals(fieldValue2, value);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWithObjectReferenceMustReturnLastOne() throws Exception {
		int pkId = 123;
		String tableName = "CustomerOrder";
		String fieldName = "deliveryDay";
		int optionId1 = 1;
		int optionId2 = 2;
		ConstantOption deliveryDay1 = new ConstantOption();
		deliveryDay1.setOptionId(optionId1);
		ConstantOption deliveryDay2 = new ConstantOption();
		deliveryDay2.setOptionId(optionId2);
				
		FieldChangeRecord r1 = new FieldChangeRecord();
		r1.setPkId(pkId);
		r1.setTableName(tableName);
		r1.setFieldName(fieldName);
		r1.setFieldValue(String.valueOf(optionId1));
		
		FieldChangeRecord r2 = new FieldChangeRecord();
		r2.setPkId(pkId);
		r2.setTableName(tableName);
		r2.setFieldName(fieldName);
		r2.setFieldValue(String.valueOf(optionId2));
		
		fieldChangeRecordDAO.save(r1);
		fieldChangeRecordDAO.save(r2);
		
		ConstantOption deliveryDay = fieldChangeRecordService.findLastRecord(NeedRecordEnum.CustomerOrder.deliveryDay, pkId, ConstantOption.class);
		Assert.assertEquals(deliveryDay2.getOptionId(), deliveryDay.getOptionId());
	}
	
	
}
