package com.fruitpay.comm.auth;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fruitpay.base.comm.UserAuthStatus;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;

/**
 * Session AOP切面 
 * 
 */
@Component
@Aspect
public class SessionAOP {
	
	@Around(value = "@annotation(com.fruitpay.comm.auth.UserAccessAnnotation)")
	public Object aroundManager(ProceedingJoinPoint pj) throws Exception {
		HttpServletRequest request = SysContent.getRequest();
		HttpServletResponse response = SysContent.getResponse();

		UserAuthStatus type = this.getSessionType(pj);
		if (type == null) {
			throw new Exception("The value of NeedSession is must.");
		}

		boolean validateisYes = type == UserAuthStatus.YES && FPAuthentication.validateFPToken(request); 
		boolean validateisNo = type == UserAuthStatus.NO && FPAuthentication.validateFPToken(request); 

		try {
			if (validateisYes) {				
				return pj.proceed();
			} else if(validateisNo){				
				response.getWriter().print(ReturnMessageEnum.Common.AuthenticationFailed.getReturnMessage());   
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private UserAuthStatus getSessionType(ProceedingJoinPoint pj) {
		// 獲取切入的 Method
		MethodSignature joinPointObject = (MethodSignature) pj.getSignature();
		Method method = joinPointObject.getMethod();
		boolean flag = method.isAnnotationPresent(UserAccessAnnotation.class);
		if (flag) {
			UserAccessAnnotation annotation = method.getAnnotation(UserAccessAnnotation.class);
			return annotation.value();
		}
		return null;
	}

}