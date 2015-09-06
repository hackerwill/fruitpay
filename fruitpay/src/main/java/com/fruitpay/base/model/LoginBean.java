package com.fruitpay.base.model;

public class LoginBean {
	
	private String email;
	private String password;
	private Boolean remember;
	private Boolean isEmailExisted;
	private Boolean isPswValidated;
	
	public LoginBean(){
		
	}
	
	public LoginBean(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRemember() {
		return remember;
	}

	public void setRemember(Boolean remember) {
		this.remember = remember;
	}

	public Boolean getIsEmailExisted() {
		return isEmailExisted;
	}

	public void setIsEmailExisted(Boolean isEmailExisted) {
		this.isEmailExisted = isEmailExisted;
	}

	public Boolean getIsPswValidated() {
		return isPswValidated;
	}

	public void setIsPswValidated(Boolean isPswValidated) {
		this.isPswValidated = isPswValidated;
	}

}
