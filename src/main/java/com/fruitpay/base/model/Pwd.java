package com.fruitpay.base.model;

import java.io.Serializable;

public class Pwd implements Serializable{
	private Integer customerId;
	private String oldPassword;
	private String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
