package com.fruitpay.base.comm;

public enum ShipmentPeriod {
	EVERY_WEEK(1), 
	DOUBLE_WEEK(2);
	
	private final int periodId;
	
	ShipmentPeriod(final int periodId){
		this.periodId = periodId;
	}
	
	public int value() {
		return this.periodId;
	}

}
