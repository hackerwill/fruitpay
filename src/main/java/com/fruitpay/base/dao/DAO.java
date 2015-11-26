package com.fruitpay.base.dao;

import java.io.Serializable;
import java.util.List;

import com.fruitpay.base.model.AbstractDataBean;

public interface DAO<T extends AbstractDataBean> {

	T create(T t);
	
	T createAndRefresh(T t);
	
	T createAndFlush(T t);

	T findById(Serializable id);

	List<T> listAll();

	T update(T t);

	void deleteById(Serializable id);

	long count();

	boolean isIdExist(Serializable id);
	
	T refresh(T t);

}
