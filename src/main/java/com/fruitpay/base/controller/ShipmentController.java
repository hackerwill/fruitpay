package com.fruitpay.base.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

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
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.DateUtil;

@Controller
@RequestMapping("shipmentCtrl")
public class ShipmentController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private ShipmentService shipmentService;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	
	@RequestMapping(value = "/shipmentChange", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER, AllowRole.CUSTOMER })
	public @ResponseBody ShipmentChange addShipmentChange(@RequestBody ShipmentChange shipmentChange) {

		if (AssertUtils.isEmpty(shipmentChange))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		shipmentChange = shipmentService.add(shipmentChange);
		
		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(shipmentChange.getCustomerOrder().getOrderId());
		if(shipmentChange.getShipmentChangeType().getOptionName().equals(ShipmentStatus.shipmentCancel.toString())){
			customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreayCancel.getStatus()));
			customerOrderService.updateCustomerOrder(customerOrder);
		}
		
		return shipmentChange;
	}
	
	@RequestMapping(value = "/shipmentChange/invalid", method = RequestMethod.PUT)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentChange updateShipmentChange(@RequestBody ShipmentChange shipmentChange) {

		if (AssertUtils.isEmpty(shipmentChange))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		shipmentChange = shipmentService.updateValidFlag(shipmentChange, CommConst.VALID_FLAG.INVALID);
		
		if(shipmentChange.getShipmentChangeType().getOptionName().equals(ShipmentStatus.shipmentCancel.toString())){
			customerOrderService.recoverOrderStatus(shipmentChange.getCustomerOrder().getOrderId());
		}
		
		return shipmentChange;
	}
	
	@RequestMapping(value = "/shipmentChange", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Page<ShipmentChange> getAllShipmentChanges(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		Page<ShipmentChange> shipmentChanges = shipmentService.findByValidFlag(CommConst.VALID_FLAG.VALID, page, size);

		return shipmentChanges;
	}

	@RequestMapping(value = "/shipmentChange/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<ShipmentChange> getShipmentChanges(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		List<ShipmentChange> shipmentChanges = shipmentService.findChangesByOrderId(orderId);

		return shipmentChanges;
	}
	
	@RequestMapping(value = "/shipmentPeriod/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<ShipmentDeliveryStatus> getShipmentChanges(@PathVariable Integer orderId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
			
		if(AssertUtils.isEmpty(startDate)){
			startDate = DateUtil.toDate("2016-04-26", "YYYY-MM-DD");
		}
		//現在開始下三個月
		if(AssertUtils.isEmpty(endDate)){
			endDate = DateUtil.toDate(DateUtil.toLocalDate(new Date()).plusMonths(3));
		}
		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		if(endDate.before(startDate))
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(startDate, endDate, orderId);
		
		return shipmentDeliveryStatuses;
	}
	
	@RequestMapping(value = "/shipmentPreview", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Page<CustomerOrder> getShipmentPreview(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		
		LocalDate localDate = null;
		if(date == null){
			return shipmentService.findByOrderIdIn(new ArrayList<Integer>(), page, size);
		}else{
			localDate = DateUtil.toLocalDate(date);	
		}
		
		Page<CustomerOrder> customerOrders = shipmentService.listAllOrdersByDate(localDate, page, size);
		return customerOrders;
	}

}
