package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the OrderPlatform database table.
 * 
 */
@Entity
@NamedQuery(name="OrderPlatform.findAll", query="SELECT o FROM OrderPlatform o")
@Cacheable
public class OrderPlatform extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="platform_id")
	private Integer platformId;

	@Column(name="platform_desc")
	private String platformDesc;

	@Column(name="platform_name")
	private String platformName;

	public OrderPlatform() {
	}

	public Integer getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlatformDesc() {
		return this.platformDesc;
	}

	public void setPlatformDesc(String platformDesc) {
		this.platformDesc = platformDesc;
	}

	public String getPlatformName() {
		return this.platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

}