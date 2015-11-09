package com.fruitpay.base.dao.impl;


import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CustomerOrder;

@Repository("CustomerOrderDAOImpl")
public class CustomerOrderDAOImpl extends AbstractJPADAO<CustomerOrder> implements CustomerOrderDAO {

}
