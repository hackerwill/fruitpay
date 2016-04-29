package com.fruitpay.comm.service;

public interface RoleService {
	
	/**
	 * 
	 * 回傳傳進的使用者是否為後台ADMIN管理人員
	 * 
	 * */
	public boolean isAdmin(String userId);

}
