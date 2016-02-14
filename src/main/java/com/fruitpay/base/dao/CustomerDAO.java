package com.fruitpay.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fruitpay.base.model.Customer;

public interface CustomerDAO extends JpaRepository<Customer, Integer> {
	
	/**
     * 用信箱得到顧客資訊
     *
     * @param  email
     *         信箱
     *
     * @return 顧客
     */
	public Customer findByEmail(String email);
	
	/**
	 * 用fbId得到顧客資訊
	 * 
	 * @param  fbId
     *         臉書ID
     *         
     * @return 顧客       
     *         
	 * */
	public Customer findByFbId(String fbId);
	
	/**
     * 驗證該信箱與密碼是否與資料庫資料吻合
     *
     * @param  email
     *         信箱
     * @param  password
     *         密碼
     *
     * @return 顧客
     */
	public Customer findByEmailAndPassword(String email, String password);
	
	/**
     * 驗證該ID與密碼是否與資料庫資料吻合
     *
     * @param  custmerId
     *         顧客ID
     * @param  password
     *         密碼
     *
     * @return 顧客
     */
	public Customer findByCustomerIdAndPassword(Integer custmerId, String password);
	
	@Query("select c from Customer c join c.customerOrders o where o.orderId = ?1")
	public Customer findByOrderId(Integer orderId);
	
}
