package com.fruitpay.comm.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fruitpay.base.comm.UserAuthStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.auth.FPAuthentication;
import com.fruitpay.comm.auth.SysContent;

/**
 * Session AOP切面 
 * 
 */

@Component
@Aspect
public class SessionAOP {
	
	@Around(value = "@annotation(com.fruitpay.comm.annotation.UserAccessValidate)")
	public Object aroundManager(ProceedingJoinPoint pj) throws Exception {
		HttpServletRequest request = SysContent.getRequest();
		HttpServletResponse response = SysContent.getResponse();

		UserAuthStatus type = this.getSessionType(pj);
		if (type == null) {
			throw new Exception("The value of NeedSession is must.");
		}

		boolean validateisYes = type == UserAuthStatus.YES && FPAuthentication.validateFPToken(request); 
		boolean validateisNo = type == UserAuthStatus.NO ; 
        boolean validateisAdmin = type == UserAuthStatus.ADMIN && FPAuthentication.validateAdmin(request);

			if (validateisYes || validateisNo || validateisAdmin) {				
				try {
					return pj.proceed();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else{
				throw new HttpServiceException(ReturnMessageEnum.Common.AuthenticationFailed.getReturnMessage());
			}
		return null;
	}

	private UserAuthStatus getSessionType(ProceedingJoinPoint pj) {
		// 獲取切入的 Method
		MethodSignature joinPointObject = (MethodSignature) pj.getSignature();
		Method method = joinPointObject.getMethod();
		boolean flag = method.isAnnotationPresent(UserAccessValidate.class);
		if (flag) {
			UserAccessValidate annotation = method.getAnnotation(UserAccessValidate.class);
			return annotation.value();
		}
		return null;
	}

}