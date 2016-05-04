package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role extends AbstractEntity implements Serializable{
	
	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer roleId;
	
	@ManyToOne
	@JoinColumn(name="role_type")
	@JsonProperty("roleType")
	private ConstantOption roleType;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	private Role parentRole;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public ConstantOption getRoleType() {
		return roleType;
	}

	public void setRoleType(ConstantOption roleType) {
		this.roleType = roleType;
	}

	public Role getParentRole() {
		return parentRole;
	}

	public void setParentRole(Role parentRole) {
		this.parentRole = parentRole;
	}


}
