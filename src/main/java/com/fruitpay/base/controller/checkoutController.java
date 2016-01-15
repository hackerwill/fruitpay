package com.fruitpay.base.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.RadomValueUtil;

@Controller
@RequestMapping("checkoutCtrl")
public class checkoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private CustomerService customerService;
	@Inject
	private EmailSendService emailSendService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		
		customerOrder.setOrderDate(Calendar.getInstance().getTime());
		customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreadyCheckout.getStatus()));
		//前一天
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(
				getPreviousDayInt(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()))));
		customerOrder.setShippingCost(customerOrder.getPaymentMode().getPaymentExtraPrice());
		customerOrder.setTotalPrice(checkoutService.getTotalPrice(customerOrder));
		
		
		if(customer == null || customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		String randomPassword = RadomValueUtil.getRandomPassword();
		customer.setPassword(randomPassword);
		
		boolean isEmailExisted = customerService.isEmailExisted(customer.getEmail());
		Customer sendCustomer = null;
		if(!isEmailExisted){
			sendCustomer = new Customer();
			sendCustomer.setPassword(randomPassword);
			sendCustomer.setFirstName(customer.getFirstName());
			sendCustomer.setLastName(customer.getLastName());
			sendCustomer.setEmail(customer.getEmail());
		}
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			orderPreference.setCustomerOrder(customerOrder);
		}
		
		customerOrder = checkoutService.checkoutOrder(customer, customerOrder);
		
		if(sendCustomer!= null){
			emailSendService.sendTo(MailType.NEW_MEMBER_FROM_ORDER, sendCustomer.getEmail(), sendCustomer);
		}
		if(customerOrder!= null ){
			CustomerOrder sendCustomerOrder = new CustomerOrder();
			BeanUtils.copyProperties(customerOrder, sendCustomerOrder);
			emailSendService.sendTo(MailType.NEW_ORDER, sendCustomerOrder.getCustomer().getEmail(), sendCustomerOrder);	
		}
		
		return customerOrder;
	}
	
	private int getPreviousDayInt(int day){
		if(day == 1)
			return 7;
		else
			return day - 1;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody Integer getTotalPrice(){
		
		return 10;
	}
	
	@RequestMapping(value = "/totalPrice", method = RequestMethod.POST)
	public @ResponseBody Integer getTotalPrice(@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null || customerOrder.getOrderProgram() == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		return checkoutService.getTotalPrice(customerOrder);
	}
	
	@RequestMapping(value = "/totalPriceWithoutShipment", method = RequestMethod.POST)
	public @ResponseBody Integer getTotalPriceWithoutShipment(@RequestBody CustomerOrder customerOrder){
		
		if(customerOrder == null || customerOrder.getOrderProgram() == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		return checkoutService.getTotalPriceWithoutShipment(customerOrder);
	}
	
	@RequestMapping(value = "/getReceiveDay/{dayOfWeek}", method = RequestMethod.GET)
	public @ResponseBody DateStr getReceiveDay(@PathVariable int dayOfWeek){
		
		DateStr dateStr = new DateStr(staticDataService.getNextReceiveDay(Calendar.getInstance().getTime(), DayOfWeek.of(dayOfWeek)));
		return dateStr;
	}
	
	private class DateStr{
		private String date;
		
		public DateStr(String date){
			this.date = date;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
	}
	
}
