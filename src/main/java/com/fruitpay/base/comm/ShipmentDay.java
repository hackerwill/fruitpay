package com.fruitpay.base.comm;

public enum ShipmentDay {
	Monday(1),
	Tuesday(2),
	Wendnesay(3),
	Thursday(4),
	Friday(5),
	Saturday(6),
	Sunday(7);
	
	
	private int day;
	
	private ShipmentDay(int day){
		this.day = day;
	}
	
	public int getDay(){
		return this.day;
	}

}
