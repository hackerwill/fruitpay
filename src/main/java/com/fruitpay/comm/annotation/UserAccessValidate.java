package com.fruitpay.comm.annotation;

import java.lang.annotation.ElementType; 
import java.lang.annotation.Retention; 
import java.lang.annotation.RetentionPolicy; 
import java.lang.annotation.Target;
import com.fruitpay.base.comm.AllowRole;


@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface UserAccessValidate { 
	 /** 
	 * User has been legal login or not. 
	 * 
	 */ 	
	AllowRole[] value() default AllowRole.CUSTOMER; 
}
