package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.ShipmentChangeDAO;
import com.fruitpay.base.dao.ShipmentRecordDAO;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.utils.DateUtil;

@Service
public class ShipmentServiceImpl implements ShipmentService {
	
	private final Logger logger = Logger.getLogger(this.getClass());

	@Inject
	private ShipmentChangeDAO shipmentChangeDAO;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	@Inject
	private ShipmentRecordDAO shipmentRecordDAO;
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	
	//if one delivery day is pulse, the next delivery day plus day amount
	private final int JUMP_DAY = 7;
	
	private ConstantOption shipmentPulse = null;
	private ConstantOption shipmentDeliver = null;
	private ConstantOption shipmentCancel = null;
	private ConstantOption shipmentDelivered = null;
	private ConstantOption shipmentReady = null;
	
	@PostConstruct
	public void init(){
		shipmentDelivered = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDelivered.toString());
		shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString()); 
		shipmentCancel = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentCancel.toString()); 
		shipmentReady = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentReady.toString()); 
	}
	
	@Override
	public List<ShipmentChange> findChangesByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<ShipmentChange> ShipmentChanges = shipmentChangeDAO.findByCustomerOrderAndValidFlag(
				customerOrder, CommConst.VALID_FLAG.VALID.value());
		return ShipmentChanges;
	}

	@Override
	@Transactional
	public ShipmentChange add(ShipmentChange shipmentChange) {
		shipmentChange.setValidFlag(CommConst.VALID_FLAG.VALID.value());
		shipmentChange = shipmentChangeDAO.save(shipmentChange);
		return shipmentChange;
	}

	@Override
	@Transactional
	public ShipmentChange update(ShipmentChange shipmentChange) {
		
		ShipmentChange origin = shipmentChangeDAO.findOne(shipmentChange.getId());
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		
		shipmentChange.setUpdateDate(new Date());
		BeanUtils.copyProperties(shipmentChange, origin);
		
		return origin;
	}
	
	@Override
	@Transactional
	public ShipmentChange updateValidFlag(ShipmentChange shipmentChange, CommConst.VALID_FLAG validFlag) {
		
		if(validFlag == null)
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		shipmentChange.setValidFlag(validFlag.value());
		
		return update(shipmentChange);
	}

	@Override
	@Transactional
	public Boolean delete(ShipmentChange shipmentChange) {
		shipmentChangeDAO.delete(shipmentChange.getId());
		return true;
	}

	@Override
	public List<ShipmentDeliveryStatus> getAllDeliveryStatus(Date startDate, Date endDate, int orderId){
		CustomerOrder customerOrder = customerOrderService.getCustomerOrdersByValidFlag(orderId, VALID_FLAG.VALID.value());
		if(customerOrder == null)
			throw new HttpServiceException(ReturnMessageEnum.Order.OrderNotFound.getReturnMessage());
		DayOfWeek dayOfWeek = DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()));
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
		int duration = customerOrder.getShipmentPeriod().getDuration();
		
		//unnecessary to count
		if(endDate.before(DateUtil.toDate(firstDeliveryDate)))
			return new ArrayList<ShipmentDeliveryStatus>();
		
		if(startDate.before(DateUtil.toDate(firstDeliveryDate)))
			startDate = DateUtil.toDate(firstDeliveryDate);
		
		LocalDate date = DateUtil.toLocalDate(startDate);
		List<ShipmentChange> shipmentChanges = this.findChangesByOrderId(orderId);
		List<ShipmentRecord> shipmentRecords = this.findRecordsByOrderId(orderId);
		List<ShipmentDeliveryStatus> deliveryStatuses = new ArrayList<ShipmentDeliveryStatus>();
		
		while(!date.isAfter(DateUtil.toLocalDate(endDate))){
			ConstantOption shipmentChangeType = getDateStatus(date, firstDeliveryDate, shipmentChanges, shipmentRecords, dayOfWeek, duration);
			if(shipmentChangeType != null){
				ShipmentDeliveryStatus deliveryStatus = new ShipmentDeliveryStatus();
				deliveryStatus.setApplyDate(DateUtil.toDate(date));
				deliveryStatus.setShipmentChangeType(shipmentChangeType);
				deliveryStatuses.add(deliveryStatus);
			}
			
			date = date.plusDays(1);
		}
		return deliveryStatuses;
	}
	
	private ConstantOption getDateStatus(LocalDate searchDate, LocalDate incrementDate,
			List<ShipmentChange> shipmentChanges, List<ShipmentRecord> shipmentRecords,  
			DayOfWeek dayOfWeek, int duration){
		
		if(isShipped(searchDate, shipmentChanges, shipmentRecords)){
			return shipmentDelivered;
		}else if(isCancel(searchDate, shipmentChanges)) {
			return shipmentCancel;
		}else if(isPulse(searchDate, shipmentChanges)) {
			return shipmentPulse;
		}else if(isNeedShipment(searchDate, shipmentChanges)){
			return shipmentDeliver;
		}
		
		if(searchDate.isBefore(LocalDate.now()) || searchDate.isBefore(incrementDate))
			return null;
		
		if(!searchDate.getDayOfWeek().equals(dayOfWeek))
			return null;
		
		//若已經取消, 不需要再配送
		if(isAfterCancel(incrementDate, shipmentChanges))
			return null;
		
		if(searchDate.equals(incrementDate)) {
			// 檢查是否有同個週期內其他的修改狀態, 若有的話, 原本的日期要做的事情就不用做 
			if(isOtherChangeInSamePeriod(searchDate, shipmentChanges, duration)) {
				return null;
			}
			
			LocalDate nextShipmentDay = staticDataService.getNextReceiveDay(new Date(), dayOfWeek);
			if(searchDate.isBefore(nextShipmentDay))
				return shipmentReady;
			return shipmentDeliver;
		//固定加上一個禮拜的時間
		}else if(isPulse(incrementDate, shipmentChanges)) {
			return getDateStatus(searchDate, incrementDate.plusDays(JUMP_DAY), shipmentChanges, shipmentRecords, dayOfWeek, duration);
		}else {
			return getDateStatus(searchDate, incrementDate.plusDays(duration), shipmentChanges, shipmentRecords, dayOfWeek, duration);
		}
		
	}
	
	private boolean isOtherChangeInSamePeriod(LocalDate date, List<ShipmentChange> shipmentChanges, int duration) {
		LocalDate startDate = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		LocalDate endDate = startDate.plusDays(duration - 1);
		
		boolean existed = shipmentChanges.stream().anyMatch(shipmentChange -> {
			LocalDate thisDate = DateUtil.toLocalDate(shipmentChange.getApplyDate());
			if((startDate.isBefore(thisDate) && endDate.isAfter(thisDate)
					|| startDate.equals(thisDate) || endDate.equals(thisDate))) {
				return true;
			}
			return false;
		});
		
		return existed;
	}
	
	private boolean isShipped(LocalDate date, List<ShipmentChange> shipmentChanges, List<ShipmentRecord> shipmentRecords) {
		
		boolean matchChange = shipmentChanges.stream().anyMatch(shipmentChange -> {
			return ShipmentStatus.shipmentDelivered.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())
					&& date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate()));
		});
		
		boolean recordMatch = false;
		for (Iterator<ShipmentRecord> iterator = shipmentRecords.iterator(); iterator.hasNext();) {
			ShipmentRecord shipmentRecord = iterator.next();
			if(date.equals(DateUtil.toLocalDate(shipmentRecord.getDate()))
					&& ShipmentStatus.shipmentDelivered.toString().equals(shipmentRecord.getShipmentType().getOptionName())){
				recordMatch = true;
			}
		}
		
		return matchChange || recordMatch;
	}
	
	private boolean isNeedShipment(LocalDate date, List<ShipmentChange> shipmentChanges){
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			
			if(date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate()))
					&& ShipmentStatus.shipmentDeliver.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isAfterCancel(LocalDate date, List<ShipmentChange> shipmentChanges){
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			
			if(date.isAfter(DateUtil.toLocalDate(shipmentChange.getApplyDate()))
					&& ShipmentStatus.shipmentCancel.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isCancel(LocalDate date, List<ShipmentChange> shipmentChanges){
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			
			if(date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate()))
					&& ShipmentStatus.shipmentCancel.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isPulse(LocalDate date, List<ShipmentChange> shipmentChanges){
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			if(date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate()))
					&& ShipmentStatus.shipmentPulse.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size) {
		Page<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByValidFlag(
				validFlag.value(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "applyDate")));
		return shipmentChanges;
	}

	@Override
	public List<ShipmentRecord> findRecordsByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<ShipmentRecord> shipmentRecords = shipmentRecordDAO.findByCustomerOrderAndValidFlag(customerOrder, VALID_FLAG.VALID.value());
		return shipmentRecords;
	}

	@Override
	public Page<CustomerOrder> listAllOrdersByDate(LocalDate date, int page, int size) {
		Constant deliveryDayConstant = staticDataService.getConstant(6);
		List<ConstantOption> deliveryDays = deliveryDayConstant.getConstOptions();
		List<OrderStatus> orderStatues = staticDataService.getAllOrderStatus().stream()
					.filter(orderStatus -> {
						return orderStatus.getOrderStatusId() == com.fruitpay.base.comm.OrderStatus.AlreadyCheckout.getStatus()
								|| orderStatus.getOrderStatusId() == com.fruitpay.base.comm.OrderStatus.CreditPaySuccessful.getStatus();
					}).collect(Collectors.toList());
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		deliveryDays = deliveryDays.stream()
			.filter(deliveryDay -> dayOfWeek.getValue() == Integer.parseInt(deliveryDay.getOptionName()))
			.collect(Collectors.toList());
		
		if(deliveryDays.size() == 0)
			return customerOrderDAO.findByOrderIdIn(new ArrayList<Integer>(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		
		List<CustomerOrder> customerOrders = customerOrderDAO.findByValidFlagAndDeliveryDayAndOrderStatusIn(
				VALID_FLAG.VALID.value(), deliveryDays.get(0), orderStatues);
		
		List<Integer> orderIds = customerOrders.stream().filter(customerOrder -> {

			LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
			int duration = customerOrder.getShipmentPeriod().getDuration();
			int orderId = customerOrder.getOrderId();
			
			List<ShipmentChange> shipmentChanges = this.findChangesByOrderId(orderId);
			List<ShipmentRecord> shipmentRecords = this.findRecordsByOrderId(orderId);
			
			ConstantOption status = this.getDateStatus(date, firstDeliveryDate, shipmentChanges, shipmentRecords, 
					dayOfWeek, duration);
			
			if(status != null && (status.getOptionName().equals(shipmentDeliver.getOptionName()) 
					|| status.getOptionName().equals(shipmentReady.getOptionName()))) {
				return true;
			} else {
				return false;
			}
		}).map(customerOrder -> {
			return customerOrder.getOrderId();
		}).collect(Collectors.toList());
		
		Page<CustomerOrder> customerOrderPages = customerOrderDAO.findByOrderIdIn(orderIds, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		
		return customerOrderPages;
	}

	@Override
	public Page<CustomerOrder> findByOrderIdIn(List<Integer> orderIds, int page, int size) {
		Page<CustomerOrder> customerOrderPages = customerOrderDAO.findByOrderIdIn(orderIds, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		return customerOrderPages;
	}

}
