package com.fruitpay.base.controller;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.ShipmentDay;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CheckoutService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.utils.HttpUtil;
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
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(ShipmentDay.Tuesday.getDay()));
		
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
	
	public void sendReq(HttpServletRequest request, HttpServletResponse hrp, String orderId) {
		
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(HttpUtil.getDomainURL(request) + "/allpayCtrl/checkout");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderId", orderId));

			httpPost.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200){
				InputStream input = response.getEntity().getContent();
				hrp.getWriter().print(input);
				client.close();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
