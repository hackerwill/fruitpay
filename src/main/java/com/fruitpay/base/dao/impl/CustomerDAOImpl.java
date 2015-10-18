package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;

@Repository("CustomerDAOImpl")
public class CustomerDAOImpl extends AbstractJPADAO<Customer> implements CustomerDAO {

	@Override
	public boolean isEmailExisted(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmailMatchPassword(String email, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
