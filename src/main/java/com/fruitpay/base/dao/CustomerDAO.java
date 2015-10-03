package com.fruitpay.base.dao;

import com.fruitpay.base.model.Customer;

public interface CustomerDAO extends DAO<Customer> {
	
	/**
     * 驗證該信箱是否已存在
     *
     * @param  email
     *         信箱
     *
     * @return 是否存在
     */
	public boolean isEmailExisted(String email);
	
	/**
     * 驗證該信箱與密碼是否與資料庫資料吻合
     *
     * @param  email
     *         信箱
     * @param  password
     *         密碼
     *
     * @return 是否吻合
     */
	public boolean isEmailMatchPassword(String email, String password);
	
}
