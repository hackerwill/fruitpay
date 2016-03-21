package com.fruitpay.base.model;

import java.io.Serializable;

public class AllPayOrder implements Serializable {
	
	//@Column()
	
	private int allpayOrderId;
	
	private int orderId;

	private String paymentDate;
	
	private String rtnCode;
	
	private String rtnMessage;

}
