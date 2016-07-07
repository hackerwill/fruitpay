package com.fruitpay.base.controller;


import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.utils.DateUtil;

@Controller
@RequestMapping("checkoutCtrl")
public class CheckoutController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private CheckoutService checkoutService;
	@Inject
	private StaticDataService staticDataService;
	
	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody CustomerOrder checkout(
			@RequestBody CheckoutPostBean checkoutPostBean,
			HttpServletRequest request, HttpServletResponse response){
		Customer customer = checkoutPostBean.getCustomer();
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		
		//貨到付款
		if(customerOrder.getPaymentMode().getPaymentModeId() == 2){
			customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreadyCheckout.getStatus()));
		//信用卡
		}else{
			customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.CreditPayFailed.getStatus()));
		}
		
		//前一天
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(
				DateUtil.getPreviousDayInt(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()))));
		customerOrder.setShippingCost(customerOrder.getPaymentMode().getPaymentExtraPrice());
		customerOrder.setTotalPrice(checkoutService.getTotalPrice(customerOrder));
		
		
		if(customer == null || customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			orderPreference.setCustomerOrder(customerOrder);
		}
		
		customerOrder = checkoutService.checkoutOrder(customer, customerOrder);
		
		return customerOrder;
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
		
		DateStr dateStr = new DateStr(staticDataService.getNextReceiveDayStr(Calendar.getInstance().getTime(), DayOfWeek.of(dayOfWeek)));
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

