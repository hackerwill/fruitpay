package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.ShipmentDayDAO;
import com.fruitpay.base.model.ShipmentDay;

@Repository("ShipmentDayDAOImpl")
public class ShipmentDayDAOImpl extends AbstractJPADAO<ShipmentDay>implements ShipmentDayDAO {

}
