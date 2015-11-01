package com.fruitpay.base.controller;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.Village;
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
	
	
	
	
}
