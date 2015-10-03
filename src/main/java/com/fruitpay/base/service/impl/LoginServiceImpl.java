package com.fruitpay.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fruitpay.base.comm.returndata.LoginReturnMessage;
import com.fruitpay.base.comm.returndata.ReturnData;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	@Qualifier("FakeCustomerDAOImpl")
	CustomerDAO customerDAO;

	@Override
	public ReturnData signup(Customer customer) {

		if(customerDAO.isEmailExisted(customer.getEmail())){
			return LoginReturnMessage.EmailAlreadyExisted.getReturnMessage();
		}else{
			customerDAO.create(customer); 
			return LoginReturnMessage.Success.getReturnMessage();
		}
	}

	@Override
	public ReturnData login(String email, String password) {

		if(!customerDAO.isEmailExisted(email)){
			return LoginReturnMessage.EmailNotFound.getReturnMessage();
		}else if(!customerDAO.isEmailMatchPassword(email, password)){
			return LoginReturnMessage.EmailNotFound.getReturnMessage();
		}else{
			return LoginReturnMessage.Success.getReturnMessage();
		}
	}
	
	
}
