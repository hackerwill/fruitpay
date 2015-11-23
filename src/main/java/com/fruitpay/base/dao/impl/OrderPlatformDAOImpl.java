package com.fruitpay.base.dao.impl;


import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.OrderPlatformDAO;
import com.fruitpay.base.model.OrderPlatform;

@Repository("OrderPlatformDAOImpl")
public class OrderPlatformDAOImpl extends AbstractJPADAO<OrderPlatform>implements OrderPlatformDAO {
	

}
