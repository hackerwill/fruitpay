package com.fruitpay.base.service.impl;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.CommConst.CREDIT_CARD_PERIOD;
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.model.AllpayOrder;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.CouponService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.base.service.StaticDataService;

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
	
	@Inject
	private CouponService couponService;

	@Override
	@Transactional
	public CustomerOrder getCustomerOrder(Integer customerId, Integer orderId) {
		return customerOrderDAO.findOne(orderId);
	}

	@Override
	@Transactional
	public CustomerOrder updateOrderStatus(int orderId, OrderStatus orderStatus, AllpayOrder allpayOrder) {
		CustomerOrder customerOrder = customerOrderDAO.getOne(orderId);
		customerOrder.setOrderStatus(orderStatusDAO.getOne(orderStatus.getStatus()));
		customerOrder.setAllpayOrder(allpayOrder);
		return customerOrder;
	}

	@Override
	@Transactional
	public CustomerOrder checkoutOrder(Customer customer, CustomerOrder customerOrder) {
		
		logger.debug("add a customer, email is " + customer.getEmail());
		
		Customer persistCustomer = customerDAO.findOne(customer.getCustomerId());
		
		persistCustomer.setBirthday(customer.getBirthday());
		persistCustomer.setAddress(customer.getAddress());
		persistCustomer.setCellphone(customer.getCellphone());
		persistCustomer.setEmail(customer.getEmail());
		persistCustomer.setFirstName(customer.getFirstName());
		persistCustomer.setGender(customer.getGender());
		persistCustomer.setHousePhone(customer.getHousePhone());
		persistCustomer.setLastName(customer.getLastName());
		persistCustomer.setPostalCode(customer.getPostalCode());
		
		customerOrder.setCustomer(persistCustomer);
		
		logger.debug("add a customerOrder, email is " + customerOrder.getCustomer().getEmail());
		
		customerOrder.setValidFlag(CommConst.VALID_FLAG.VALID.value());
		customerOrderDAO.save(customerOrder);
		
		return customerOrder;
	}
	
	@Override
	public int getTotalPrice(CustomerOrder customerOrder){
		return getTotalPriceWithoutShipment(customerOrder) 
				+ customerOrder.getPaymentMode().getPaymentExtraPrice();
	}

	@Override
	public int getTotalPriceWithoutShipment(CustomerOrder customerOrder) {
		//加上Coupon打折的金額
		int totalTimes = CREDIT_CARD_PERIOD.PERIOD.value() / customerOrder.getShipmentPeriod().getDuration();
		int totalProductsPrice = totalTimes * customerOrder.getOrderProgram().getPrice() * customerOrder.getProgramNum();
		for (int i = 0; i < customerOrder.getCoupons().size(); i++) {
			Coupon coupon = customerOrder.getCoupons().get(i);
			totalProductsPrice = couponService.countFinalPrice(coupon, totalProductsPrice);
		}
		return totalProductsPrice;
	}

}
