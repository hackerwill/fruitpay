package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

public class ChosenProductBean implements Serializable {
	
	private int productId;
	private String productName;
	private List<ProductStatusBean> productStatusBeans;

	public ChosenProductBean(int productId, String productName, List<ProductStatusBean> productStatusBeans) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productStatusBeans = productStatusBeans;
	}
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public List<ProductStatusBean> getProductStatusBeans() {
		return productStatusBeans;
	}
	public void setProductStatusBeans(List<ProductStatusBean> productStatusBeans) {
		this.productStatusBeans = productStatusBeans;
	}
	
}
