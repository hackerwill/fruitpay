package com.fruitpay.comm.utils;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.auth.AuthenticationException;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fruitpay.base.model.AbstractEntity;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.auth.SysContent;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.session.model.FPSessionInfo;

@Component
public class AbstractEntityListener {
	
	@PrePersist
	protected void onCreate(AbstractEntity abstractEntity) {
		
		Customer user = getCurrentUser(abstractEntity);
		abstractEntity.setCreateUser(user);
		abstractEntity.setUpdateUser(user);
		abstractEntity.setCreateDate(new Date());
		abstractEntity.setUpdateDate(new Date());
	}
	
	@PreUpdate
	protected void onUpdate(AbstractEntity abstractEntity) {
		
		abstractEntity.setUpdateUser(getCurrentUser(abstractEntity));
		abstractEntity.setUpdateDate(new Date());
	}
	
	private Customer getCurrentUser(AbstractEntity abstractEntity){
		Customer user = null;
		CustomerService customerService = SpringApplicationContext.getBean(CustomerService.class);
		
		try{
			HttpServletRequest request = SysContent.getRequest();
			FPSessionInfo fpSessionInfo = FPSessionUtil.getFPsessionInfo(request);
			int userId = Integer.valueOf(fpSessionInfo.getUserId());
			user = customerService.findOne(userId);
		}catch(AuthenticationException e){
			//可能是在註冊的時候才會遇到這個問題
			if (abstractEntity instanceof Customer) {
				user = (Customer)abstractEntity;
			}
		}
		
		return user;
	}

}
