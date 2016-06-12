package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.comm.NeedRecordEnum;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderCommentDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;
import com.fruitpay.base.model.OrderCondition;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.FieldChangeRecordService;
import com.fruitpay.base.service.StaticDataService;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject 
	private CustomerOrderDAO customerOrderDAO;
	@Inject 
	private CustomerDAO customerDAO;
	@Inject 
	private CustomerService customerService;
	@Inject 
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject 
	private StaticDataService staticDataService;
	@Inject 
	private FieldChangeRecordService fieldChangeRecordService;
	@Inject 
	private OrderCommentDAO orderCommentDAO;
	
	@Override
	public CustomerOrder getCustomerOrder(Integer orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		return customerOrder;
	}

	@Override
	public List<CustomerOrder> getCustomerOrdersByCustomerId(Integer customerId) {
		Customer customer = customerDAO.findOne(customerId);
		if(customer == null)
			throw new HttpServiceException(ReturnMessageEnum.Login.AccountNotFound.getReturnMessage());
		List<CustomerOrder> customerOrders = customerOrderDAO.findByCustomerAndValidFlag(customer, CommConst.VALID_FLAG.VALID.value());
		
		return customerOrders;
	}

	@Override
	@Transactional
	public CustomerOrder updateCustomerOrder(CustomerOrder customerOrder) {
		CustomerOrder origin = customerOrderDAO.findOne(customerOrder.getOrderId());
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		
		//若沒有顧客, 使用原本的即可
		if(customerOrder.getCustomer() == null) {
			customerOrder.setCustomer(origin.getCustomer());
		}
		BeanUtils.copyProperties(customerOrder, origin);
		origin = customerOrderDAO.saveAndFlush(origin);
		customerService.update(customerOrder.getCustomer());
		return origin;
	}

	@Override
	@Transactional
	public CustomerOrder addCustomerOrder(CustomerOrder customerOrder) {
		customerOrder.setValidFlag(CommConst.VALID_FLAG.VALID.value());
		customerOrder = customerOrderDAO.saveAndFlush(customerOrder);
		return customerOrder;
	}

	@Override
	public Page<CustomerOrder> getAllCustomerOrder(int validFlag, int page , int size) {
		Page<CustomerOrder> customerOrders = customerOrderDAO.findByValidFlag(validFlag, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		
		return customerOrders;
	}
	
	@Override
	@Transactional
	public void deleteOrder(List<CustomerOrder> customerOrders) {
		
		for (Iterator<CustomerOrder> iterator = customerOrders.iterator(); iterator.hasNext();) {
			CustomerOrder customerOrder = iterator.next();
			customerOrderDAO.delete(customerOrder);
		}
	}

	@Override
	@Transactional
	public void deleteOrder(CustomerOrder customerOrder) {
		
		customerOrderDAO.delete(customerOrder);
	}

	@Override
	@Transactional
	public void moveToTrash(List<CustomerOrder> customerOrders) {
		customerOrders.forEach(order -> customerOrderDAO.findOne(order.getOrderId()).setValidFlag(CommConst.VALID_FLAG.INVALID.value()));
	}

	@Override
	@Transactional
	public void recover(List<CustomerOrder> customerOrders) {
		customerOrders.forEach(order -> customerOrderDAO.findOne(order.getOrderId()).setValidFlag(CommConst.VALID_FLAG.VALID.value()));
	}

	@Override
	public CustomerOrder getCustomerOrdersByValidFlag(Integer orderId, int validFlag) {
		CustomerOrder customerOrder =  customerOrderDAO.findByOrderIdAndValidFlag(orderId, validFlag);
		return customerOrder;
	}

	@Override
	public List<CustomerOrder> findAllByConditions(OrderCondition orderCondition) {
		List<CustomerOrder> customerOrders = customerOrderDAO.findByConditions(
				orderCondition.getName(), 
				orderCondition.getOrderId(), 
				orderCondition.getStartDate(),
				orderCondition.getEndDate(),
				orderCondition.getValidFlag(),
				orderCondition.getAllowForeignFruits(),
				orderCondition.getOrderStatusId());
		
		for (Iterator<CustomerOrder> iterator = customerOrders.iterator(); iterator.hasNext();) {
			CustomerOrder customerOrder = iterator.next();
			//只抓0的出來，為了效能的關係
			customerOrder.setOrderPreferences(orderPreferenceDAO.findByCustomerOrderAndLikeDegree(customerOrder, (byte)0));
		}
		return customerOrders;
	}
	
	@Override
	public Page<CustomerOrder> findAllByConditions(OrderCondition orderCondition, int page, int size) {
		Page<CustomerOrder> customerOrders = customerOrderDAO.findByConditions(
				orderCondition.getName(), 
				orderCondition.getOrderId(), 
				orderCondition.getStartDate(),
				orderCondition.getEndDate(),
				orderCondition.getValidFlag(),
				orderCondition.getAllowForeignFruits(),
				orderCondition.getOrderStatusId(),
				new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		return customerOrders;
	}

	@Override
	public CustomerOrder findOneIncludingOrderPreference(Integer orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		customerOrder.setOrderPreferences(orderPreferenceDAO.findByCustomerOrder(customerOrder));
		return customerOrder;
	}

	@Override
	public LocalDate findOrderFirstDeliveryDate(int orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), 
				DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName())));
		return firstDeliveryDate;
	}

	@Override
	@Transactional
	public CustomerOrder recoverTotalPrice(int orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		logger.info(fieldChangeRecordService);
		int totalPrice = fieldChangeRecordService.findLastRecord(NeedRecordEnum.CustomerOrder.totalPrice, orderId, Integer.class);
		customerOrder.setTotalPrice(totalPrice);
		customerOrder = customerOrderDAO.save(customerOrder);
		return customerOrder;
	}
	
	@Override
	@Transactional
	public CustomerOrder recoverOrderStatus(int orderId) {
		CustomerOrder customerOrder = customerOrderDAO.findOne(orderId);
		OrderStatus orderStatus = fieldChangeRecordService.findLastRecord(NeedRecordEnum.CustomerOrder.orderStatus, orderId, OrderStatus.class);
		customerOrder.setOrderStatus(orderStatus);
		customerOrder = customerOrderDAO.save(customerOrder);
		return customerOrder;
	}

	@Override
	public List<CustomerOrder> findByOrderIdsIncludingPreferenceAndComments(List<Integer> orderIds) {
		List<CustomerOrder> customerOrders = customerOrderDAO.findByOrderIdIn(orderIds);
		List<OrderPreference> orderPreferences = orderPreferenceDAO.findByCustomerOrderIn(customerOrders);
		List<OrderComment> orderComments = orderCommentDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		
		customerOrders = customerOrders.stream().map(customerOrder -> {
			List<OrderPreference> thisPreferences = orderPreferences.stream().filter(orderPreference -> {
				return orderPreference.getCustomerOrder().getOrderId().equals(customerOrder.getOrderId());
			}).collect(Collectors.toList());
			
			List<OrderComment> thisOrderComments = orderComments.stream().filter(orderComment -> {
				return orderComment.getCustomerOrder().getOrderId().equals(customerOrder.getOrderId());
			}).collect(Collectors.toList());
			
			customerOrder.setOrderPreferences(thisPreferences);
			customerOrder.setOrderComments(thisOrderComments);
			return customerOrder;
		}).collect(Collectors.toList());
		return customerOrders;
	}

	@Override
	@Transactional
	public OrderComment add(OrderComment orderComment) {
		orderComment.setValidFlag(VALID_FLAG.VALID.value());
		orderComment = orderCommentDAO.save(orderComment);
		return orderComment;
	}

	@Override
	@Transactional
	public OrderComment invalidate(int commentId) {
		OrderComment origin = orderCommentDAO.findOne(commentId);
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		
		origin.setValidFlag(VALID_FLAG.INVALID.value());
		return origin;
	}

	@Override
	public List<OrderComment> findCommentsByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<OrderComment> orderComments = orderCommentDAO.findByCustomerOrder(customerOrder);
		return orderComments;
	}

}
