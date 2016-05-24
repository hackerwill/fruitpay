package com.fruitpay.comm.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fruitpay.comm.annotation.ColumnName;

public abstract class AbstractExcelBean implements Serializable {

	public Map<String, Object> getMap() throws IllegalArgumentException, IllegalAccessException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field : fields) {
    		field.setAccessible(true);
    		if(field.isAnnotationPresent(ColumnName.class)) {
    			map.put(field.getName(), field.get(this));
    		}
		}
		
		return map;
	}
	
	public List<ExcelColumn> getColList() {
		List<ExcelColumn> colList = new ArrayList<ExcelColumn>();
		
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field : fields) {
    		field.setAccessible(true);
    		if(field.isAnnotationPresent(ColumnName.class)) {
    			colList.add(new ExcelColumn(
					field.getName(), 
					field.getDeclaredAnnotation(ColumnName.class).value())
    			);
    		}
		}
		
		return colList;
	}
	
	

}
