package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class AbstractDataBean implements Serializable {
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDate;
	
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateDate;
	
	@PrePersist
	protected void onCreate() {
		this.createDate = new Date();
		this.updateDate = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updateDate = new Date();
	}
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
