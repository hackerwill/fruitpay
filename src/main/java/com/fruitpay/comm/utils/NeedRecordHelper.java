package com.fruitpay.comm.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.FieldChangeRecord;
import com.fruitpay.comm.annotation.NeedRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NeedRecordHelper {
	
	public static <T> List<FieldChangeRecord> getFieldChangeRecords(T t) throws IllegalAccessException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		
		List<FieldChangeRecord> records = new ArrayList<>();
		Map<String, String> map = getNeedRecordList(t);
		int pkId = getObjectId(t);
		String tableName = t.getClass().getSimpleName();
		
		map.forEach((key, value) -> {
			FieldChangeRecord record = new FieldChangeRecord();
			record.setPkId(pkId);
			record.setTableName(tableName);
			record.setFieldName(key);
			record.setFieldValue(value);
			records.add(record);
		});
		
		return records;
	}
	
	public static <T> Map<String, String> getNeedRecordList(T t) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException{
		String value;
		Map<String, String> map = new HashMap<String, String>();
		List<Field> fields = getAnnotationPresentFileds(NeedRecord.class, t);
		
		for (Iterator<Field> iterator = fields.iterator(); iterator.hasNext();) {
			Field field = iterator.next();
			
			// null的不用紀錄
			field.setAccessible(true);
			if(field.get(t) == null)
				continue;
			
			if(field.getType().isPrimitive() || Integer.class == field.getType()) {
				map.put(field.getName(), String.valueOf(field.get(t)));
			} else {
				value = String.valueOf(getObjectFieldId(field, t));
				map.put(field.getName(), value);
			}
		}
		
		return map;
	}
	
	public static <T> int getObjectId(T t) throws IllegalArgumentException, IllegalAccessException{
		List<Field> fields = getAnnotationPresentFileds(Id.class, t);
		
		if(fields.isEmpty() || fields.size() > 1){
			throw new IllegalArgumentException("The entity must have one and only one property with id annotation, class name: " + t.getClass().getName());
		}
		
		Field idField = fields.get(0);
		idField.setAccessible(true);
		
		if(idField.getType().isPrimitive()) {
			return idField.getInt(t);
		}else {
			return (Integer)idField.get(t);
		}
		
	}
	
	public static <T> int getObjectFieldId(Field field, T t) throws IllegalArgumentException, IllegalAccessException{
		field.setAccessible(true);
		Object obj = field.get(t);
		
		return getObjectId(obj);
	}
	
	public static <T> List<Field> getAnnotationPresentFileds(Class clz, T t){
		List<Field> fields = new ArrayList<Field>();
		
		for(Field field : t.getClass().getDeclaredFields()){
			if(field.isAnnotationPresent(clz)){
				fields.add(field);
			}
		}
		
		return fields;
	}

}
