package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

public class ChosenProductItemBean implements Serializable {
	
	private int productItemId;
	private String productItemName;
	private List<ProductStatusBean> productStatusBeans;
	private Integer maxLimit;
	private double actualTotal;
	
	public ChosenProductItemBean() {
		
	}

	public ChosenProductItemBean(int productItemId, String productItemName, List<ProductStatusBean> productStatusBeans, Integer maxLimit, double actualTotal) {
		super();
		this.productItemId = productItemId;
		this.productItemName = productItemName;
		this.productStatusBeans = productStatusBeans;
		this.maxLimit = maxLimit;
		this.actualTotal = actualTotal;
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

	public Integer getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(Integer maxLimit) {
		this.maxLimit = maxLimit;
	}

	public double getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(double actualTotal) {
		this.actualTotal = actualTotal;
	}

}
