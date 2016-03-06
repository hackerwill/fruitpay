package com.fruitpay.comm.model;

import java.io.Serializable;

public class ExcelColumn implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String columnCode;
	private String columnName;
	
	public ExcelColumn(String columnCode,String columnName){
		this.columnCode = columnCode;
		this.columnName = columnName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null||columnCode==null)
			return false;
		
		return(obj instanceof String && columnCode.equals(obj));
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return this.clone();
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	
}
