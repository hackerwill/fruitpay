package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fruitpay.comm.entityListener.AbstractEntityListener;

@MappedSuperclass
@EntityListeners(AbstractEntityListener.class)
public abstract class AbstractEntity implements Serializable {
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="create_id")
	@JsonIgnore 
	protected Customer createUser;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="update_id")
	@JsonIgnore 
	protected Customer updateUser;
	
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createDate;
	
	@Column(name="update_date")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updateDate;
	
	@Transient
	private transient List<FieldChangeRecord> previousRecords;
	
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

	public Customer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Customer createUser) {
		this.createUser = createUser;
	}

	public Customer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Customer updateUser) {
		this.updateUser = updateUser;
	}

	public List<FieldChangeRecord> getPreviousRecords() {
		return previousRecords;
	}

	public void setPreviousRecords(List<FieldChangeRecord> previousRecords) {
		this.previousRecords = previousRecords;
	}
}
