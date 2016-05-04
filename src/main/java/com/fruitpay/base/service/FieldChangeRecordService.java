package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.comm.NeedRecordInterface;
import com.fruitpay.base.model.FieldChangeRecord;

public interface FieldChangeRecordService {
	
	public List<FieldChangeRecord> add(List<FieldChangeRecord> fieldChangeRecords);
	
	public <T> T findLastRecord(NeedRecordInterface needRecord, int pkId, Class<T> t);
	
}
