package com.fruitpay.comm.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fruitpay.comm.annotation.ColumnName;

public class ExcelBeanList <T extends AbstractExcelBean> {
	List<T> list = null;
	/*
	public <F> ExcelBeanList(List<F> list, Class<T> t) {
		this.list = list.stream().map(bean ->{
			return t.newInstance();
		}).collect(Collectors.toList());
		
	}
	
	
	public List<ExcelColumn> getColList() {
		List<ExcelColumn> colList = new ArrayList<ExcelColumn>();
		Field[] fields = getDeclaredFields();
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
	}*/

}
