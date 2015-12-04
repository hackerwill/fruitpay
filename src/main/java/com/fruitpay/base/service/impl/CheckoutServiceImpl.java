package com.fruitpay.base.service.impl;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderPreferencePK;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.service.EmailSendService;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@PersistenceContext
	private EntityManager em;
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject
	private ProductDAO productDAO;
	@Inject
	private LoginService loginService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private EmailSendService emailSendService;
	@Inject
	private CustomerDAO customerDAO;

	@Override
	@Transactional
	public CustomerOrder getCustomerOrder(Integer customerId, Integer orderId) {
		return customerOrderDAO.findById(orderId);
	}

	@Override
	@Transactional
	public Boolean updateOrderStatus(Integer orderId, OrderStatus orderStatus) {
		return customerOrderDAO.updateOrderStatus(orderId, orderStatus);
	}

	@Override
	@Transactional
	public ReturnData<CustomerOrder> checkoutOrder(Customer customer, CustomerOrder customerOrder) {
		
		logger.debug("add a customer, email is " + customer.getEmail());
		
		if(!customerDAO.isEmailExisted(customer.getEmail())){
			
			ReturnData<Customer> returnData = loginService.signup(customer);
			if(!"0".equals(returnData.getErrorCode()))
				return ReturnMessageEnum.Order.AddCustomerFailed.getReturnMessage();
			
			customer = returnData.getObject();
			customerOrder.setCustomer(customer);
		}else{
			
			Customer persistCustomer = customerDAO.findById(customer.getCustomerId());
			
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
		
		customerOrderDAO.createAndFlush(customerOrder);
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			OrderPreferencePK id = new OrderPreferencePK();
			orderPreference.setProduct(productDAO.findById(orderPreference.getId().getProductId()));
			orderPreference.setCustomerOrder(customerOrder);
			id.setOrderId(orderPreference.getCustomerOrder().getOrderId());
			orderPreference.setId(id);
			orderPreferenceDAO.create(orderPreference);
		}
		
		customerOrder.setVillage(staticDataService.getVillage(customerOrder.getVillage().getVillageCode()));
		customer.setVillage(staticDataService.getVillage(customer.getVillage().getVillageCode()));
		return new ReturnObject<CustomerOrder>(customerOrder);
	}

}
