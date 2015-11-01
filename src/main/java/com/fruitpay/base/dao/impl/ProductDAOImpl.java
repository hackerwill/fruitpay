package com.fruitpay.base.dao.impl;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.model.Product;

@Repository("ProductDAOImpl")
public class ProductDAOImpl extends AbstractJPADAO<Product> implements ProductDAO {

}
