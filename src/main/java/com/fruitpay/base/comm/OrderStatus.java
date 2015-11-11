package com.fruitpay.base.comm;

public enum OrderStatus{
	AlreadyCheckout(1),
	AlreayCancel(2),
	CreditPaySuccessful(3),
	CreditPayFailed(4),
	AlreadyShipped(5);
	
	private int status;
	
	private OrderStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}
	
}