package com.fruitpay.base.comm;

public class NeedRecordEnum {
	
	public enum CustomerOrder implements NeedRecordInterface{
		orderStatus,
		paymentMode,
		deliveryDay,
		totalPrice
	};

}
