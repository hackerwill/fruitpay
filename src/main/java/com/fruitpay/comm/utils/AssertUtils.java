package com.fruitpay.comm.utils;

import java.util.List;

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
	
	public static boolean anyIsEmpty(List<Integer> values){
		return anyIsEmpty(values.toArray(new Integer[values.size()]));
	}
	
	public static boolean anyIsEmpty(Integer... values){
		for (int i = 0; i < values.length; i++) {
			if(isEmpty(values[i])) 
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
