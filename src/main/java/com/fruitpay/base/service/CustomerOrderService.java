package com.fruitpay.base.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.model.AllpayScheduleOrder;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;
import com.fruitpay.base.model.OrderCondition;

public interface CustomerOrderService {
	
	public AllpayScheduleOrder add(AllpayScheduleOrder allpayScheduleOrder);

	public CustomerOrder getCustomerOrder(Integer orderId);
	
	public CustomerOrder updateCustomerOrder(CustomerOrder customerOrder);
	
	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder);
	
	public Page<CustomerOrder> getAllCustomerOrder(int validFlag, int page , int size);
	
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId);
	
	public CustomerOrder getCustomerOrdersByValidFlag(Integer orderId, int validFlag);
	
	public void deleteOrder(CustomerOrder customerOrder);
	
	public void deleteOrder(List<CustomerOrder> customerOrders);
	
	public void moveToTrash(List<CustomerOrder> customerOrders);
	
	public void recover(List<CustomerOrder> customerOrders);
	
	public Page<CustomerOrder> findAllByConditions(OrderCondition orderCondition, int page , int size);
	
	public List<CustomerOrder> findAllByConditions(OrderCondition orderCondition);
	
	public List<CustomerOrder> findByOrderIdsIncludingPreferenceAndComments(List<Integer> orderIds);
	
	public CustomerOrder findOneIncludingOrderPreference(Integer orderId);
	
	public LocalDate findOrderFirstDeliveryDate(int orderId);
	
	public CustomerOrder recoverTotalPrice(int orderId);

	public CustomerOrder recoverOrderStatus(int orderId);
	
	public OrderComment add(OrderComment orderComment);
	
	public OrderComment invalidate(int commentId);
	
	public List<OrderComment> findCommentsByOrderId(int orderId);
	
	public boolean calculateDailyRecord(LocalDate localDate);

}
