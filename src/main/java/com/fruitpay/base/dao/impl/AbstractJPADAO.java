package com.fruitpay.base.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import com.fruitpay.base.dao.DAO;
import com.fruitpay.base.model.AbstractDataBean;

public abstract class AbstractJPADAO<T extends AbstractDataBean> implements DAO<T> {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager entityManager;
	private Class<T> modelClass;

	protected EntityManager getEntityManager() {
		logger.debug("enter getEntityManager method");
		return entityManager;
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
		EntityManager em = getEntityManager();
		em.persist(t);
		em.flush();
		return t;
	}
	
	@Override
	public void update(T t) {
		logger.debug("enter update method");
		this.setUpdateInfo(t);
	}

	@Override
	public T findById(Serializable id) {
		logger.debug("enter findById method");
		return (T) getEntityManager().find(getModelClass(), id);
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
}
