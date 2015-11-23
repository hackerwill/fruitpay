package com.fruitpay.base.dao.impl;

import java.io.Serializable;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Village;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.AssertUtils;

@Repository("CustomerDAOImpl")
public class CustomerDAOImpl extends AbstractJPADAO<Customer> implements CustomerDAO {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Customer findById(Serializable id) {
		Customer customer = super.findById(id);
		return setVillageRelatedData(customer);
	}
	
	@Override
	public Customer getCustomerByEmail(String email){
		
		Query q = getEntityManager().createQuery("SELECT c FROM Customer c WHERE c.email = ?1");
		q.setParameter(1, email);
		
		Customer customer = null;
		try{
			customer = (Customer)q.getSingleResult();
			customer = setVillageRelatedData(customer);
		}catch(NoResultException e){
			logger.debug("the customer of email : " + email + " is not found.");;
		}
		return customer;
	}
	
	
	@Override
	public Customer findByFbId(String fbId) {
		Query q = getEntityManager().createQuery("SELECT c FROM Customer c WHERE c.fbId = ?1");
		q.setParameter(1, fbId);
		
		Customer customer = null;
		try{
			customer = (Customer)q.getSingleResult();
			customer = setVillageRelatedData(customer);
		}catch(NoResultException e){
			logger.debug("the customer of fbId : " + fbId + " is not found.");;
		}
		return customer;
	}

	@Override
	public boolean isEmailMatchPassword(String email, String password) {

		Query q = getEntityManager().createQuery("SELECT c FROM Customer c WHERE c.email = ?1 AND c.password = ?2 ");
		q.setParameter(1, email);
		q.setParameter(2, password);
		Customer customer = null;
		try{
			customer = (Customer)q.getSingleResult();
		}catch(NoResultException e){
			logger.debug("the customer of email : " + email + " is not found.");;
		}
		
		if(customer != null){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean isCustomerIdMatchPassword(Integer customerId, String password) {

		Query q = getEntityManager().createQuery("SELECT c FROM Customer c WHERE c.customerId = ?1 AND c.password = ?2 ");
		q.setParameter(1, customerId);
		q.setParameter(2, password);
		Customer customer = null;
		try{
			customer = (Customer)q.getSingleResult();
		}catch(NoResultException e){
			logger.debug("the customer of customerId : " + customerId + " is not found.");;
		}
		
		if(customer != null){
			return true;
		}else{
			return false;
		}
	}
	
	private Customer setVillageRelatedData(Customer customer) {
		if(!AssertUtils.isEmpty(customer.getVillage())){
			Village village = customer.getVillage();
			village.setCounty(new SelectOption(village.getCountyCode(), village.getCountyName()));
			village.setTowership(new SelectOption(village.getTowershipCode(), village.getTowershipName()));
			village.setId(village.getVillageCode());
			village.setName(village.getVillageName());
		}
		return customer;
	}

	@Override
	public boolean isEmailExisted(String email) {
		
		Query q = getEntityManager().createQuery("SELECT count(1) FROM Customer c WHERE c.email = ?1 ");
		q.setParameter(1, email);
		
		long count = (long)q.getSingleResult();
		if(count == 0)
			return false;
		else
			return true;
	}

}
