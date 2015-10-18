package com.fruitpay.base.service;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.model.ReturnData;

public interface LoginService {
	
	/**
     * 註冊
     *
     * @param  Customer
     *         顧客資訊
     *
     * @return 是否註冊成功，回傳信息
     */
	public ReturnData signup(Customer customer);
	
	/**
     * 登入
     *
     * @param  Customer
     *         顧客資訊
     *
     * @return 是否登入成功，回傳信息
     */
	public ReturnData login(String email, String password);
	
	
	
	
	
	
}
