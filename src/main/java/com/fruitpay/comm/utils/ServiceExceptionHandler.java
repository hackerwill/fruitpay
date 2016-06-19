package com.fruitpay.comm.utils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
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
import com.fruitpay.base.dao.ExceptionLogDAO;
import com.fruitpay.base.model.ExceptionLog;
import com.fruitpay.comm.model.ReturnMessage;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject 
	private ExceptionLogDAO exceptionLogDAO;
	
	/**
	 * 錯誤處理回傳回Json格式字串
	 * 
	 * */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {
    	ReturnMessage returnMessage = null;
    	HttpStatus status = null;
    	if(ex instanceof HttpServiceException){
    		HttpServiceException serviceEx = (HttpServiceException)ex;
    		returnMessage = serviceEx.getReturnMessage();
    		status = serviceEx.getStatus();
    	}else{
    		ex.printStackTrace();
    		returnMessage = new ReturnMessage(ex);
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
    	ExceptionLog exceptionLog = new ExceptionLog(status.toString(), returnMessage.getMessage(), getIp(req));
    	exceptionLogDAO.save(exceptionLog);
    	return new ResponseEntity<Object>(returnMessage, status);
    }
    
    private String getIp(HttpServletRequest req) {
    	String ipAddress = req.getHeader("X-FORWARDED-FOR");
    	if (ipAddress == null) {
    	    ipAddress = req.getRemoteAddr();
    	}
    	
    	return ipAddress;
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String,String> responseBody = new HashMap<String,String>();
        responseBody.put("path",request.getContextPath());
        responseBody.put("message","The URL you have reached is not in service at this time (404).");
        return new ResponseEntity<Object>(responseBody,HttpStatus.NOT_FOUND);
    }
}