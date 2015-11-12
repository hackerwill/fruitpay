package com.fruitpay.comm.model;

import java.io.Serializable;

public interface ReturnData<T> extends Serializable {
	
	/**
     * 得到錯誤代碼
     *
     * @return 錯誤代碼
     */
	public String getErrorCode();
	
	/**
     * 得到回報信息
     *
     * @return 回報信息
     */
	public String getMessage();
	
	/**
     * 得到回傳物件
     *
     * @return 回傳物件
     */
	public T getObject();

}
