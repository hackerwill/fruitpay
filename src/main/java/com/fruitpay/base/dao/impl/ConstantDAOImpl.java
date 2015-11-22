package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.ConstantDAO;
import com.fruitpay.base.model.Constant;

@Repository("ConstantDAOImpl")
public class ConstantDAOImpl extends AbstractJPADAO<Constant> implements ConstantDAO {

}
