package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;


/**
 * The persistent class for the OrderProgram database table.
 * 
 */
@Entity
@NamedQuery(name="OrderProgram.findAll", query="SELECT o FROM OrderProgram o")
public class OrderProgram extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="program_id")
	private int programId;

	@Column(name="program_desc")
	private String programDesc;

	@Column(name="program_name")
	private String programName;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="img_link")
	private String imgLink;

	public OrderProgram() {
	}

	public int getProgramId() {
		return this.programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramDesc() {
		return this.programDesc;
	}

	public void setProgramDesc(String programDesc) {
		this.programDesc = programDesc;
	}

	public String getProgramName() {
		return this.programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImgLink() {
		return imgLink;
	}

	public void setImgLink(String imgLink) {
		this.imgLink = imgLink;
	}
	
}