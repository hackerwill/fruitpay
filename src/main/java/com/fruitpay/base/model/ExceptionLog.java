package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="ExceptionLog.findAll", query="SELECT e FROM ExceptionLog e")
public class ExceptionLog extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="log_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer logId;

	@Column(length=3)
	private String status;
	
	@Column(length=1024)
	private String message;
	
	private String ip;
	
	public ExceptionLog(String status, String message, String ip) {
		super();
		this.status = status;
		this.message = message;
		this.ip = ip;
	}

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}