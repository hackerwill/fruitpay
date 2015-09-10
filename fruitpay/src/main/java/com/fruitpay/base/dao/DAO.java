package com.fruitpay.base.dao;

import java.io.Serializable;
import java.util.List;

import com.fruitpay.base.model.AbstractDataBean;

public interface DAO<T extends AbstractDataBean> {

	void create(T t);

	T findById(Serializable id);

	List<T> listAll();

	void update(T t);

	void deleteById(Serializable id);

	long count();

	boolean isIdExist(Serializable id);

}