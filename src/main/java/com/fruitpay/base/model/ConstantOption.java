package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="ConstOption.findAll", query="SELECT c FROM ConstantOption c")
@Cacheable
public class ConstantOption extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="option_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer optionId;

	@Column(name="option_name")
	private String optionName;

	@Column(name="option_desc", length=512)
	private String optionDesc;
	
	@Column(name="valid_flag", nullable=false)
	private int validFlag;

	@Column(name="order_no")
	private Integer orderNo;
	
	@ManyToOne
	@JoinColumn(name="const_id")
	@JsonBackReference
	private Constant constant;

	public ConstantOption() {
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getOptionDesc() {
		return optionDesc;
	}

	public void setOptionDesc(String optionDesc) {
		this.optionDesc = optionDesc;
	}

	public int getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(int validFlag) {
		this.validFlag = validFlag;
	}

	public Constant getConstant() {
		return constant;
	}

	public void setConstant(Constant constant) {
		this.constant = constant;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}


}