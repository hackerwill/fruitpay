package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.model.OrderPreference;

@Repository("OrderPreferenceDAOImpl")
public class OrderPreferenceDAOImpl extends AbstractJPADAO<OrderPreference> implements OrderPreferenceDAO {

}
