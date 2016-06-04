package com.fruitpay.comm.model;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;

public class ReturnMessage{

	private static final long serialVersionUID = 1L;
	
	protected String errorCode;		//錯誤代碼，0為無錯誤
	protected String message;		//錯誤信息
	protected HttpStatus status;	//HTTP錯誤代碼
	
	public ReturnMessage(String errorCode){
		super();
		this.errorCode = errorCode;
		this.message = "";
	}
	
	public ReturnMessage(Throwable ex){
		super();
		this.errorCode = ReturnMessageEnum.Status.Failed.getStatus();
		
		StringBuilder sb = new StringBuilder();
		Throwable child =  ex;
		while(child != null) {
			sb.append(child.getMessage() + System.lineSeparator());
			child = child.getCause();
		}
		
		this.message = sb.toString();
	}
	
	public ReturnMessage(String errorCode, String message, HttpStatus status) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.status = status;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return this.message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}
