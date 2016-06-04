package com.fruitpay.comm.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.UserRole;
import com.fruitpay.base.service.UserRoleService;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.auth.FPAuthentication;
import com.fruitpay.comm.auth.SysContent;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionInfo;

/**
 * Session AOP切面 
 * 
 */

@Component
@Aspect
public class SessionAOP {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject UserRoleService userRoleService;
	
	@Around(value = "@annotation(com.fruitpay.comm.annotation.UserAccessValidate)")
	public Object aroundManager(ProceedingJoinPoint pj) throws Throwable {
		HttpServletRequest request = SysContent.getRequest();

		AllowRole[] allowRoles = this.getSessionType(pj);
		if (allowRoles == null) 
			throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
		
		//驗證token
		FPSessionInfo fpSessionInfo;
		try {
			fpSessionInfo = FPSessionUtil.getFPsessionInfo(request);
		} catch (AuthenticationException e) {
			logger.error(e);
			throw new HttpServiceException(ReturnMessageEnum.Login.RequiredLogin.getReturnMessage());
		}
		
		//所有的Role清單
		List<UserRole> userRoles = userRoleService.findByUserId(Integer.valueOf(fpSessionInfo.getUserId()));
		
		if(!isRoleMatch(allowRoles, userRoles))
			throw new HttpServiceException(ReturnMessageEnum.Common.AuthenticationFailed.getReturnMessage());
		
		return pj.proceed();
	}
	
	private boolean isRoleMatch(AllowRole[] allowRoles, List<UserRole> userRoles){
		
		//若權限為顧客的話,代表每個人都可以
		for (int i = 0; i < allowRoles.length; i++) {
			if(allowRoles[i].equals(AllowRole.CUSTOMER))
				return true;
		}
		
		for (int i = 0; i < allowRoles.length; i++) {
			AllowRole allowRole = allowRoles[i];
			for (int j = 0; j < userRoles.size(); j++) {
				UserRole userRole = userRoles.get(j);
				if(userRole.getRole().getRoleType().getOptionName().equals(allowRole.toString()))
					return true;
			}
		}
	
		return false;
	}

	private AllowRole[] getSessionType(ProceedingJoinPoint pj) {
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