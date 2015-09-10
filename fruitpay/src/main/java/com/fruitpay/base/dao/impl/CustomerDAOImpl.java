package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;

@Service
public class CustomerDAOImpl extends AbstractJPADAO<Customer> implements CustomerDAO {

}
