package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.AssertUtils;


/**
 * The persistent class for the Village database table.
 * 
 */
@Entity
@NamedQuery(name="Towership.findAll", query="SELECT t FROM Towership t")
public class Towership extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="towership_code")
	private String towershipCode;

	@Column(name="towership_name")
	private String towershipName;
	
	@Column(name="county_code")
	private String countyCode;

	@Column(name="county_name")
	private String countyName;
	
	@Transient
	private String id;
	@Transient
	private String name;
	@Transient
	@JsonIgnore
	private SelectOption county;	

	public Towership() {
	}

	public String getCountyCode() {
		return this.countyCode;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public String getTowershipCode() {
		return this.towershipCode;
	}

	public String getTowershipName() {
		return this.towershipName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("county")
	public SelectOption getCounty() {
		return county;
	}

	@JsonIgnore
	public void setCounty(SelectOption county) {
		this.county = county;
	}
	
	public void setTowershipRelatedData() {
		if(AssertUtils.hasValue(this.getTowershipCode()))
			this.setId(this.getTowershipCode());
		
		if(AssertUtils.hasValue(this.getTowershipName()))
			this.setName(this.getTowershipName());
		
		if(AssertUtils.hasValue(this.getCountyCode()) && AssertUtils.hasValue(this.getCountyName()))
			this.setCounty(new SelectOption(this.getCountyCode(), this.getCountyName()));
	}
	
}