package com.fruitpay.comm.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fruitpay.base.model.Customer;


public class AuthenticationUtil {
	
	private enum Session{
		Customer;
	}
	
	public static Customer getSessionCustomer(HttpServletRequest request, Integer customerId){
		HttpSession session = request.getSession();
		Customer customer = (Customer)session.getAttribute(Session.Customer.name());
		if(AssertUtils.isEmpty(session.getAttribute(Session.Customer.name())))
			return null;
		if(!customerId.equals(customer.getCustomerId()))
			return null;
		
		return customer;
	}
	
	public static void setSessionCustomer( HttpServletRequest request, Customer customer){
		HttpSession session = request.getSession();
		session.setAttribute(Session.Customer.name(), customer);
	}

}
