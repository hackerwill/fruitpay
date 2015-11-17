package com.fruitpay.base.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.model.ReturnObject;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.AssertUtils;

@Controller
@RequestMapping("staticDataCtrl")
public class StaticDataController {
	
	@Autowired
	StaticDataService staticDataService;

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@RequestMapping(value = "/getAllCounties", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllCounties(){
		return new ReturnObject(staticDataService.getAllCounties());
	}
	
	@RequestMapping(value = "/getTowerships/{countyCode}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllTowerships(@PathVariable String countyCode){
		logger.debug(countyCode);
		if(AssertUtils.isEmpty(countyCode))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		
		List<SelectOption> towerships = staticDataService.getTowerships(countyCode);
		if(towerships == null){
			return ReturnMessageEnum.Common.UnexpectedError.getReturnMessage();
		}else{
			return  new ReturnObject(towerships);
		}
	}
		
	
	@RequestMapping(value = "/getVillages/{towershipCode}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllVillages(@PathVariable String towershipCode){
		logger.info(towershipCode);
		if(AssertUtils.isEmpty(towershipCode))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		List<SelectOption> villages = staticDataService.getVillages(towershipCode);
		if(villages == null){
			return ReturnMessageEnum.Common.UnexpectedError.getReturnMessage();
		}else{
			return  new ReturnObject(villages);
		}
	}
	
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllProducts(){
		List<Product> products = staticDataService.getAllProducts();
		return new ReturnObject(products);
	}
	
	@RequestMapping(value = "/orderPlatforms", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllOrderPlatforms(){
		List<OrderPlatform> orderPlatforms = staticDataService.getAllOrderPlatform();
		return new ReturnObject(orderPlatforms);
	}
	
	@RequestMapping(value = "/orderPlatforms/{platformId}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrderPlatform(@PathVariable Integer platformId){
		OrderPlatform orderPlatforms = staticDataService.getOrderPlatform(platformId);
		return new ReturnObject(orderPlatforms);
	}
	
	@RequestMapping(value = "/orderPrograms", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllOrderPrograms(){
		List<OrderProgram> orderPrograms = staticDataService.getAllOrderProgram();
		return new ReturnObject(orderPrograms);
	}
	
	@RequestMapping(value = "/orderPrograms/{programId}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrderProgram(@PathVariable Integer programId){
		OrderProgram orderProgram = staticDataService.getOrderProgram(programId);
		return new ReturnObject(orderProgram);
	}
	
	@RequestMapping(value = "/orderStatuses", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllOrderStatuss(){
		List<OrderStatus> orderStatuses = staticDataService.getAllOrderStatus();
		return new ReturnObject(orderStatuses);
	}
	
	@RequestMapping(value = "/orderStatuses/{orderStatusId}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrderStatus(@PathVariable Integer orderStatusId){
		OrderStatus orderStatus = staticDataService.getOrderStatus(orderStatusId);
		return new ReturnObject(orderStatus);
	}
	
	@RequestMapping(value = "/paymentModes", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllPaymentMode(){
		List<PaymentMode> paymentModes = staticDataService.getAllPaymentMode();
		return new ReturnObject(paymentModes);
	}
	
	@RequestMapping(value = "/paymentModes/{paymentModeId}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getPaymentMode(@PathVariable Integer paymentModeId){
		PaymentMode paymentMode = staticDataService.getPaymentMode(paymentModeId);
		return new ReturnObject(paymentMode);
	}
	
	@RequestMapping(value = "/shipmentPeriods", method = RequestMethod.GET)
	public @ResponseBody ReturnData getAllShipmentPeriod(){
		List<ShipmentPeriod> shipmentPeriods = staticDataService.getAllShipmentPeriod();
		return new ReturnObject(shipmentPeriods);
	}
	
	@RequestMapping(value = "/shipmentPeriods/{periodId}", method = RequestMethod.GET)
	public @ResponseBody ReturnData getShipmentPeriod(@PathVariable Integer periodId){
		ShipmentPeriod shipmentPeriod = staticDataService.getShipmentPeriod(periodId);
		return new ReturnObject(shipmentPeriod);
	}
	
	
}
