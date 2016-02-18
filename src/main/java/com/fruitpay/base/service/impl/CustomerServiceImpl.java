package com.fruitpay.base.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.utils.AssertUtils;

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
		
		if(AssertUtils.isEmpty(customer.getPassword()))
			customer.setPassword(origin.getPassword());
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
	public Page<Customer> findAllCustomer(int page , int size) {
		PageRequest pageRequest = new PageRequest(page, size);
		Page<Customer> customers = customerDAO.findAll(pageRequest);
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
	public void deleteCustomer(Customer customer) {
		customerDAO.delete(customer);
	}

	@Override
	public Customer findByOrderId(Integer orderId) {
		Customer customer = customerDAO.findByOrderId(orderId);
		return customer;
	}
	
	@Override
	public Customer findByFbId(String fbId) {
		Customer customer = customerDAO.findByFbId(fbId);
		return customer;
	}
	
	@Override
	public String getCustomerNamesStr() {
		List<Customer> customers = customerDAO.findAll();
		StringBuilder names = new StringBuilder();
		boolean isFirstOne = true;
		for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
			Customer customer = iterator.next();
			String lastName = AssertUtils.isEmpty(customer.getLastName()) ? "" : customer.getLastName();
			String firstName = AssertUtils.isEmpty(customer.getFirstName()) ? "" : customer.getFirstName();
			String fullName = lastName + firstName + "(" + customer.getCustomerId() + ")";
			
			if(isFirstOne){
				names.append(fullName);
				isFirstOne = false;
			}else{
				names.append("," + fullName);
			}
				
		}
		return names.toString();
	
	}
	
}
