package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.AreaDAO;
import com.fruitpay.base.model.Area;

@Repository("AreaDAOImpl")
public class AreaDQOImpl extends AbstractJPADAO<Area>implements AreaDAO {

}
