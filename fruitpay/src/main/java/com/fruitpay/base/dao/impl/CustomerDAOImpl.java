package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;

@Repository
public class CustomerDAOImpl extends AbstractJPADAO<Customer> implements CustomerDAO {

}
