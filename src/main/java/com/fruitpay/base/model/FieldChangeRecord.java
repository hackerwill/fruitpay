package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="FieldChangeRecord.findAll", query="SELECT f FROM FieldChangeRecord f")
public class FieldChangeRecord extends AbstractEntity implements Serializable {
	
	@Id
	@Column(name="field_change_record_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int fieldChangeRecordId;
	
	@Column(name="pk_id")
	private int pkId;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="field_name")
	private String fieldName;
	
	@Column(name="field_value")
	private String fieldValue;

	public int getFieldChangeRecordId() {
		return fieldChangeRecordId;
	}

	public void setFieldChangeRecordId(int fieldChangeRecordId) {
		this.fieldChangeRecordId = fieldChangeRecordId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getPkId() {
		return pkId;
	}

	public void setPkId(int pkId) {
		this.pkId = pkId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
}
