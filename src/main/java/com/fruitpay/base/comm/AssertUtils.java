package com.fruitpay.base.comm;

public class AssertUtils {
	
	public static boolean anyIsEmpty(String... strs){
		for (int i = 0; i < strs.length; i++) {
			if(isEmpty(strs[i])) 
				return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String str){
		return str == null || str.isEmpty();
	}
	
	public static boolean isNotEmpty(String str){
		return ! isEmpty(str);
	}

}
