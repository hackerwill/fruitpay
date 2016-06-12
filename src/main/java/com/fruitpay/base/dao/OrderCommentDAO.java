package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;

public interface OrderCommentDAO extends JpaRepository<OrderComment, Integer> {
	
	List<OrderComment> findByCustomerOrder(CustomerOrder customerOrder);
	
	List<OrderComment> findByCustomerOrderInAndValidFlag(List<CustomerOrder> customerOrders, int validFlag);
	
}
