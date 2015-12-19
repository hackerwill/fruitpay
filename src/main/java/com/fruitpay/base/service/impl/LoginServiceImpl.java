package com.fruitpay.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.utils.Md5Util;
import com.fruitpay.comm.utils.RadomValueUtil;

@Service
public class LoginServiceImpl implements LoginService {

	private final String FB_PASSWORD = "FB_PASSWORD";
	@Autowired
	CustomerDAO customerDAO;
	

	@Override
	@Transactional
	public Customer signup(Customer customer) {

		if(customerDAO.findByEmail(customer.getEmail()) != null)
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailAlreadyExisted.getReturnMessage());
		
		customer = getEncodedPasswordCustomer(customer);
		customer = customerDAO.saveAndFlush(customer);
		
		return customer;
	}
	
	//加密密碼
	private Customer getEncodedPasswordCustomer(Customer customer){
		 customer.setPassword(Md5Util.getMd5(customer.getPassword()));
		 return customer;
	}

	@Override
	@Transactional
	public Customer login(String email, String password) {
		Customer customer = customerDAO.findByEmail(email); 
		if(customer == null){
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailNotFound.getReturnMessage());
		}else if(!Md5Util.getMd5(password).equals(customer.getPassword())){
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailPasswordNotMatch.getReturnMessage());
		}else{
			return customer;
		}
	}
	
	@Override
	public Customer loginByCustomerId(Integer customerId, String password) {
		Customer customer = customerDAO.findOne(customerId);
		if(customer == null){
			throw new HttpServiceException(ReturnMessageEnum.Login.AccountNotFound.getReturnMessage());
		}else if(!password.equals(customer.getPassword())){
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailPasswordNotMatch.getReturnMessage());
		}
		
		return customer;
	}

	@Override
	@Transactional
	public Customer fbLogin(Customer customer) {
		Customer checkcustomer = customerDAO.findByFbId(customer.getFbId()); 
		if(checkcustomer == null){
			customer.setPassword(FB_PASSWORD);
			customer = getEncodedPasswordCustomer(customer);
			customer = customerDAO.saveAndFlush(customer); 
			checkcustomer = customer;
		}
		
		return checkcustomer;
	}
	
	@Override
	@Transactional
	public Customer changePassword(Pwd pwd) {
		Customer customer = customerDAO.findByCustomerIdAndPassword(pwd.getCustomerId(), Md5Util.getMd5(pwd.getOldPassword()));
		if(customer == null)
			throw new HttpServiceException(ReturnMessageEnum.Login.PasswordNotMatched.getReturnMessage());
		
		customer.setPassword(Md5Util.getMd5(pwd.getNewPassword()));
		return customer;
	}

	@Override
	@Transactional
	public Customer forgetPassword(String email, String newPassword) {
		Customer customer = customerDAO.findByEmail(email);
		if(customer == null)
			throw new HttpServiceException(ReturnMessageEnum.Login.EmailNotFound.getReturnMessage());
		
		customer.setPassword(Md5Util.getMd5(newPassword));
		return customer;
	}
}
