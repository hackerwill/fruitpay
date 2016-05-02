package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class FieldChangeRecord extends AbstractEntity implements Serializable {
	
	@Id
	@Column(name="filed_change_record_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int FieldChangeRecordId;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="field_name")
	private String fieldName;
	
	@Column(name="value")
	private String value;

	public int getFieldChangeRecordId() {
		return FieldChangeRecordId;
	}

	public void setFieldChangeRecordId(int fieldChangeRecordId) {
		FieldChangeRecordId = fieldChangeRecordId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
