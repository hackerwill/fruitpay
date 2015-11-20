package com.fruitpay.base.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Inject
	CustomerDAO customerDAO;

	@Override
	@Transactional
	public ReturnData<Customer> update(Customer customer) {
		Customer origin = customerDAO.update(customer);
		if(origin == null){
			return ReturnMessageEnum.Login.AccountNotFound.getReturnMessage();
		}
		return new ReturnObject<Customer>(origin);
	}

}
