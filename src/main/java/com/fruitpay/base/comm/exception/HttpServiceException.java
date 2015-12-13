package com.fruitpay.base.comm.exception;

import org.springframework.http.HttpStatus;

import com.fruitpay.comm.model.ReturnMessage;

public class HttpServiceException extends RuntimeException {
	private static final long serialVersionUID = 3976892301056166141L;
	private ReturnMessage returnMessage;
	
	public HttpServiceException(ReturnMessage returnMessage){
		 super(returnMessage.getMessage());
		 this.returnMessage = returnMessage;
	}

	public HttpStatus getStatus() {
		return returnMessage.getStatus();
	}

	public String getErrorCode() {
		return returnMessage.getErrorCode();
	}

	public ReturnMessage getReturnMessage() {
		return returnMessage;
	}
	
}
