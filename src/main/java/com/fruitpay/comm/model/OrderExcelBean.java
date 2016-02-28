package com.fruitpay.comm.model;

import java.io.Serializable;

public class OrderExcelBean implements Serializable {
	
	private String platform;
	private String type;
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
