package com.fruitpay.base.service.impl;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.dao.VillageDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderPreferencePK;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.RadomValueUtil;

@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject
	private ProductDAO productDAO;
	@Inject
	private LoginService loginService;
	@Inject
	private VillageDAO villageDAO;
	@Inject
	private EmailSendService emailSendService;
	
	@Override
	public CustomerOrder checkoutOrder(CustomerOrder customerOrder) {
		logger.debug("add a customerOrder, email is " + customerOrder.getCustomer().getEmail());

		customerOrder = customerOrderDAO.create(customerOrder);
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			OrderPreferencePK id = new OrderPreferencePK();
			orderPreference.setProduct(productDAO.findById(orderPreference.getId().getProductId()));
			orderPreference.setCustomerOrder(customerOrder);
			id.setOrderId(orderPreference.getCustomerOrder().getOrderId());
			orderPreference.setId(id);
			
			orderPreferenceDAO.create(orderPreference);
		}
		
		//OrderProgram orderProgram = orderProgramDAO.findById(customerOrder.getOrderProgram().getProgramId());
		//customerOrder.setOrderProgram(orderProgram);
		
		return customerOrder;
	}

	@Override
	public CustomerOrder getCustomerOrder(Integer orderId) {
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
		String randomPassword = RadomValueUtil.getRandomPassword();
		customer.setPassword(randomPassword);
		
		ReturnData<Customer> returnData = loginService.signup(customer);
		if(!"0".equals(returnData.getErrorCode()))
			return ReturnMessageEnum.Order.AddCustomerFailed.getReturnMessage();
		
		customer = returnData.getObject();
		logger.debug("customer Id : " + customer.getCustomerId());
		customerOrder.setCustomer(customer);
		customerOrder = this.checkoutOrder(customerOrder);
		customerOrder.setVillage(villageDAO.findById(customerOrder.getVillage().getVillageCode()));
		customer.setVillage(villageDAO.findById(customer.getVillage().getVillageCode()));
		
		customer.setPassword(randomPassword);
		if(customerOrder!= null){
			emailSendService.sendTo(MailType.NEW_MEMBER_FROM_ORDER, customer.getEmail(), customerOrder);
			emailSendService.sendTo(MailType.NEW_ORDER, customer.getEmail(), customerOrder);	
		}
		
		return new ReturnObject<CustomerOrder>(customerOrder);
	}

}
