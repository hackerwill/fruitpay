package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="Constant.findAll", query="SELECT c FROM Constant c")
public class Constant extends AbstractDataBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="const_id")
	private int constId;

	@Column(name="const_name")
	private String constName;

	@Column(name="const_desc")
	private String constDesc;

	//bi-directional many-to-one association to Shipment
	@OneToMany(mappedBy="constant", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<ConstantOption> constOptions;

	public Constant() {
	}

	public int getConstId() {
		return constId;
	}

	public void setConstId(int constId) {
		this.constId = constId;
	}

	public String getConstName() {
		return constName;
	}

	public void setConstName(String constName) {
		this.constName = constName;
	}

	public String getConstDesc() {
		return constDesc;
	}

	public void setConstDesc(String constDesc) {
		this.constDesc = constDesc;
	}

	public List<ConstantOption> getConstOptions() {
		return constOptions;
	}

	public void setConstOptions(List<ConstantOption> constOptions) {
		this.constOptions = constOptions;
	}
	
	public void addConstOption(ConstantOption constOption) {
		getConstOptions().add(constOption);
		constOption.setConstant(this);
	}
	
	public ConstantOption removeConstOption(ConstantOption constOption) {
		getConstOptions().remove(constOption);
		constOption.setConstant(null);
		return constOption;
	}
	
	

}