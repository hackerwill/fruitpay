package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;


/**
 * The persistent class for the Shipment database table.
 * 
 */
@Entity
@NamedQuery(name="ScheduledRecord.findAll", query="SELECT s FROM ScheduledRecord s")
public class ScheduledRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="scheduled_record_id")
	private Integer scheduledRecordId;
	
	@Column(name="method_name", length = 128)
	@NotNull
	private String methodName;
	
	@Column(name="is_successful", length = 1)
	@NotNull
	private String isSuccessful;

	@Column(length = 1024)
	private String message;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_date")
	@NotNull
	private Date createDate;

	public String getIsSuccessful() {
		return isSuccessful;
	}

	public void setIsSuccessful(String isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public Integer getScheduledRecordId() {
		return scheduledRecordId;
	}

	public void setScheduledRecordId(Integer scheduledRecordId) {
		this.scheduledRecordId = scheduledRecordId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}