package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.VillageDAO;
import com.fruitpay.base.model.Village;

@Repository("VillageDAOImpl")
public class VillageDAOImpl extends AbstractJPADAO<Village> implements VillageDAO {

}
