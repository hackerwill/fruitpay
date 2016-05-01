package com.fruitpay.base.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.annotation.UserAccessValidate;

@Controller
@RequestMapping("staticDataCtrl")
public class StaticDataController {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	StaticDataService staticDataService;
	
	@RequestMapping(value = "/getAllPostalCodes", method = RequestMethod.GET)
	public @ResponseBody List<PostalCode> getAllPostalCodes(){
		List<PostalCode> postCodes = staticDataService.getAllPostalCodes();
		postCodes = postCodes.stream()
				.filter(p -> "Y".equals(p.getAllowShipment()))
				.collect(Collectors.toList());;
		return postCodes;
	}
	
	@RequestMapping(value = "/getAllProducts", method = RequestMethod.GET)
	public @ResponseBody List<Product> getAllProducts(){
		List<Product> products = staticDataService.getAllProducts();
		return products;
	}
	
	@RequestMapping(value = "/orderPlatforms", method = RequestMethod.GET)
	public @ResponseBody List<OrderPlatform> getAllOrderPlatforms(){
		List<OrderPlatform> orderPlatforms = staticDataService.getAllOrderPlatform();
		return orderPlatforms;
	}
	
	@RequestMapping(value = "/orderPlatforms/{platformId}", method = RequestMethod.GET)
	public @ResponseBody OrderPlatform getOrderPlatform(@PathVariable Integer platformId){
		OrderPlatform orderPlatform = staticDataService.getOrderPlatform(platformId);
		return orderPlatform;
	}
	
	@RequestMapping(value = "/orderPrograms", method = RequestMethod.GET)
	public @ResponseBody List<OrderProgram> getAllOrderPrograms(){
		List<OrderProgram> orderPrograms = staticDataService.getAllOrderProgram();
		return orderPrograms;
	}
	
	@RequestMapping(value = "/orderPrograms/{programId}", method = RequestMethod.GET)
	public @ResponseBody OrderProgram getOrderProgram(@PathVariable Integer programId){
		OrderProgram orderProgram = staticDataService.getOrderProgram(programId);
		return orderProgram;
	}
	
	@RequestMapping(value = "/orderStatuses", method = RequestMethod.GET)
	public @ResponseBody List<OrderStatus> getAllOrderStatuss(){
		List<OrderStatus> orderStatuses = staticDataService.getAllOrderStatus();
		return orderStatuses;
	}
	
	@RequestMapping(value = "/orderStatuses/{orderStatusId}", method = RequestMethod.GET)
	public @ResponseBody OrderStatus getOrderStatus(@PathVariable Integer orderStatusId){
		OrderStatus orderStatus = staticDataService.getOrderStatus(orderStatusId);
		return orderStatus;
	}
	
	@RequestMapping(value = "/paymentModes", method = RequestMethod.GET)
	public @ResponseBody List<PaymentMode> getAllPaymentMode(){
		List<PaymentMode> paymentModes = staticDataService.getAllPaymentMode();
		return paymentModes;
	}
	
	@RequestMapping(value = "/paymentModes/{paymentModeId}", method = RequestMethod.GET)
	public @ResponseBody PaymentMode getPaymentMode(@PathVariable Integer paymentModeId){
		PaymentMode paymentMode = staticDataService.getPaymentMode(paymentModeId);
		return paymentMode;
	}
	
	@RequestMapping(value = "/shipmentPeriods", method = RequestMethod.GET)
	public @ResponseBody List<ShipmentPeriod> getAllShipmentPeriod(){
		List<ShipmentPeriod> shipmentPeriods = staticDataService.getAllShipmentPeriod();
		return shipmentPeriods;
	}
	
	@RequestMapping(value = "/shipmentPeriods/{periodId}", method = RequestMethod.GET)
	public @ResponseBody ShipmentPeriod getShipmentPeriod(@PathVariable Integer periodId){
		ShipmentPeriod shipmentPeriod = staticDataService.getShipmentPeriod(periodId);
		return shipmentPeriod;
	}
	
	@RequestMapping(value = "/constants", method = RequestMethod.GET)
	public @ResponseBody List<Constant> getAllConstants(){
		List<Constant> constants = staticDataService.getAllConstants();
		//過濾掉僅顯示有效的常數
		for (Constant constant : constants) {
			List<ConstantOption> options = constant.getConstOptions().stream()
					.filter(option -> "1".equals(option.getValidFlag()))
					.collect(Collectors.toList());
		}
		return constants;
	}
	
	@RequestMapping(value = "/constants/{constId}", method = RequestMethod.GET)
	public @ResponseBody Constant getAllConstants(@PathVariable Integer constId){
		Constant constant = staticDataService.getConstant(constId);
		List<ConstantOption> options = constant.getConstOptions().stream()
			.filter(option -> "1".equals(option.getValidFlag()))
			.collect(Collectors.toList());
		//排序
		options.sort(new Comparator<ConstantOption>() {
					@Override
					public int compare(ConstantOption o1, ConstantOption o2) {
						return Integer.compare(o1.getOrderNo(), o2.getOrderNo());
					}
				});
		
		constant.setConstOptions(options);
		return constant;
	}
	
	@RequestMapping(value = "/adminConstant/{constId}", method = RequestMethod.GET)
	@UserAccessValidate(AllowRole.SYSTEM_MANAGER)
	public @ResponseBody Constant getAllAdminConstant(@PathVariable Integer constId){
		Constant constant = staticDataService.getConstant(constId);
		List<ConstantOption> options = constant.getConstOptions();
		//排序
		options.sort(new Comparator<ConstantOption>() {
					@Override
					public int compare(ConstantOption o1, ConstantOption o2) {
						return Integer.compare(o1.getOrderNo(), o2.getOrderNo());
					}
				});
		return constant;
	}
	
	@RequestMapping(value = "/exceptionHandleTest", method = RequestMethod.GET)
	public @ResponseBody Object exceptionHandleTest(){
		boolean flag = true;
		if(flag){
			throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
		}
		return null;
	}
	
}
