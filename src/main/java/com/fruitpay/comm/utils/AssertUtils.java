package com.fruitpay.comm.utils;

import org.apache.log4j.Logger;


public class AssertUtils {
	
	private static final Logger logger = Logger.getLogger(AssertUtils.class);
	
	public static boolean anyIsEmpty(String... strs){
		for (int i = 0; i < strs.length; i++) {
			if(isEmpty(strs[i])) 
				return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Integer value){
		return value == null;
	}
	
	public static boolean isEmpty(String str){
		return str == null || str.isEmpty();
	}
	
	public static boolean hasValue(Integer value){
		return ! isEmpty(value);
	}
	
	public static boolean hasValue(String str){
		return ! isEmpty(str);
	}
	
	public static boolean isEmpty(Object object){
		return object == null ;
	}

}
