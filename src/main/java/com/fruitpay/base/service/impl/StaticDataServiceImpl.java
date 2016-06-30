package com.fruitpay.base.service.impl;

import java.io.Serializable;
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
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.ConstantDAO;
import com.fruitpay.base.dao.ConstantOptionDAO;
import com.fruitpay.base.dao.OrderPlatformDAO;
import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.dao.PaymentModeDAO;
import com.fruitpay.base.dao.PostalCodeDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.dao.ProductItemDAO;
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
import com.fruitpay.base.model.ProductItem;
import com.fruitpay.base.model.ShipmentDay;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.comm.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class StaticDataServiceImpl implements StaticDataService {
	private final Logger logger = Logger.getLogger(this.getClass());
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
	@Inject
	private ProductItemDAO productItemDAO;
	
	List<Constant> consantList = null;
	List<SelectOption> countList = null;
	Map<String, List<SelectOption>> towershipMap = null;
	Map<String, List<SelectOption>> villageMap = null;
	private Integer BUFFER_DAY = null;
	List<DateMapping> dataMappings = null;
	
	@PostConstruct
	public void init() {
		ConstantOption bufferDayOption = this.getConstantOptionByName("bufferDay");
		BUFFER_DAY = Integer.valueOf(bufferDayOption.getOptionDesc());
		String dateMappingJson = this.getConstantOptionByName("dateMappingJson").getOptionDesc();
		Gson gson = new Gson();
		dataMappings =  gson.fromJson(dateMappingJson, new TypeToken<List<DateMapping>>(){}.getType());
	}
	
	private class DateMapping implements Serializable {
		private Integer dayOfWeek;
		private Integer mappingDayOfWeek;
		public Integer getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(Integer dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		public Integer getMappingDayOfWeek() {
			return mappingDayOfWeek;
		}
		public void setMappingDayOfWeek(Integer mappingDayOfWeek) {
			this.mappingDayOfWeek = mappingDayOfWeek;
		}
	}
	
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
	public int getMappingDayOfWeek(int dayOfWeek) {
		Optional<DateMapping> mappingOptional = this.dataMappings.stream().filter(dateMapping -> {
			return dateMapping.getDayOfWeek().equals(dayOfWeek);
		}).findFirst();
		
		if(mappingOptional.isPresent()) {
			return mappingOptional.get().getMappingDayOfWeek();
		} else {
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		}
		
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
	public Page<Constant> getAllConstants(int page, int size) {
		return constantDAO.findAll(new PageRequest(page, size));
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
		//規則 : 提前n天，若出貨日是2016/01/06，只要時間早於n天前的凌晨0:00，都會延到下一周
		LocalDate now = DateUtil.toLocalDate(nowTime);
		//下一個收貨日
		LocalDate receiveDayOfThisWeek = this.getNextReceiveDay(nowTime, dayOfWeek);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
		return receiveDayOfThisWeek.format(formatter);
	}
	
	public LocalDate getNextReceiveDay(Date nowTime, DayOfWeek dayOfWeek){
		//規則 : 提前n天，若出貨日是2016/01/06，只要時間早於4天前的凌晨0:00，都會延到下一周
		LocalDate now = DateUtil.toLocalDate(nowTime);
		//下一個收貨日
		LocalDate receiveDayOfThisWeek = now.with(TemporalAdjusters.nextOrSame(dayOfWeek));
		
		//提前的天數
		LocalDate stopDayOfThisWeek = receiveDayOfThisWeek.minusDays(BUFFER_DAY);
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


	@Override
	@Transactional
	public Constant addConstant(Constant constant) {
		constant = constantDAO.save(constant);
		return constant;
	}


	@Override
	@Transactional
	public Constant updateConstant(Constant constant) {
		Constant origin = constantDAO.findOne(constant.getConstId());
		
		BeanUtils.copyProperties(constant, origin);
		origin = constantDAO.save(origin);
		return origin;
	}


	@Override
	@Transactional
	public ConstantOption addConstantOption(ConstantOption constantOption) {
		constantOption = constantOptionDAO.save(constantOption);
		return constantOption;
	}


	@Override
	@Transactional
	public ConstantOption updateConstantOption(ConstantOption constantOption) {
		ConstantOption origin = constantOptionDAO.findOne(constantOption.getOptionId());
		
		BeanUtils.copyProperties(constantOption, origin);
		origin = constantOptionDAO.save(origin);
		return origin;
	}


	@Override
	public List<Constant> getAllConstants() {
		return constantDAO.findAll();
	}


	@Override
	public Page<ConstantOption> getConstantOptions(Integer constId, int page, int size) {
		Constant constant = new Constant();
		constant.setConstId(constId);
		return constantOptionDAO.findByConstant(constant, new PageRequest(page, size, new Sort(Sort.Direction.ASC, "orderNo")));
	}


	@Override
	public List<ProductItem> getAllProductItems() {
		List<ProductItem> productItems = productItemDAO.findAll();
		return productItems;
	}


	@Override
	public int getNextReceiveDayOfWeek(Date nowTime) {
		// TODO Auto-generated method stub
		return 0;
	}

}
