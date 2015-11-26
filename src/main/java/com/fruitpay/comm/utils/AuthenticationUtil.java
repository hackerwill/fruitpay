package com.fruitpay.comm.utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.Customer;

@Component
public class AuthenticationUtil {
	
	private enum Session{
		Customer;
	}
	
	public Integer getSessionCustomerId(HttpServletRequest request, Integer customerId){
		HttpSession session = request.getSession();
		Integer sessionCustomerId = (Integer)session.getAttribute(Session.Customer.name());
		if(AssertUtils.isEmpty(session.getAttribute(Session.Customer.name())))
			return null;
		
		return sessionCustomerId;
	}
	
	public void setSessionCustomer( HttpServletRequest request, Integer customerId){
		HttpSession session = request.getSession();
		session.setAttribute(Session.Customer.name(), customerId);
	}

}
