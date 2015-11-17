package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.PaymentModeDAO;
import com.fruitpay.base.model.PaymentMode;

@Repository("PaymentModeDAOImpl")
public class PaymentModeDAOImpl extends AbstractJPADAO<PaymentMode>implements PaymentModeDAO {

}
