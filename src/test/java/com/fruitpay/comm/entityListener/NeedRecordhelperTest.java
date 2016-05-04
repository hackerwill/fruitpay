package com.fruitpay.comm.entityListener;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.FieldChangeRecord;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.comm.utils.NeedRecordHelper;

public class NeedRecordhelperTest{
	
	@Test
	public void testWithCustomerOrderNeedValidate() throws Exception {
		int id = 1;
		CustomerOrder customerOrder = new CustomerOrder();
		OrderStatus orderStatus = new OrderStatus();
		ConstantOption deliveryDay = new ConstantOption();

		orderStatus.setOrderStatusId(id);
		deliveryDay.setOptionId(id);
		
		customerOrder.setOrderId(id);
		customerOrder.setOrderStatus(orderStatus);
		customerOrder.setDeliveryDay(deliveryDay);
		
		List<FieldChangeRecord> records = new ArrayList<>();
		
		FieldChangeRecord r1 = new FieldChangeRecord();
		r1.setTableName("CustomerOrder");
		r1.setPkId(id);
		r1.setFieldName("deliveryDay");
		r1.setFieldValue("1");
		records.add(r1);
		
		FieldChangeRecord r2 = new FieldChangeRecord();
		r2.setTableName("CustomerOrder");
		r2.setPkId(id);
		r2.setFieldName("orderStatus");
		r2.setFieldValue("1");
		
		records.add(r2);
		
		List<FieldChangeRecord> returnRecords = NeedRecordHelper.getFieldChangeRecords(customerOrder);
		
		for (int i = 0; i < returnRecords.size(); i++) {

			Assert.assertEquals(records.get(i).getFieldName(), returnRecords.get(i).getFieldName());
			Assert.assertEquals(records.get(i).getFieldValue(), returnRecords.get(i).getFieldValue());
			Assert.assertEquals(records.get(i).getPkId(), returnRecords.get(i).getPkId());
			Assert.assertEquals(records.get(i).getTableName(), returnRecords.get(i).getTableName());
		}
		
		
	}
	
	
}
