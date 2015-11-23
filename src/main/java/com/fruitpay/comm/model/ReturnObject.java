package com.fruitpay.comm.model;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;

public class ReturnObject<T> implements ReturnData {
	
	private static final long serialVersionUID = 1L;
	private ReturnData returnMessage;
	private T object;				//回傳物件
	
	public ReturnObject(T object){
		this.returnMessage = ReturnMessageEnum.Common.Success.getReturnMessage();
		this.object = object;
	}
	
	public ReturnObject(ReturnData returnMessage, T object) {
		this.returnMessage = returnMessage;
		this.object = object;
	}

	@Override
	public String getErrorCode() {
		return returnMessage.getErrorCode();
	}

	@Override
	public String getMessage() {
		return returnMessage.getMessage();
	}

	@Override
	public Object getObject() {
		return object;
	}
	
	

}
