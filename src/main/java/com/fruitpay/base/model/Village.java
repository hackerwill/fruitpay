package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


/**
 * The persistent class for the Village database table.
 * 
 */
@Entity
@NamedQuery(name="Village.findAll", query="SELECT v FROM Village v")
public class Village extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="village_code")
	private String villageCode;

	@Column(name="county_code")
	private Integer countyCode;

	@Column(name="county_name")
	private String countyName;

	@Column(name="towership_code")
	private Integer towershipCode;

	@Column(name="towership_name")
	private String towershipName;

	@Column(name="village_name")
	private String villageName;

	public Village() {
	}

	//@JsonProperty("villageCode")
	public String getVillageCode() {
		return this.villageCode;
	}

	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	public Integer getCountyCode() {
		return this.countyCode;
	}

	public void setCountyCode(Integer countyCode) {
		this.countyCode = countyCode;
	}

	public String getCountyName() {
		return this.countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Integer getTowershipCode() {
		return this.towershipCode;
	}

	public void setTowershipCode(Integer towershipCode) {
		this.towershipCode = towershipCode;
	}

	public String getTowershipName() {
		return this.towershipName;
	}

	public void setTowershipName(String towershipName) {
		this.towershipName = towershipName;
	}

	public String getVillageName() {
		return this.villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	
}