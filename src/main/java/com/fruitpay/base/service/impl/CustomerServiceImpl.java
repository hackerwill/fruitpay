package com.fruitpay.base.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Pwd;
import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.AssertUtils;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Inject
	CustomerDAO customerDAO;

	@Override
	@Transactional
	public ReturnData<Customer> update(Customer customer) {
		Customer origin = customerDAO.findOne(customer.getCustomerId());
		BeanUtils.copyProperties(customer, origin);
		if(origin == null){
			return ReturnMessageEnum.Login.AccountNotFound.getReturnMessage();
		}
		return new ReturnObject<Customer>(origin);
	}

	@Override
	public ReturnData<Boolean> isEmailExisted(String email) {
		if(customerDAO.findByEmail(email) != null){
			return new ReturnObject<Boolean>(true);
		}else{
			return new ReturnObject<Boolean>(false);
		}
	}

	@Override
	public ReturnData<List<Customer>> findAllCustomer() {
		List<Customer> customers = customerDAO.findAll();
		return new ReturnObject<List<Customer>>(customers);
	}

	@Override
	public ReturnData<Customer> findCustomer(int customerId) {
		Customer customer = customerDAO.findOne(customerId);
		customer = setVillageRelatedData(customer);
		return new ReturnObject<Customer>(customer);
	}
	
	@Override
	public ReturnData<Customer> findByEmail(String email) {
		Customer customer = customerDAO.findByEmail(email);
		customer = setVillageRelatedData(customer);
		return new ReturnObject<Customer>(customer);
	}

	@Override
	public ReturnData<Customer> saveCustomer(Customer customer) {
		customerDAO.saveAndFlush(customer);
		customer = setVillageRelatedData(customer);
		return new ReturnObject<Customer>(customer);
	}

	@Override
	public ReturnData<Customer> updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Customer setVillageRelatedData(Customer customer) {
		if(customer != null && !AssertUtils.isEmpty(customer.getVillage())){
//			customer = refresh(customer);
			Village village = customer.getVillage();
			village.setCounty(new SelectOption(village.getCountyCode(), village.getCountyName()));
			village.setTowership(new SelectOption(village.getTowershipCode(), village.getTowershipName()));
			village.setId(village.getVillageCode());
			village.setName(village.getVillageName());
		}
		return customer;
	}

}
