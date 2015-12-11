package com.fruitpay.base.dao;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Pageable; 

import com.fruitpay.base.model.AbstractDataBean;

public interface DAO<T extends AbstractDataBean> {

	T create(T t);
	
	T createAndRefresh(T t);
	
	T createAndFlush(T t);

	T findById(Serializable id);

	List<T> listAll();
	
	List<T> listAll(Pageable pageable);

	T update(T t);

	void deleteById(Serializable id);

	long count();

	boolean isIdExist(Serializable id);
	
	T refresh(T t);
	
	T detach(T t);

}
