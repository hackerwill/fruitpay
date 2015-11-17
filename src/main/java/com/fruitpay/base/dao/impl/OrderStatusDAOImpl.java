package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.model.OrderStatus;

@Repository("OrderStatusDAOImpl")
public class OrderStatusDAOImpl extends AbstractJPADAO<OrderStatus>implements OrderStatusDAO {

}
