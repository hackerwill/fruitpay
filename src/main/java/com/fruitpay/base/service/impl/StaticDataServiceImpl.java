package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.stereotype.Service;


import com.fruitpay.base.dao.ConstantDAO;
import com.fruitpay.base.dao.ConstantOptionDAO;
import com.fruitpay.base.dao.OrderPlatformDAO;
import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.dao.PaymentModeDAO;
import com.fruitpay.base.dao.PostalCodeDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.dao.ShipmentDayDAO;
import com.fruitpay.base.dao.ShipmentPeriodDAO;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.DateUtil;

@Service
public class StaticDataServiceImpl implements com.fruitpay.base.service.StaticDataService {

	@Inject
	private PostalCodeDAO postalCodeDAO;
	@Inject
	private ProductDAO productDAO;
	@Inject
	private OrderPlatformDAO orderPlatformDAO;
	@Inject
	private OrderProgramDAO orderProgramDAO;
	@Inject
	private OrderStatusDAO orderStatusDAO;
	@Inject
	private PaymentModeDAO paymentModeDAO;
	@Inject
	private ShipmentPeriodDAO shipmentPeriodDAO;
	@Inject
	private ConstantDAO constantDAO;
	@Inject
	private ShipmentDayDAO shipmentDayDAO;
	@Inject
	private ConstantOptionDAO constantOptionDAO;
	
	List<Constant> consantList = null;
	List<SelectOption> countList = null;
	Map<String, List<SelectOption>> towershipMap = null;
	Map<String, List<SelectOption>> villageMap = null;
	
	private boolean isOffIslands(Integer countyCode){
		return countyCode == 9007 || countyCode == 9020 || countyCode == 10016;
	}

	
	private List<SelectOption> getSortedOptions(List<SelectOption> optionList){
		Collections.sort(optionList, (p1, p2) -> p1.getId().compareTo(p2.getId()));
		return optionList;
	}
	
	private List<SelectOption> getUnreapetedOptions(List<SelectOption> optionList){
		return optionList.parallelStream()
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> getAllProducts() {
		return productDAO.findAll();
	}

	@Override
	public List<OrderPlatform> getAllOrderPlatform() {
		return orderPlatformDAO.findAll();
	}

	@Override
	public OrderPlatform getOrderPlatform(Integer platformId) {
		return orderPlatformDAO.findOne(platformId);
	}

	@Override
	public List<OrderProgram> getAllOrderProgram() {
		return orderProgramDAO.findAll();
	}

	@Override
	public OrderProgram getOrderProgram(Integer programId) {
		return orderProgramDAO.findOne(programId);
	}

	@Override
	public List<OrderStatus> getAllOrderStatus() {
		return orderStatusDAO.findAll();
	}

	@Override
	public OrderStatus getOrderStatus(Integer orderStatusId) {
		return orderStatusDAO.findOne(orderStatusId);
	}

	@Override
	public List<PaymentMode> getAllPaymentMode() {
		return paymentModeDAO.findAll();
	}

	@Override
	public PaymentMode getPaymentMode(Integer paymentModeId) {
		return paymentModeDAO.findOne(paymentModeId);
	}

	@Override
	public List<ShipmentPeriod> getAllShipmentPeriod() {
		return shipmentPeriodDAO.findAll();
	}

	@Override
	public ShipmentPeriod getShipmentPeriod(Integer periodId) {
		return shipmentPeriodDAO.findOne(periodId);
	}

	@Override
	public List<Constant> getAllConstants() {
		return constantDAO.findAll();
	}

	@Override
	public Constant getConstant(Integer constId) {
		return constantDAO.findOne(constId);
	}

	@Override
	public List<ShipmentDay> getAllShipmentDays() {
		return shipmentDayDAO.findAll();
	}

	@Override
	public ShipmentDay getShipmentDay(Integer shipmentDaysId) {
		return shipmentDayDAO.findOne(shipmentDaysId);
	}


	@Override
	public List<PostalCode> getAllPostalCodes() {
		List<PostalCode> postalCodes = postalCodeDAO.findAll();
		return postalCodes;
	}


	@Override
	public PostalCode getPostalCode(Integer postId) {
		PostalCode postalCode = postalCodeDAO.findOne(postId);
		return postalCode;
	}
	
	@Override
	public String getNextReceiveDayStr(Date nowTime, DayOfWeek dayOfWeek){
		//規則 : 提前兩天，若出貨日是2016/01/06，只要時間早於2天前的凌晨0:00，也就是說2016/01/04 00:00，都會延到下一周
		LocalDate now = DateUtil.toLocalDate(nowTime);
		//下一個收貨日
		LocalDate receiveDayOfThisWeek = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
		//提前的天數
		LocalDate stopDayOfThisWeek = receiveDayOfThisWeek.minusDays(4);
		LocalDateTime stopDayTimeOfThisWeek = stopDayOfThisWeek.atTime(0, 0);
		
		boolean greaterThanNow = compareTimeGreaterThanNow(
				Date.from(stopDayTimeOfThisWeek.atZone(ZoneId.systemDefault()).toInstant()), 
				nowTime);
		if(!greaterThanNow)
			receiveDayOfThisWeek = receiveDayOfThisWeek.with(TemporalAdjusters.next(dayOfWeek));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
		return receiveDayOfThisWeek.format(formatter);
	}
	
	public LocalDate getNextReceiveDay(Date nowTime, DayOfWeek dayOfWeek){
		//規則 : 提前兩天，若出貨日是2016/01/06，只要時間早於2天前的凌晨0:00，也就是說2016/01/04 00:00，都會延到下一周
		LocalDate now = DateUtil.toLocalDate(nowTime);//下一個收貨日
		LocalDate receiveDayOfThisWeek = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
		//提前的天數
		LocalDate stopDayOfThisWeek = receiveDayOfThisWeek.minusDays(4);
		LocalDateTime stopDayTimeOfThisWeek = stopDayOfThisWeek.atTime(0, 0);
		
		boolean greaterThanNow = compareTimeGreaterThanNow(
				Date.from(stopDayTimeOfThisWeek.atZone(ZoneId.systemDefault()).toInstant()), 
				nowTime);
		if(!greaterThanNow)
			receiveDayOfThisWeek = receiveDayOfThisWeek.with(TemporalAdjusters.next(dayOfWeek));
		return receiveDayOfThisWeek;
	}
	
	private boolean compareTimeGreaterThanNow(Date compareDate, Date now){
		long nowTime = now.getTime();
		long compare = compareDate.getTime();
		if(compare > nowTime)
			return true;
		else 
			return false;
	}


	@Override
	public ConstantOption getConstantOptionByName(String optionName) {
		ConstantOption constantOption = constantOptionDAO.findByOptionName(optionName);
		return constantOption;
	}

}
