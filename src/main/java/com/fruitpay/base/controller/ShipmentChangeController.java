package com.fruitpay.base.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.comm.utils.AssertUtils;

@Controller
@RequestMapping("shipmentCtrl")
public class ShipmentChangeController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private ShipmentService shipmentService;
	
	@RequestMapping(value = "/shipmentChange", method = RequestMethod.POST)
	public @ResponseBody ShipmentChange addShipmentChanges(@RequestBody ShipmentChange shipmentChange) {

		if (AssertUtils.isEmpty(shipmentChange))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		shipmentChange = shipmentService.add(shipmentChange);
		
		return shipmentChange;
	}

	@RequestMapping(value = "/shipmentChange/{orderId}", method = RequestMethod.GET)
	public @ResponseBody List<ShipmentChange> getShipmentChanges(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		List<ShipmentChange> shipmentChanges = shipmentService.findByOrderId(orderId);

		return shipmentChanges;
	}
	
	@RequestMapping(value = "/shipmentPeriod/{orderId}", method = RequestMethod.GET)
	public @ResponseBody List<ShipmentDeliveryStatus> getShipmentChanges(@PathVariable Integer orderId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
			
		if (AssertUtils.isEmpty(orderId) || AssertUtils.isEmpty(startDate) || AssertUtils.isEmpty(endDate))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		if(endDate.before(startDate))
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(startDate, endDate, orderId);
		
		return shipmentDeliveryStatuses;
	}

}
