package com.fruitpay.base.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Inject
	CustomerDAO customerDAO;

	@Override
	@Transactional
	public Customer update(Customer customer) {
		Customer origin = customerDAO.findOne(customer.getCustomerId());
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Login.AccountNotFound.getReturnMessage());
		
		BeanUtils.copyProperties(customer, origin);
		
		return origin;
	}

	@Override
	public Boolean isEmailExisted(String email) {
		if(customerDAO.findByEmail(email) != null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Customer> findAllCustomer() {
		List<Customer> customers = customerDAO.findAll();
		return customers;
	}

	@Override
	public Customer findCustomer(int customerId) {
		Customer customer = customerDAO.findOne(customerId);
		return customer;
	}
	
	@Override
	public Customer findByEmail(String email) {
		Customer customer = customerDAO.findByEmail(email);
		return customer;
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		customerDAO.saveAndFlush(customer);
		return customer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
