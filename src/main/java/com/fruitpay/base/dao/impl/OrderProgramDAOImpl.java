package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.model.OrderProgram;

@Repository("OrderProgramDAOImpl")
public class OrderProgramDAOImpl extends AbstractJPADAO<OrderProgram>implements OrderProgramDAO {

}
