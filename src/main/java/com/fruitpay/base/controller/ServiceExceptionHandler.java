package com.fruitpay.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.comm.model.ReturnMessage;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 錯誤處理回傳回Json格式字串
	 * 
	 * */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
    	if(ex instanceof HttpServiceException){
    		HttpServiceException serviceEx = (HttpServiceException)ex;
    		return new ResponseEntity<Object>(serviceEx.getReturnMessage(), serviceEx.getStatus());
    	}else{
    		ReturnMessage returnMessage = new ReturnMessage(ex);
    		return new ResponseEntity<Object>(returnMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    	}

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<String,String>();
        responseBody.put("path",request.getContextPath());
        responseBody.put("message","The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody,HttpStatus.NOT_FOUND);
    }
}