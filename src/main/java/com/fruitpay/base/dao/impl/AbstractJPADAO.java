package com.fruitpay.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import com.fruitpay.base.dao.DAO;
import com.fruitpay.base.model.AbstractDataBean;

public abstract class AbstractJPADAO<T extends AbstractDataBean> implements DAO<T> {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	private Class<T> modelClass;

	protected EntityManager getEntityManager() {
		logger.debug("enter getEntityManager method");
		return em;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getModelClass() {
		logger.debug("enter getModelClass method");
		if (modelClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			this.modelClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return modelClass;
	}

	private String getDomainClassName() {
		logger.debug("enter getDomainClassName method");
		return getModelClass().getName();
	}

	@Override
	public T create(T t) {
		logger.debug("enter create method");
		getEntityManager().persist(t);
		return t;
	}
	
	@Override
	public T createAndRefresh(T t) {
		logger.debug("enter create method");
		t = create(t);
		refresh(t);
		return t;
	}
	
	@Override
	public T createAndFlush(T t) {
		logger.debug("enter create method");
		getEntityManager().persist(t);
		getEntityManager().flush();
		return t;
	}
	
	@Override
	public T update(T t) {
		logger.debug("enter update method");
		this.setUpdateInfo(t);
		getEntityManager().merge(t);
		return t;
	}

	@Override
	public T findById(Serializable id) {
		logger.debug("enter findById method");
		T t = (T) getEntityManager().find(getModelClass(), id);
		return t;
	}

	@Override
	public List<T> listAll() {
		logger.debug("enter listAll method");
		return getEntityManager().createQuery("select a from " + getDomainClassName() + " a", getModelClass()).getResultList();
	}

	public void setUpdateInfo(T t) {
		logger.debug("enter setUpdateInfo method");
	}

	@Override
	public void deleteById(Serializable id) {
		logger.debug("enter deleteById method");
		getEntityManager().remove(findById(id));
	}

	@Override
	public long count() {
		logger.debug("enter count method");
		return ((Number) getEntityManager().createQuery(
				"select count(*) from " + getDomainClassName()).getSingleResult()).longValue();
	}

	@Override
	public boolean isIdExist(Serializable id) {
		logger.debug("enter isIdExist method");
		return (findById(id) != null);
	}
	
	@Override
	public T refresh(T t){
		logger.debug("enter refresh method");
		getEntityManager().flush();
		getEntityManager().refresh(t);
		return t;
	}
}
