package com.fruitpay.base.controller;

import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.model.CheckoutPostBean;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;
import com.fruitpay.base.model.OrderCondition;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.OrderPreferenceService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.model.EnumMapOrder;
import com.fruitpay.comm.model.ExcelColumn;
import com.fruitpay.comm.model.Order;
import com.fruitpay.comm.model.OrderExcelBean;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.comm.utils.ExcelUtil;

@Controller
@RequestMapping("orderCtrl")
public class CustomerOrderController {

	private final Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private OrderPreferenceService orderPreferenceService;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private EmailSendService emailSendService;
	@Inject
	private ShipmentService shipmentService;

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody CustomerOrder addOrder(@RequestBody CheckoutPostBean checkoutPostBean) {
		CustomerOrder customerOrder = checkoutPostBean.getCustomerOrder();
		Customer customer = checkoutPostBean.getCustomer();

		customerOrder.setCustomer(customer);
		//前一天
		customerOrder.setShipmentDay(staticDataService.getShipmentDay(
				DateUtil.getPreviousDayInt(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()))));
		
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
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody CustomerOrder updateOrder(@RequestBody CustomerOrder customerOrder) {

		if (customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrder = customerOrderService.updateCustomerOrder(customerOrder);
		return customerOrder;
	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody CustomerOrder getOrder(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(orderId);

		return customerOrder;
	}
	
	@RequestMapping(value = "/orderSendEmail/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
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
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
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
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody CustomerOrder deleteOrder(@RequestBody CustomerOrder customerOrder) {

		if (customerOrder == null || AssertUtils.isEmpty(customerOrder.getOrderId()))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.deleteOrder(customerOrder);

		return customerOrder;
	}
	
	@RequestMapping(value = "/trash", method = RequestMethod.PUT)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Boolean moveToTrash(@RequestBody List<CustomerOrder> customerOrders) {

		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.moveToTrash(customerOrders);

		return true;
	}
	
	@RequestMapping(value = "/recover", method = RequestMethod.PUT)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Boolean recover(@RequestBody List<CustomerOrder> customerOrders) {

		if(customerOrders.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		customerOrderService.recover(customerOrders);

		return true;
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Page<CustomerOrder> orders(
			@RequestParam(value = "validFlag", required = false, defaultValue = "") String validFlag,
			@RequestParam(value = "allowForeignFruits", required = false, defaultValue = "") String allowForeignFruits,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate,
			@RequestParam(value = "orderStatusId", required = false, defaultValue = "") String orderStatusId,
			@RequestParam(value = "receiverCellphone", required = false, defaultValue = "") String receiverCellphone,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "shipmentChangeReason", required = false, defaultValue = "") String shipmentChangeReason,
			@RequestParam(value = "email", required = false, defaultValue = "") String email) {

		name = name.toLowerCase();
		
		OrderCondition orderCondition = new OrderCondition(email, orderId, name, startDate, endDate, validFlag, allowForeignFruits, orderStatusId, receiverCellphone, shipmentChangeReason);
		Page<CustomerOrder> customerOrders = customerOrderService.findAllByConditions(orderCondition, page, size);

		return customerOrders;
	}

	@RequestMapping(value = "/exportOrders", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody HttpServletResponse exportOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody  List<CustomerOrder> customerOrders,
			@RequestParam(value = "validFlag", required = false, defaultValue = "") String validFlag,
			@RequestParam(value = "allowForeignFruits", required = false, defaultValue = "") String allowForeignFruits,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate,
			@RequestParam(value = "receiverCellphone", required = false, defaultValue = "") String receiverCellphone,
			@RequestParam(value = "orderStatusId", required = false, defaultValue = "") String orderStatusId,
			@RequestParam(value = "shipmentChangeReason", required = false, defaultValue = "") String shipmentChangeReason,
			@RequestParam(value = "email", required = false, defaultValue = "") String email) {
		
		if(customerOrders.size() == 0) {
			OrderCondition orderCondition = new OrderCondition(email, orderId, name, startDate, endDate, validFlag, allowForeignFruits, orderStatusId, receiverCellphone, shipmentChangeReason);
			customerOrders = customerOrderService.findAllByConditions(orderCondition);
			customerOrders = shipmentService.countShipmentTimes(customerOrders);
		} else {
			customerOrders = customerOrders.stream().map(order -> {
				return customerOrderService.findOneIncludingOrderPreference(order.getOrderId());
			}).collect(Collectors.toList());
		}
		
		List<Map<String, Object>> map = customerOrders.stream().map(customerOrder -> {
			try {
				return new OrderExcelBean(customerOrder).getMap();
			} catch (Exception e) {
				throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
			}
		}).collect(Collectors.toList());
		
		try {
			ExcelUtil.doExcelExport(request, response, "xls", map, new OrderExcelBean().getColList());
		} catch (Exception e) {
			throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
		}

		return response;

	}
	
	@RequestMapping(value = "/orderPreferences/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<OrderPreference> getOrderPreferences(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		List<OrderPreference> OrderPreferences = orderPreferenceService.findByCustomerOrder(orderId);

		return OrderPreferences;
	}
	
	@RequestMapping(value = "/orderPreferences/{orderId}", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<OrderPreference> updateOrderPreferences(@PathVariable int orderId, @RequestBody List<OrderPreference> orderPreferences) {

		if (orderPreferences == null || orderPreferences.isEmpty())
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		List<OrderPreference> OrderPreferences = orderPreferenceService.updateOrderPreferences(orderId, orderPreferences);

		return OrderPreferences;
	}
	
	@RequestMapping(value = "/orderComment", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody OrderComment addOrderComment(@RequestBody OrderComment orderComment) {

		if (AssertUtils.isEmpty(orderComment))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		orderComment = customerOrderService.add(orderComment);	
		return orderComment;
	}
	
	@RequestMapping(value = "/orderComment", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<OrderComment> getComment(
			@RequestParam(value = "orderId", required = true, defaultValue = "") int orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		List<OrderComment> orderComments = customerOrderService.findCommentsByOrderId(orderId);
		return orderComments;
	}
	
	@RequestMapping(value = "/orderComment/{commentId}", method = RequestMethod.DELETE)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody OrderComment removeComment(@PathVariable int commentId) {

		if (AssertUtils.isEmpty(commentId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		OrderComment orderComment = customerOrderService.invalidate(commentId);
		return orderComment;
	}

}
