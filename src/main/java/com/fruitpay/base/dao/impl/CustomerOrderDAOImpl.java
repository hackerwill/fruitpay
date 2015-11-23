package com.fruitpay.base.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;

@Repository("CustomerOrderDAOImpl")
public class CustomerOrderDAOImpl extends AbstractJPADAO<CustomerOrder> implements CustomerOrderDAO {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		
		Query q = getEntityManager().createQuery(
				"UPDATE CustomerOrder c SET c.orderStatus.orderStatusId = ?1 WHERE c.orderId = ?2");
		q.setParameter(1, orderStatus.getStatus());
		q.setParameter(2, orderId);
		int count = q.executeUpdate();
		return count > 0 ? true : false;
	}

	@Override
	public List<CustomerOrder> findByCustomerId(Integer customerId) {
		Query q = getEntityManager().createQuery(
				"SELECT c FROM CustomerOrder c WHERE c.customer.customerId = ?1");
		q.setParameter(1, customerId);
		try{
			return (List<CustomerOrder>)q.getResultList();
		}catch(NoResultException e){
			logger.debug("the customerOrder of customerId : " + customerId + " is not found.");;
		}
		return null;
	}	

}
