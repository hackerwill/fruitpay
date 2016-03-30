package com.fruitpay.comm.utils;

public class DateUtil {
	
	
	public static int getPreviousDayInt(int day){
		if(day == 1)
			return 7;
		else
			return day - 1;
	}
	

}
