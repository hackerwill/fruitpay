package com.fruitpay.base.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private LoginService loginService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private CustomerDAO customerDAO;
	@Inject
	private OrderStatusDAO orderStatusDAO;

	@Override
	@Transactional
	public CustomerOrder getCustomerOrder(Integer customerId, Integer orderId) {
		return customerOrderDAO.findOne(orderId);
	}

	@Override
	@Transactional
	public Boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		CustomerOrder customerOrder = customerOrderDAO.getOne(orderId);
		customerOrder.setOrderStatus(orderStatusDAO.getOne(orderStatus.getStatus()));
		return true;
	}

	@Override
	@Transactional
	public ReturnData<CustomerOrder> checkoutOrder(Customer customer, CustomerOrder customerOrder) {
		
		logger.debug("add a customer, email is " + customer.getEmail());
		
		if(customerDAO.findByEmail(customer.getEmail()) == null){
			
			ReturnData<Customer> returnData = loginService.signup(customer);
			if(!"0".equals(returnData.getErrorCode()))
				return ReturnMessageEnum.Order.AddCustomerFailed.getReturnMessage();
			
			customer = returnData.getObject();
			customerOrder.setCustomer(customer);
		}else{
			
			Customer persistCustomer = customerDAO.findOne(customer.getCustomerId());
			
			persistCustomer.setBirthday(customer.getBirthday());
			persistCustomer.setCellphone(customer.getCellphone());
			persistCustomer.setEmail(customer.getEmail());
			persistCustomer.setFirstName(customer.getFirstName());
			persistCustomer.setGender(customer.getGender());
			persistCustomer.setHousePhone(customer.getHousePhone());
			persistCustomer.setLastName(customer.getLastName());
			persistCustomer.setVillage(customer.getVillage());
			persistCustomer.addCustomerOrder(customerOrder);
			
			customerOrder.setCustomer(persistCustomer);
		}
		
		logger.debug("add a customerOrder, email is " + customerOrder.getCustomer().getEmail());
		
		customerOrderDAO.saveAndFlush(customerOrder);
		
		customerOrder.setVillage(staticDataService.getVillage(customerOrder.getVillage().getVillageCode()));
		customer.setVillage(staticDataService.getVillage(customer.getVillage().getVillageCode()));
		return new ReturnObject<CustomerOrder>(customerOrder);
	}

}
