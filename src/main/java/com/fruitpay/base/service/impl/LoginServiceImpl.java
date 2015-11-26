package com.fruitpay.base.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.utils.Md5Util;

@Service
public class LoginServiceImpl implements LoginService {

	private final String FB_PASSWORD = "FB_PASSWORD";
	@Autowired
	@Qualifier("CustomerDAOImpl")
	CustomerDAO customerDAO;
	

	@Override
	public ReturnData<Customer> signup(Customer customer) {

		if(customerDAO.getCustomerByEmail(customer.getEmail()) != null){
			return ReturnMessageEnum.Login.EmailAlreadyExisted.getReturnMessage();
		}else{
			customer = getEncodedPasswordCustomer(customer);
			customer = customerDAO.create(customer); 
			
			return new ReturnObject<Customer>(
					ReturnMessageEnum.Common.Success.getReturnMessage(),
					customer);
		}
	}
	
	//加密密碼
	private Customer getEncodedPasswordCustomer(Customer customer){
		 customer.setPassword(Md5Util.getMd5(customer.getPassword()));
		 return customer;
	}

	@Override
	public ReturnData<Customer> login(String email, String password) {
		Customer customer = customerDAO.getCustomerByEmail(email); 
		if(customer == null){
			return ReturnMessageEnum.Login.EmailNotFound.getReturnMessage();
		}else if(!customerDAO.isEmailMatchPassword(email, Md5Util.getMd5(password))){
			return ReturnMessageEnum.Login.EmailPasswordNotMatch.getReturnMessage();
		}else{
			return new ReturnObject<Customer>(ReturnMessageEnum.Common.Success.getReturnMessage(),
					customer);
		}
	}
	
	@Override
	public ReturnData<Customer> loginByCustomerId(Integer customerId, String password) {
		Customer customer = customerDAO.findById(customerId);
		if(customer == null){
			return ReturnMessageEnum.Login.AccountNotFound.getReturnMessage();
		}else if(!customerDAO.isCustomerIdMatchPassword(customerId, password)){
			return ReturnMessageEnum.Login.AccountNotFound.getReturnMessage();
		}else{
			return new ReturnObject<Customer>(ReturnMessageEnum.Common.Success.getReturnMessage(),
					customer);
		}
	}

	@Override
	@Transactional
	public ReturnData<Customer> fbLogin(Customer customer) {
		Customer checkcustomer = customerDAO.findByFbId(customer.getFbId()); 
		if(checkcustomer == null){
			customer.setPassword(FB_PASSWORD);
			customer = getEncodedPasswordCustomer(customer);
			customer = customerDAO.createAndRefresh(customer); 
			checkcustomer = customer;
		}
		
		return new ReturnObject<Customer>(ReturnMessageEnum.Common.Success.getReturnMessage(),
				checkcustomer);
	}
	
	@Override
	@Transactional
	public ReturnData<Customer> changePassword(Pwd pwd) {
		Boolean matched = customerDAO.isCustomerIdMatchPassword(
				pwd.getCustomerId(), Md5Util.getMd5(pwd.getOldPassword()));
		Customer customer = null;
		if(matched){
			customer = customerDAO.findById(pwd.getCustomerId());
			customer.setPassword(Md5Util.getMd5(pwd.getNewPassword()));
		}else{
			return ReturnMessageEnum.Login.PasswordNotMatched.getReturnMessage();
		}
			
		return new ReturnObject<Customer>(customer);
	}
}
