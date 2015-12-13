package com.fruitpay.base.service;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;

public interface LoginService {
	
	/**
     * 註冊
     *
     * @param  Customer
     *         顧客資訊
     *
     * @return 是否註冊成功，回傳顧客信息
     */
	public Customer signup(Customer customer);
	
	/**
     * 登入
     *
     * @return 是否登入成功，回傳信息
     */
	public Customer login(String email, String password);
	
	/**
     * 登入
     *
     * @return 是否登入成功，回傳信息
     */
	public Customer  loginByCustomerId(Integer customerId, String password);
	
	/**
	 * 若帳號存在直接登入，若不存在建立一個帳號
	 * 
	 * param  Customer
     *         顧客資訊
	 * 
	 * @return 是否登入成功，回傳信息
	 */
	public Customer fbLogin(Customer customer);
	
	/**
	 * 修改密碼
	 * 
	 * */
	public Customer changePassword(Pwd pwd);
	
	
	
	
	
}
