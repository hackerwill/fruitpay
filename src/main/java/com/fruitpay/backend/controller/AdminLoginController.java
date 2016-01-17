package com.fruitpay.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.backend.model.Manager;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.comm.utils.AssertUtils;

@Controller
@RequestMapping("AdminloginCtrl")
public class AdminLoginController {
	
	private final String MANAGER_ID = "FruitpayAdmin";
	private final String PASSWORD = "FruitpayGreat";
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Manager login(@RequestBody Manager manager){
		if(AssertUtils.isEmpty(manager.getManagerId()) || 
				AssertUtils.isEmpty(manager.getPassword()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		if(!isMatched(manager))
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailPasswordNotMatch.getReturnMessage());

		return manager;
	}
	
	private boolean isMatched(Manager manager){
		return MANAGER_ID.equals(manager.getManagerId()) &&
				PASSWORD.equals(manager.getPassword());
	}
	

}
