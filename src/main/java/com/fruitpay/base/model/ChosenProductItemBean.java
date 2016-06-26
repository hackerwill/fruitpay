package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

public class ChosenProductItemBean implements Serializable {
	
	private int productItemId;
	private String productItemName;
	private List<ProductStatusBean> productStatusBeans;
	private Integer maxLimitWithUnit;
	private int actualTotalWithUnit;
	private String unit;
	private int familyAmount;
	private int singleAmount;
	private int actualTotal;
	private int actualTotalFamily;
	private int actualTotalSingle;
	
	public ChosenProductItemBean() {
		
	}

	public ChosenProductItemBean(int productItemId, String productItemName, List<ProductStatusBean> productStatusBeans,
			Integer maxLimitWithUnit, int actualTotal, int actualTotalWithUnit, 
			String unit, int familyAmount, int singleAmount) {
		super();
		this.unit = unit;
		this.productItemId = productItemId;
		this.productItemName = productItemName;
		this.productStatusBeans = productStatusBeans;
		this.maxLimitWithUnit = maxLimitWithUnit;
		this.actualTotalWithUnit = actualTotalWithUnit;
		this.actualTotal = actualTotal;
		this.familyAmount = familyAmount;
		this.singleAmount = singleAmount;
	}

	public int getActualTotalFamily() {
		return actualTotalFamily;
	}

	public void setActualTotalFamily(int actualTotalFamily) {
		this.actualTotalFamily = actualTotalFamily;
	}

	public int getActualTotalSingle() {
		return actualTotalSingle;
	}

	public void setActualTotalSingle(int actualTotalSingle) {
		this.actualTotalSingle = actualTotalSingle;
	}

	public Integer getMaxLimitWithUnit() {
		return maxLimitWithUnit;
	}

	public void setMaxLimitWithUnit(Integer maxLimitWithUnit) {
		this.maxLimitWithUnit = maxLimitWithUnit;
	}

	public double getActualTotalWithUnit() {
		return actualTotalWithUnit;
	}

	public void setActualTotalWithUnit(int actualTotalWithUnit) {
		this.actualTotalWithUnit = actualTotalWithUnit;
	}

	public int getFamilyAmount() {
		return familyAmount;
	}

	public void setFamilyAmount(int familyAmount) {
		this.familyAmount = familyAmount;
	}

	public int getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(int singleAmount) {
		this.singleAmount = singleAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getProductItemId() {
		return productItemId;
	}

	public void setProductItemId(int productItemId) {
		this.productItemId = productItemId;
	}

	public String getProductItemName() {
		return productItemName;
	}

	public void setProductItemName(String productItemName) {
		this.productItemName = productItemName;
	}

	public List<ProductStatusBean> getProductStatusBeans() {
		return productStatusBeans;
	}

	public void setProductStatusBeans(List<ProductStatusBean> productStatusBeans) {
		this.productStatusBeans = productStatusBeans;
	}

	public int getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(int actualTotal) {
		this.actualTotal = actualTotal;
	}

}
