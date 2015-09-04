package com.fruitpay.base.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.TestDao;
import com.fruitpay.base.model.Test;

@Service
public class TestDaoImpl implements TestDao {

	@PersistenceContext
	private EntityManager em;
	
	public List<Test> getAllTask() {
		
		TypedQuery<Test> query = em.createQuery(
	            "SELECT t FROM Test t ", Test.class);
	        return query.getResultList();
	}

}
