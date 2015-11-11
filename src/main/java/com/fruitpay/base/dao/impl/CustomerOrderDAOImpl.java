package com.fruitpay.base.dao.impl;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CustomerOrder;

@Repository("CustomerOrderDAOImpl")
public class CustomerOrderDAOImpl extends AbstractJPADAO<CustomerOrder> implements CustomerOrderDAO {

	
	@Override
	public boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		
		Query q = getEntityManager().createQuery(
				"UPDATE CustomerOrder c SET c.orderStatus.orderStatusId = ?1 WHERE c.orderId = ?2");
		q.setParameter(1, orderStatus.getStatus());
		q.setParameter(2, orderId);
		int count = q.executeUpdate();
		return count > 0 ? true : false;
	}	

}
