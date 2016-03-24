package com.fruitpay.base.controller;

import java.io.OutputStream;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.UserAuthStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderCondition;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.auth.UserAccessAnnotation;
import com.fruitpay.comm.model.OrderExcelBean;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.ExcelUtil;
import com.fruitpay.comm.utils.StringUtil;

@Controller
@RequestMapping("orderCtrl")
public class CustomerOrderController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	CustomerOrderDAO customerOrderDAO;
	@Inject
	private EmailSendService emailSendService;

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public @ResponseBody CustomerOrder addOrder(@RequestBody CheckoutPostBean checkoutPostBean) {
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		Customer customer = checkoutPostBean.getCustomer();

		customerOrder.setCustomer(customer);
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(DayOfWeek.TUESDAY.getValue()));

		if (customer == null || customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		for (Iterator<OrderPreference> iterator = customerOrder.getOrderPreferences().iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			orderPreference.setCustomerOrder(customerOrder);
		}
		customerOrder = customerOrderService.addCustomerOrder(customerOrder);

		return customerOrder;
	}

	@RequestMapping(value = "/order", method = RequestMethod.PUT)
	public @ResponseBody CustomerOrder updateOrder(@RequestBody CustomerOrder customerOrder) {

		if (customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrder = customerOrderService.updateCustomerOrder(customerOrder);
		return customerOrder;
	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	@UserAccessAnnotation(UserAuthStatus.YES)
	public @ResponseBody CustomerOrder getOrder(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(orderId);

		return customerOrder;
	}
	
	@RequestMapping(value = "/orderSendEmail/{orderId}", method = RequestMethod.GET)
	@UserAccessAnnotation(UserAuthStatus.YES)
	public @ResponseBody CustomerOrder getOrderAndSendEmail(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(orderId);
		
		if(customerOrder!= null && AssertUtils.hasValue(customerOrder.getCustomer().getEmail())){
			emailSendService.sendTo(MailType.NEW_ORDER, customerOrder.getCustomer().getEmail(), customerOrder);	
		}

		return customerOrder;
	}

	@RequestMapping(value = "/orders", method = RequestMethod.DELETE)
	@UserAccessAnnotation(UserAuthStatus.ADMIN)
	public @ResponseBody Boolean deleteOrder(HttpServletRequest request, HttpServletResponse response, @RequestBody List<CustomerOrder> customerOrders) {

		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		for (Iterator<CustomerOrder> iterator = customerOrders.iterator(); iterator.hasNext();) {
			CustomerOrder customerOrder = iterator.next();
			customerOrderService.deleteOrder(customerOrder);
		}

		return true;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.DELETE)
	public @ResponseBody CustomerOrder deleteOrder(@RequestBody CustomerOrder customerOrder) {

		if (customerOrder == null || AssertUtils.isEmpty(customerOrder.getOrderId()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.deleteOrder(customerOrder);

		return customerOrder;
	}
	
	@RequestMapping(value = "/trash", method = RequestMethod.PUT)
	public @ResponseBody Boolean moveToTrash(@RequestBody List<CustomerOrder> customerOrders) {

		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.moveToTrash(customerOrders);

		return true;
	}
	
	@RequestMapping(value = "/recover", method = RequestMethod.PUT)
	public @ResponseBody Boolean recover(@RequestBody List<CustomerOrder> customerOrders) {

		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.recover(customerOrders);

		return true;
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public @ResponseBody Page<CustomerOrder> orders(
			@RequestParam(value = "validFlag", required = false, defaultValue = "1") String validFlag,
			@RequestParam(value = "allowForeignFruits", required = false, defaultValue = "") String allowForeignFruits,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		name = name.toLowerCase();
		
		OrderCondition orderCondition = new OrderCondition(orderId, name, startDate, endDate, validFlag, allowForeignFruits);
		Page<CustomerOrder> customerOrders = customerOrderService.findAllByConditions(orderCondition, page, size);

		return customerOrders;
	}

	@RequestMapping(value = "/exportOrders", method = RequestMethod.POST)
	@UserAccessAnnotation(UserAuthStatus.ADMIN)
	public @ResponseBody HttpServletResponse exportOrder(HttpServletRequest request, HttpServletResponse response,@RequestBody  List<CustomerOrder> customerOrders) {
		if(customerOrders.size()<=0){
			customerOrders = customerOrderDAO.findByValidFlag(CommConst.VALID_FLAG.VALID.value());
		}
		else{
			for(int i=0; i<customerOrders.size(); i++){
				CustomerOrder tempOrder = customerOrders.get(i);
				customerOrders.set(i, customerOrderDAO.findOne(tempOrder.getOrderId()));
			}
		}
		List<Map<String, Object>> customerExcelBeans = new LinkedList<Map<String, Object>>();
		for (Iterator<CustomerOrder> iterator = customerOrders.iterator(); iterator.hasNext();) {
			CustomerOrder customerOrder = iterator.next();
			OrderExcelBean orderExcelBean = new OrderExcelBean(customerOrder);
			customerExcelBeans.add(orderExcelBean.getOrderExcelMap());
		}
		String fileName = FPSessionUtil.getHeader(request, "fileName");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		request.setAttribute("fileName", fileName);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Accept-Language", "zh-TW");
		response.setHeader("contentType",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		try {
			OutputStream output = response.getOutputStream();
			ExcelUtil.doExcelExport(null, customerExcelBeans, output, "xls"); // new FileOutputStream(new File("D:/"+fileName))
			output.close();
			System.out.println("excel ecport success！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // exportExcelFile(customerOrders, output);

		return response;

	}

}
