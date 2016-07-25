package com.fruitpay.base.comm;

public enum CustomerServiceFieldValueType {
	VALIE(0), //Simple Value
	PK(1);	//PK of some Table
	
	private int type;
	
	private CustomerServiceFieldValueType(int type){
		this.type = type;
	}
	
	public int value(){
		return this.type;
	}
}

