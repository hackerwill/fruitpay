package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
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

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.comm.ShipmentChangeStatus;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.dao.ShipmentChangeDAO;
import com.fruitpay.base.dao.ShipmentRecordDAO;
import com.fruitpay.base.dao.ShipmentRecordDetailDAO;
import com.fruitpay.base.model.CachedBean;
import com.fruitpay.base.model.ChosenProductItemBean;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.ProductItem;
import com.fruitpay.base.model.ProductStatusBean;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentChangeCondition;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentInfoBean;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.model.StatusInteger;
import com.fruitpay.base.service.CachedService;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.ReturnMessage;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.comm.utils.ListTranspose;

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
	private ShipmentRecordDetailDAO shipmentRecordDetailDAO;
	@Inject
	private ShipmentRecordDAO shipmentRecordDAO;
	@Inject
	private CustomerOrderDAO customerOrderDAO;
	@Inject
	private OrderPreferenceDAO orderPreferenceDAO;
	@Inject
	private CachedService cachedService;
	
	//if one delivery day is pulse, the next delivery day plus day amount
	private final int JUMP_DAY = 7;
	
	private ConstantOption shipmentPulse = null;
	private ConstantOption shipmentDeliver = null;
	private ConstantOption shipmentCancel = null;
	private ConstantOption shipmentDelivered = null;
	private ConstantOption shipmentReady = null;
	private ConstantOption shipmentReturn = null;
	private ConstantOption shipmentChangeStatusUnhandled = null;
	private List<ProductItem> cacheProductItems = null;
	
	@PostConstruct
	public void init(){
		shipmentDelivered = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDelivered.toString());
		shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString()); 
		shipmentCancel = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentCancel.toString()); 
		shipmentReady = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentReady.toString()); 
		shipmentReturn = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentReturn.toString()); 
		shipmentChangeStatusUnhandled = staticDataService.getConstantOptionByName(ShipmentChangeStatus.shipmentChangeStatusUnhandled.toString());
		cacheProductItems = staticDataService.getAllProductItems();
	}
	
	@Override
	public List<ShipmentChange> findShipmentChangesByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<ShipmentChange> ShipmentChanges = shipmentChangeDAO.findByCustomerOrderAndValidFlag(
				customerOrder, CommConst.VALID_FLAG.VALID.value());
		return ShipmentChanges;
	}

	@Override
	@Transactional
	public ShipmentChange add(ShipmentChange shipmentChange) {
		shipmentChange.setStatus(shipmentChangeStatusUnhandled);
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
		int duration = customerOrder.getShipmentPeriod().getDuration();
		DayOfWeek dayOfWeek = DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()));
		List<ShipmentChange> shipmentChanges = this.findShipmentChangesByOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = this.findShipmentRecordDetailsByOrderId(orderId);
		List<ShipmentDeliveryStatus> deliveryStatuses = new ArrayList<ShipmentDeliveryStatus>();
		
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
		//檢查是否有時間在第一次之前 若有的話 以最早的為主
		startDate = checkAndModifiedStartDate(firstDeliveryDate, shipmentChanges, shipmentRecordDetails);
		
		//unnecessary to count
		if(endDate.before(DateUtil.toDate(firstDeliveryDate)))
			return new ArrayList<ShipmentDeliveryStatus>();
		LocalDate date = DateUtil.toLocalDate(startDate);
		
		while(!date.isAfter(DateUtil.toLocalDate(endDate))){
			ConstantOption shipmentChangeType = this.getDateStatus(date, firstDeliveryDate, shipmentChanges, shipmentRecordDetails, dayOfWeek, duration);
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
	
	private Date checkAndModifiedStartDate(LocalDate firstDeliveryDate, List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecordDetails) {
		List<LocalDate> sortDateList = new ArrayList<>();
		sortDateList.addAll(shipmentChanges.stream()
			.map(shipmentChange -> {
			return DateUtil.toLocalDate(shipmentChange.getApplyDate());
		}).collect(Collectors.toList()));
		sortDateList.addAll(shipmentRecordDetails.stream().map(shipmentRecordDetail -> {
			return DateUtil.toLocalDate(shipmentRecordDetail.getShipmentRecord().getDate());
		}).collect(Collectors.toList()));
		if(!sortDateList.isEmpty()) {
			Collections.sort(sortDateList);
			LocalDate minDate = sortDateList.get(0);
			firstDeliveryDate = firstDeliveryDate.compareTo(minDate) > 0 ? minDate : firstDeliveryDate;
		}
		
		return DateUtil.toDate(firstDeliveryDate);
	}
	
	private ConstantOption getDateStatus(LocalDate searchDate, LocalDate incrementDate,
			List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecordDetails,  
			DayOfWeek dayOfWeek, int duration){
		
		if(isReturn(searchDate, shipmentChanges)) {
			return shipmentReturn;
		} else if(isShipped(searchDate, shipmentChanges, shipmentRecordDetails)){
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
			return getDateStatus(searchDate, incrementDate.plusDays(JUMP_DAY), shipmentChanges, shipmentRecordDetails, dayOfWeek, duration);
		}else {
			return getDateStatus(searchDate, incrementDate.plusDays(duration), shipmentChanges, shipmentRecordDetails, dayOfWeek, duration);
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
	
	private boolean isShipped(LocalDate date, List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecords) {
		
		boolean matchChange = shipmentChanges.stream().anyMatch(shipmentChange -> {
			return ShipmentStatus.shipmentDelivered.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())
					&& date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate()));
		});
		
		boolean recordMatch = false;
		for (Iterator<ShipmentRecordDetail> iterator = shipmentRecords.iterator(); iterator.hasNext();) {
			ShipmentRecordDetail shipmentRecordDetail = iterator.next();
			if(date.equals(DateUtil.toLocalDate(shipmentRecordDetail.getShipmentRecord().getDate()))
					&& ShipmentStatus.shipmentDelivered.toString().equals(shipmentRecordDetail.getShipmentRecord().getShipmentType().getOptionName())){
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
	
	private boolean isReturn(LocalDate date, List<ShipmentChange> shipmentChanges){
		return shipmentChanges.stream().anyMatch(shipmentChange -> {
			return date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate())) 
					&& ShipmentStatus.shipmentReturn.toString().equals(shipmentChange.getShipmentChangeType().getOptionName());
		});
	}
	
	private boolean isCancel(LocalDate date, List<ShipmentChange> shipmentChanges){
		return shipmentChanges.stream().anyMatch(shipmentChange -> {
			return date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate())) 
					&& ShipmentStatus.shipmentCancel.toString().equals(shipmentChange.getShipmentChangeType().getOptionName());
		});
	}
	
	private boolean isPulse(LocalDate date, List<ShipmentChange> shipmentChanges){
		return shipmentChanges.stream().anyMatch(shipmentChange -> {
			return date.equals(DateUtil.toLocalDate(shipmentChange.getApplyDate())) 
					&& ShipmentStatus.shipmentPulse.toString().equals(shipmentChange.getShipmentChangeType().getOptionName());
		});
	}
	
	@Override
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size) {
		Page<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByValidFlag(
				validFlag.value(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "applyDate")));
		return shipmentChanges;
	}

	@Override
	public List<ShipmentRecordDetail> findShipmentRecordDetailsByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderAndValidFlag(customerOrder, VALID_FLAG.VALID.value());
		return shipmentRecordDetails;
	}
	
	@Override
	public List<CustomerOrder> listAllCustomerOrdersByDate(LocalDate date) {
		if(date == null) 
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		List<CustomerOrder> customerOrders = null;
		
		Constant deliveryDayConstant = staticDataService.getConstant(6);
		List<ConstantOption> deliveryDays = deliveryDayConstant.getConstOptions();
		List<OrderStatus> orderStatues = staticDataService.getAllOrderStatus().stream()
					.filter(orderStatus -> {
						return orderStatus.getOrderStatusId() == com.fruitpay.base.comm.OrderStatus.AlreadyCheckout.getStatus()
								|| orderStatus.getOrderStatusId() == com.fruitpay.base.comm.OrderStatus.CreditPaySuccessful.getStatus()
								//即使已經取消了, 只要取消的日期沒到, 還是要繼續出
								|| orderStatus.getOrderStatusId() == com.fruitpay.base.comm.OrderStatus.AlreayCancel.getStatus();
					}).collect(Collectors.toList());
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		deliveryDays = deliveryDays.stream()
			.filter(deliveryDay -> dayOfWeek.getValue() == Integer.parseInt(deliveryDay.getOptionName()))
			.collect(Collectors.toList());
		
		if(deliveryDays.size() == 0)
			return new ArrayList<>();
		
		customerOrders = customerOrderDAO.findByValidFlagAndDeliveryDayAndOrderStatusIn(
				VALID_FLAG.VALID.value(), deliveryDays.get(0), orderStatues);
		List<ShipmentChange> allShipmentChanges = shipmentChangeDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		List<ShipmentRecordDetail> allShipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		
		customerOrders = customerOrders.stream().filter(customerOrder -> {
			int duration = customerOrder.getShipmentPeriod().getDuration();
			List<ShipmentChange> shipmentChanges = allShipmentChanges.stream().filter(shipmentChange-> {
				return shipmentChange.getCustomerOrder().getOrderId().equals(customerOrder.getOrderId());
			}).collect(Collectors.toList());
			List<ShipmentRecordDetail> shipmentRecordDetails = allShipmentRecordDetails.stream().filter(shipmentRecordDetail-> {
				return shipmentRecordDetail.getCustomerOrder().getOrderId().equals(customerOrder.getOrderId());
			}).collect(Collectors.toList());
			LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
			
			ConstantOption status = this.getDateStatus(date, firstDeliveryDate, shipmentChanges, shipmentRecordDetails, 
					dayOfWeek, duration);
			
			if(status != null && (status.getOptionName().equals(shipmentDeliver.getOptionName()) 
					|| status.getOptionName().equals(shipmentReady.getOptionName()) 
					|| status.getOptionName().equals(shipmentDelivered.getOptionName()))) {	//已出貨也要列入計算 因為有可能重新出
				return true;
			} else {
				return false;
			}
		}).map(customerOrder -> {
			return customerOrder;
		}).collect(Collectors.toList());
		
		//如果有客製需配送的日期 在這裡加入
		List<CustomerOrder> customizedOrderIds = shipmentChangeDAO.findByApplyDateAndShipmentChangeTypeAndValidFlag(
				DateUtil.toDate(date), shipmentDeliver, VALID_FLAG.VALID.value()).stream()
				.map(shipmentChangeType -> {
					return shipmentChangeType.getCustomerOrder();
				}).collect(Collectors.toList());
		
		if(!customizedOrderIds.isEmpty()) {
			customerOrders.addAll(customizedOrderIds);
		}
		
		return customerOrders;
	}
	
	@Override
	public LocalDate getNextNeedShipmentDate(CustomerOrder customerOrder, List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecordDetails) {
		int duration = customerOrder.getShipmentPeriod().getDuration();
		DayOfWeek dayOfWeek = DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName()));
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
		//檢查是否有時間在第一次之前 若有的話 以最早的為主
	    
		//因為下方會加一天 所以從昨天開始查
		LocalDate maxDate = LocalDate.now().plusDays(90);
		LocalDate searchDate = LocalDate.now().minusDays(1); 
		ConstantOption status = null;
		//若該筆訂單已經取消 不用進入計算
		if(shipmentChanges.stream().anyMatch(shipmentChange -> {
			return shipmentCancel.getOptionName().equals(shipmentChange.getShipmentChangeType().getOptionName());
		})) {
			return null;
		}
		
		do {
			searchDate = searchDate.plusDays(1);
			
			status = this.getDateStatus(searchDate, firstDeliveryDate, shipmentChanges, shipmentRecordDetails, dayOfWeek, duration);
			//多加一個條件 若往後查三個月 還沒找到資料 也跳出
			if(searchDate.isAfter(maxDate)) {
				return null;
			}
		} while(status == null || !(shipmentDeliver.getOptionName().equals(status.getOptionName()) || 
				shipmentReady.getOptionName().equals(status.getOptionName())));
		
		return searchDate;
	}
	
	@Override
	public List<Integer> listAllOrderIdsByDate(LocalDate date) {
		List<CustomerOrder> customerOrders = listAllCustomerOrdersByDate(date);
		if(customerOrders.isEmpty()) {
			return new ArrayList<>();
		}
		
		return customerOrders.stream()
				.map(CustomerOrder -> CustomerOrder.getOrderId())
				.collect(Collectors.toList());
	}

	@Override
	public Page<CustomerOrder> listAllOrdersPageable(List<Integer> orderIds, int page, int size) {
		
		Page<CustomerOrder> customerOrderPages = customerOrderDAO.findByOrderIdIn(orderIds, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		
		return customerOrderPages;
	}

	@Override
	public Page<CustomerOrder> findByOrderIdIn(List<Integer> orderIds, int page, int size) {
		Page<CustomerOrder> customerOrderPages = customerOrderDAO.findByOrderIdIn(orderIds, new PageRequest(page, size, new Sort(Sort.Direction.DESC, "orderId")));
		return customerOrderPages;
	}

	@Override
	@Transactional
	public ShipmentRecord add(ShipmentRecord shipmentRecord, List<Integer> orderIds) {
		ConstantOption shipmentType = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDelivered.toString());
		shipmentRecord.setValidFlag(VALID_FLAG.VALID.value());
		shipmentRecord.setShipmentType(shipmentType);
		shipmentRecord.setShipmentRecordDetails(getShipmentRecordDetails(shipmentRecord, orderIds));
		shipmentRecord = shipmentRecordDAO.save(shipmentRecord);
		return shipmentRecord;
	}
	
	private List<ShipmentRecordDetail> getShipmentRecordDetails(ShipmentRecord shipmentRecord, List<Integer> orderIds) {
		return orderIds.stream().map(orderId -> {
			ShipmentRecordDetail shipmentRecordDetail = new ShipmentRecordDetail();
			CustomerOrder customerOrder = new CustomerOrder();
			customerOrder.setOrderId(orderId);
			shipmentRecordDetail.setCustomerOrder(customerOrder);
			shipmentRecordDetail.setValidFlag(VALID_FLAG.VALID.value());
			shipmentRecordDetail.setShipmentRecord(shipmentRecord);
			return shipmentRecordDetail;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<ShipmentRecord> getShipmentRecordWithDetails(Date date, int page, int size) {
		Page<ShipmentRecord> shipmentRecords = shipmentRecordDAO.findByDateAndValidFlag(date, VALID_FLAG.VALID.value(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "shipmentRecordId")));
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByShipmentRecordIn(shipmentRecords.getContent());
		
		shipmentRecords = shipmentRecords.map(shipmentRecord -> {
			shipmentRecord.setShipmentRecordDetails(shipmentRecordDetails.stream()
					.filter(detail -> {
						return detail.getShipmentRecord().getShipmentRecordId().equals(shipmentRecord.getShipmentRecordId());
					}).collect(Collectors.toList()));
			return shipmentRecord;
		});
		
		return shipmentRecords;
	}

	@Override
	public ShipmentRecord findOneShipmentRecord(int shipmentRecordId) {
		ShipmentRecord shipmentRecord = shipmentRecordDAO.findOne(shipmentRecordId);
		return shipmentRecord;
	}
	
	@Override
	public ShipmentRecord findOneShipmentRecord(Date date) {
		List<ShipmentRecord> shipmentRecords = shipmentRecordDAO.findByDateEqualsAndValidFlag(date, VALID_FLAG.VALID.value());
		if(!shipmentRecords.isEmpty() && shipmentRecords.size() > 1) {
			throw new HttpServiceException(ReturnMessageEnum.Shipment.HasMoreThanOneRecordInSameDay.getReturnMessage());
		}
		return shipmentRecords.size() == 0 ? null : shipmentRecords.get(0);
	}

	@Override
	@Transactional
	public ShipmentRecord invalidate(ShipmentRecord shipmentRecord) {
		shipmentRecord.setValidFlag(VALID_FLAG.INVALID.value());
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByShipmentRecord(shipmentRecord);
		shipmentRecordDetails = shipmentRecordDetails.stream().map(detail -> {
			detail.setValidFlag(VALID_FLAG.INVALID.value());
			return detail;
		}).collect(Collectors.toList());
		
		shipmentRecord.setShipmentRecordDetails(shipmentRecordDetails);
		shipmentRecordDAO.save(shipmentRecord);
		return shipmentRecord;
	}
	
	@Override
	public Page<ShipmentChange> findAllByConditions(ShipmentChangeCondition condition, int page, int size) {
		Page<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByConditions(
				condition.getDeliverStartDate(),
				condition.getDeliverEndDate(),
				condition.getUpdateStartDate(),
				condition.getUpdateEndDate(),
				condition.getValidFlag(),
				condition.getOrderId(),
				condition.getName(),
				condition.getReceiverCellphone(),
				condition.getShipmentChangeType(),
				new PageRequest(page, size, new Sort(Sort.Direction.DESC, "applyDate")));
		return shipmentChanges;
	}
	
	@Override
	public List<ShipmentChange> findAllByConditions(ShipmentChangeCondition condition) {
		List<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByConditions(
				condition.getDeliverStartDate(),
				condition.getDeliverEndDate(),
				condition.getUpdateStartDate(),
				condition.getUpdateEndDate(),
				condition.getValidFlag(),
				condition.getOrderId(),
				condition.getName(),
				condition.getReceiverCellphone(),
				condition.getShipmentChangeType());
		return shipmentChanges;
	}

	@Override
	public ShipmentPreferenceBean findInitialShipmentPreference(LocalDate date, List<String> categoryItemIds) {
		List<CustomerOrder> customerOrders =  this.listAllCustomerOrdersByDate(date);	
		
		List<OrderPreference> orderPreferences = orderPreferenceDAO.findByCustomerOrderIn(customerOrders);
		List<ShipmentInfoBean> shipmentInfoBeans = customerOrders.stream().map(customerOrder -> {
			List<OrderPreference> preferences = orderPreferences.stream()
					.filter(preference -> preference.getCustomerOrder().getOrderId().equals(customerOrder.getOrderId()))
					.collect(Collectors.toList());
			return new ShipmentInfoBean(
					customerOrder.getOrderId(), 
					0, 
					customerOrder.getProgramNum(),
					customerOrder.getReceiverLastName().trim() + customerOrder.getReceiverFirstName().trim(),
					0, 
					customerOrder.getOrderProgram().getAmount() * customerOrder.getProgramNum(),
					preferences,
					customerOrder.getAllowForeignFruits(),
					customerOrder.getOrderProgram().getProgramId());
		}).collect(Collectors.toList());
		
		ShipmentPreferenceBean shipmentPreferenceBean = new ShipmentPreferenceBean(
				0, DateUtil.toDate(date), shipmentInfoBeans, null);
		
		shipmentPreferenceBean = this.calculate(shipmentPreferenceBean, categoryItemIds);
		
		return shipmentPreferenceBean;
	}
	
	@Override
	public ShipmentPreferenceBean calculate(ShipmentPreferenceBean shipmentPreferenceBean, List<String> categoryItemIds) {
		List<ChosenProductItemBean> chosenProductItemBeans = categoryItemIds.stream().map(categoryItemId -> {
			return cacheProductItems.stream().filter(cacheProductItem -> {
				return cacheProductItem.getCategoryItemId().equals(categoryItemId);
			})
			.findFirst()
			.get();
		})
		.sorted((a, b) -> {
			//排序依照過期的順序 先過期的要先排
			if(a.getExpiryOrder() > b.getExpiryOrder()) {
				return 1;
			} else if(a.getExpiryOrder() < b.getExpiryOrder()) {
				return -1;
			} else {
				return 0;
			}
		})
		.map(productItem -> {
			//如果原本已經有的話 直接回傳即可
			if(shipmentPreferenceBean.getChosenProductItemBeans() != null && !shipmentPreferenceBean.getChosenProductItemBeans().isEmpty()) {
				ChosenProductItemBean foundChosenProductItemBean = shipmentPreferenceBean.getChosenProductItemBeans().stream().filter(thisChosenProductItemBean -> {
					return thisChosenProductItemBean.getProductItemId() == productItem.getProductItemId();
				})
				.findFirst()
				.get();
				if(foundChosenProductItemBean != null) {
					return foundChosenProductItemBean;
				}
			}
			List<ProductStatusBean> productStatusBeans = shipmentPreferenceBean.getShipmentInfoBeans().stream().map(shipmentInfoBean -> {
				Optional<OrderPreference> preferenceOptional = shipmentInfoBean.getOrderPreferences().stream()
						.filter(orderPreference -> orderPreference.getProduct().getProductId().equals(productItem.getProduct().getProductId()))
						.findFirst();
				
				OrderPreference preference = preferenceOptional.isPresent() ? preferenceOptional.get() : null;
				
				String status;
				StatusInteger statusInteger = null;
				if(preference != null && (preference.getLikeDegree() == 0
						|| isNotLikeForeign(productItem, shipmentInfoBean))) {
					status = ProductStatusBean.STATUS.UNLIKE.value();
					statusInteger = new StatusInteger(StatusInteger.Status.fixed.toString(), 0);
				} else {
					status = ProductStatusBean.STATUS.NO.value();
					statusInteger = new StatusInteger(StatusInteger.Status.none.toString(), 0);
				}
				return new ProductStatusBean(shipmentInfoBean.getOrderId(), 0, status, statusInteger);
			}).collect(Collectors.toList());
			return new ChosenProductItemBean(
					productItem.getProductItemId(), 
					productItem.getName(), 
					productStatusBeans, 
					null, 
					0,
					0, 
					productItem.getUnit().getOptionDesc(),
					productItem.getFamilyAmount(),
					productItem.getSingleAmount());
		})
		.collect(Collectors.toList());
		shipmentPreferenceBean.setChosenProductItemBeans(chosenProductItemBeans);
		
		//限制每一個產品數量加總不能大於最大的限制
		List<Integer> rowLimits = shipmentPreferenceBean.getShipmentInfoBeans().stream().map(shipmentInfoBean -> {
			return shipmentInfoBean.getRequiredAmount();
		}).collect(Collectors.toList());
		
		//比對水果數量 確定扣掉不喜歡的水果數量是否大於需要的數量
		//分成兩組 一組是吻合的 另一組是不吻合的
		List<Integer> unsatisfiedList = getNeedMinusUnlikeSatisfiedIndex(shipmentPreferenceBean.getChosenProductItemBeans(), rowLimits);

		List<List<ProductStatusBean>> unsatisfiedProductStatusBeanLists = new ArrayList<>();
		List<ShipmentInfoBean> shipmentInfoBeans = shipmentPreferenceBean.getShipmentInfoBeans();
		List<ShipmentInfoBean> unsatisfiedShipmentInfoBeans = new ArrayList<>();
		List<ShipmentInfoBean> satisfiedShipmentInfoBeans = new ArrayList<>();
		
		int count = 0;
		for(ShipmentInfoBean shipmentInfoBean : shipmentInfoBeans) {
			if(unsatisfiedList.contains(count)) {
				shipmentInfoBean.setErrorStatus("可選擇水果不足");
				unsatisfiedShipmentInfoBeans.add(shipmentInfoBean);
			} else {
				shipmentInfoBean.setErrorStatus(null);
				satisfiedShipmentInfoBeans.add(shipmentInfoBean);
			}
			count ++;
		};
		
		for(ChosenProductItemBean chosenProductItemBean : shipmentPreferenceBean.getChosenProductItemBeans()) {
			count = 0;
			List<ProductStatusBean> unsatisfiedProductStatusBeans = new ArrayList<>();
			List<ProductStatusBean> satisfiedProductStatusBeans = new ArrayList<>();
			for(ProductStatusBean productStatusBean : chosenProductItemBean.getProductStatusBeans()) {
				if(unsatisfiedList.contains(count)) {
					unsatisfiedProductStatusBeans.add(productStatusBean);
				} else {
					satisfiedProductStatusBeans.add(productStatusBean);
				}
				count ++;
			};
			//只把吻合的拿去計算
			chosenProductItemBean.setProductStatusBeans(satisfiedProductStatusBeans);
			unsatisfiedProductStatusBeanLists.add(unsatisfiedProductStatusBeans);
		};
		
		if(!satisfiedShipmentInfoBeans.isEmpty()) {
			chosenProductItemBeans = shipmentPreferenceBean.getChosenProductItemBeans().stream()
					.map(chosenProductItemBean -> {
						int maxLimitWithUnit = chosenProductItemBean.getMaxLimitWithUnit() != null ? chosenProductItemBean.getMaxLimitWithUnit() : getInitMaxLimitWithUnit(satisfiedShipmentInfoBeans, chosenProductItemBean); //計算上限
						chosenProductItemBean.setMaxLimitWithUnit(maxLimitWithUnit);
						return chosenProductItemBean;
					}).collect(Collectors.toList());
			
			chosenProductItemBeans = this.calculateChosenProductItemBeans(chosenProductItemBeans, satisfiedShipmentInfoBeans);
			//增加箱子號碼
			int i = 0;
			for(ShipmentInfoBean shipmentInfoBean : satisfiedShipmentInfoBeans) {
				int programNum = shipmentInfoBean.getProgramNum();
				StringJoiner joiner = new StringJoiner(",");
				int j = 1;
				while(j <= programNum) {
					joiner.add(String.valueOf(i + j));
					j++;
				}
				shipmentInfoBean.setBoxNo(joiner.toString());
				i += shipmentInfoBean.getProgramNum();
			}
		}
		
		count = 0;
		for(ChosenProductItemBean chosenProductItemBean : chosenProductItemBeans) {
			List<ProductStatusBean> productStatusBeans = chosenProductItemBean.getProductStatusBeans();
			productStatusBeans.addAll(0, unsatisfiedProductStatusBeanLists.get(count));
			count++;
		}
		
		unsatisfiedShipmentInfoBeans.addAll(satisfiedShipmentInfoBeans);
		shipmentPreferenceBean.setChosenProductItemBeans(chosenProductItemBeans);
		shipmentPreferenceBean.setShipmentInfoBeans(unsatisfiedShipmentInfoBeans);
		
		return shipmentPreferenceBean;
	}
	
	private int getInitMaxLimitWithUnit(List<ShipmentInfoBean> satisfiedShipmentInfoBeans, ChosenProductItemBean chosenProductItemBean) {
		return satisfiedShipmentInfoBeans.stream().map(shipmentInfoBean -> {
			return getRequiredAmountForProgram(shipmentInfoBean.getProgramId(), chosenProductItemBean);
		}).mapToInt(Integer::intValue)
		.sum();
	}
	
	private int getRequiredAmountForProgram(int programId, ChosenProductItemBean chosenProductItemBean) {
		if(programId == 1) {
			return chosenProductItemBean.getSingleAmount();
		} else {
			return chosenProductItemBean.getFamilyAmount();
		}
	}
	
	private boolean isNotLikeForeign(ProductItem productItem, ShipmentInfoBean shipmentInfoBean) {
		if(productItem.getIsForeign() == 1 && "N".equals(shipmentInfoBean.getAllowForeignFruits())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private List<Integer> getNeedMinusUnlikeSatisfiedIndex(List<ChosenProductItemBean> chosenProductItemBeans, List<Integer> rowLimits) {
		List<List<StatusInteger>> statusIntegerLists = chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			return chosenProductItemBean.getProductStatusBeans().stream().map(productStatusBean -> {
				return productStatusBean.getRequiredAmount();
			}).collect(Collectors.toList());
		}).collect(Collectors.toList());
		
		List<List<StatusInteger>> rowLists = ListTranspose.transpose(statusIntegerLists);
		int row = 0;
		List<Integer> unsatisfiedList = new ArrayList<>();
		for(List<StatusInteger> rowList: rowLists) {
			int rowLimit = rowLimits.get(row);
			int totalCount = rowList.stream().filter(statusInteger -> {
				return statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
			}).map(statusInteger -> {
				return statusInteger.getInteger();
			}).reduce(0, (a, b) -> a + b);
			int needAmount = rowLimit - totalCount;
			
			List<StatusInteger> filterdList = rowList.stream().filter(statusInteger -> {
				return !statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
			}).map(statusInteger -> {
				statusInteger.setInteger(0);
				return statusInteger;
			}).collect(Collectors.toList());
			
			if (needAmount > filterdList.size()) {
				unsatisfiedList.add(row);
			}
			row++;
		}
		return unsatisfiedList;
	}
	
	@Override
	public List<ChosenProductItemBean> calculateChosenProductItemBeans(List<ChosenProductItemBean> chosenProductItemBeans, List<ShipmentInfoBean> shipmentInfoBeans) {
		List<Integer> rowLimits = shipmentInfoBeans.stream().map(shipmentInfoBean -> {
			return shipmentInfoBean.getRequiredAmount();
		}).collect(Collectors.toList());
		List<Integer> colLimits = chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			return chosenProductItemBean.getMaxLimitWithUnit();
		}).collect(Collectors.toList());
		List<List<StatusInteger>> statusIntegerLists = chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			return chosenProductItemBean.getProductStatusBeans().stream().map(productStatusBean -> {
				return productStatusBean.getRequiredAmount();
			}).collect(Collectors.toList());
		}).collect(Collectors.toList());
		
		statusIntegerLists = this.calculate(rowLimits, colLimits, statusIntegerLists, chosenProductItemBeans, shipmentInfoBeans);
		int col = 0;
		for(ChosenProductItemBean chosenProductItemBean: chosenProductItemBeans) {
			int row = 0;
			int actualTotalWithUnit = 0;
			int acutalTotalFamily = 0;
			int acutalTotalSingle = 0;
			int actualTotal = chosenProductItemBean.getProductStatusBeans().stream()
					.map(productStatusBean -> {
						return productStatusBean.getRequiredAmount().getInteger();
					})
					.mapToInt(Integer::intValue)
					.sum();
			for(ProductStatusBean productStatusBean: chosenProductItemBean.getProductStatusBeans()) {
				ShipmentInfoBean shipmentInfoBean = shipmentInfoBeans.get(row);
				productStatusBean.setRequiredAmount(statusIntegerLists.get(col).get(row));
				if(shipmentInfoBean.getProgramId() == 1) {
					acutalTotalSingle += productStatusBean.getRequiredAmount().getInteger();
				} else {
					acutalTotalFamily += productStatusBean.getRequiredAmount().getInteger();
				}
				actualTotalWithUnit += getRequiredAmountForProgram(shipmentInfoBean.getProgramId(), chosenProductItemBean) 
						* productStatusBean.getRequiredAmount().getInteger();
				row++;
			}
			chosenProductItemBean.setActualTotalFamily(acutalTotalFamily);
			chosenProductItemBean.setActualTotalSingle(acutalTotalSingle);
			chosenProductItemBean.setActualTotal(actualTotal);
			chosenProductItemBean.setActualTotalWithUnit(actualTotalWithUnit);
			col++;
		}
		
		return chosenProductItemBeans;
	
	}
	
	@Override
	public List<List<StatusInteger>> calculate(List<Integer> rowLimits, List<Integer> colLimits,
			List<List<StatusInteger>> statusIntegerLists, List<ChosenProductItemBean> chosenProductItemBeans, List<ShipmentInfoBean> shipmentInfoBeans) {
		try {
			//隨機產生一組巢狀陣列 必須要每張的出貨單數量都滿足 產生完之後再檢查是否有通過每一種水果的上限的檢查
			statusIntegerLists = this.getRandomLists(statusIntegerLists, rowLimits, colLimits, chosenProductItemBeans, shipmentInfoBeans);
			return statusIntegerLists;
		} catch(StackOverflowError e) {
			String list = printCalculatedResult(colLimits, rowLimits, statusIntegerLists);
			ReturnMessage message = ReturnMessageEnum.ShipmentPrerence.UnableToGetResult.getReturnMessage();
			ReturnMessage returnMessage = new ReturnMessage(message.getErrorCode(), 
					message.getMessage() + list, message.getStatus());
			throw new HttpServiceException(returnMessage);
		}
	}
	
	public List<List<StatusInteger>> getRandomLists(List<List<StatusInteger>> statusIntegerLists, List<Integer> rowLimits, List<Integer> colLimits, List<ChosenProductItemBean> chosenProductItemBeans,  List<ShipmentInfoBean> shipmentInfoBeans) {
		List<List<StatusInteger>> rowLists = ListTranspose.transpose(statusIntegerLists);
		
		int row = 0;
		for(List<StatusInteger> rowList: rowLists) {
			rowList = getRandomRowList(rowList, rowLimits.get(row), row);
			row++;
		}
		
		//檢查數量是否通過單位數量匹配
		if(checkConstraints(statusIntegerLists, colLimits, chosenProductItemBeans, shipmentInfoBeans)) {
			return statusIntegerLists;
		} else {
			return getRandomLists(statusIntegerLists, rowLimits, colLimits, chosenProductItemBeans, shipmentInfoBeans);
		}
	}
	
	public boolean checkConstraints(List<List<StatusInteger>> statusIntegerLists, List<Integer> colLimits, List<ChosenProductItemBean> chosenProductItemBeans, List<ShipmentInfoBean> shipmentInfoBeans) {
		if(chosenProductItemBeans == null || shipmentInfoBeans == null) {
			int i = 0;
			for(List<StatusInteger> statusIntegerList: statusIntegerLists) {
				int totalCount = statusIntegerList.stream().map(statusInteger -> {
					return statusInteger.getInteger();
				}).reduce(0, (a, b) -> a + b);
				if(totalCount > colLimits.get(i)) {
					return false;
				}
				i++;
			}
			return true;
		} else {
			int col = 0;
			for(List<StatusInteger> statusIntegerList: statusIntegerLists) {
				int row = 0;
				int totalCount = 0;
				ChosenProductItemBean chosenProductItemBean = chosenProductItemBeans.get(col);
				for(StatusInteger statusInteger: statusIntegerList) {
					ShipmentInfoBean shipmentInfoBean = shipmentInfoBeans.get(row);
					totalCount += this.getRequiredAmountForProgram(shipmentInfoBean.getProgramId(), chosenProductItemBean) * statusInteger.getInteger();
					row++;
				}
				if(totalCount > colLimits.get(col)) {
					return false;
				}
				col++;
			}
			return true;
		}
	}
	
	public List<StatusInteger> getRandomRowList(List<StatusInteger> rowList, int requiredAmount, int index) {
		int totalCount = rowList.stream().filter(statusInteger -> {
			return statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
		}).map(statusInteger -> {
			return statusInteger.getInteger();
		}).reduce(0, (a, b) -> a + b);
		int needAmount = requiredAmount - totalCount;
		if(needAmount < 0) {
			throw new HttpServiceException(ReturnMessageEnum.ShipmentPrerence.RequiredFruitAmountIsNotEnoughForUser.getReturnMessage());
		}
		
		List<StatusInteger> filterdList = rowList.stream().filter(statusInteger -> {
			return !statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
		}).map(statusInteger -> {
			statusInteger.setInteger(0);
			return statusInteger;
		}).collect(Collectors.toList());
		
		if(needAmount > filterdList.size()) {
			throw new HttpServiceException(ReturnMessageEnum.ShipmentPrerence.RequiredFruitAmountIsNotEnoughForUser.getReturnMessage());
		}
		//隨機塞值
		Collections.shuffle(filterdList);
		filterdList = filterdList.subList(0, needAmount)
				.stream().map(statusInteger -> {
					statusInteger.setInteger(1);
					return statusInteger;
				}).collect(Collectors.toList());
		
		return rowList;
	}

	@Override
	public String printCalculatedResult(List<Integer> colLimits, List<Integer> rowLimits,
			List<List<StatusInteger>> statusIntegerLists) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for(int row = 0; row < rowLimits.size(); row ++) {
			for(int col = 0; col < colLimits.size(); col ++) {
				sb.append(statusIntegerLists.get(col).get(row).getInteger() + "\t");
			}
			sb.append("|\t" + rowLimits.get(row) + "\n");
		}
		
		for(Integer col : colLimits) {
			sb.append("--" + "\t");
		}
		sb.append("\n");
		for(Integer col : colLimits) {
			sb.append(col + "\t");
		}
		sb.append("\n");
		logger.info(sb.toString());
		return sb.toString();
	}
	
	@Override
	public List<CustomerOrder> countShipmentTimes(List<CustomerOrder> customerOrders) {
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		List<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		customerOrders = customerOrders.stream().map(customerOrder -> {
			List<ShipmentRecordDetail> matchedShipmentRecordDetails = shipmentRecordDetails.stream().filter(shipmentRecordDetail-> {
				return customerOrder.getOrderId().equals(shipmentRecordDetail.getCustomerOrder().getOrderId());
			}).collect(Collectors.toList());
			
			List<ShipmentChange> matchedShipmentChanges = shipmentChanges.stream().filter(shipmentChange-> {
				return customerOrder.getOrderId().equals(shipmentChange.getCustomerOrder().getOrderId());
			}).collect(Collectors.toList());
			
			int total = this.countShipmentTimes(customerOrder, matchedShipmentRecordDetails, matchedShipmentChanges);
			customerOrder.setShipmentCount(total);
			return customerOrder;
		}).collect(Collectors.toList());
		
		return customerOrders;
	}

	@Override
	public int countShipmentTimes(CustomerOrder customerOrder) {
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderAndValidFlag(customerOrder, VALID_FLAG.VALID.value());
		List<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByCustomerOrderAndValidFlag(customerOrder, VALID_FLAG.VALID.value());
		return this.countShipmentTimes(customerOrder, shipmentRecordDetails, shipmentChanges);
	}

	@Override
	public int countShipmentTimes(CustomerOrder customerOrder, List<ShipmentRecordDetail> shipmentRecordDetails,
			List<ShipmentChange> shipmentChanges) {
		//計算邏輯
		//最原始的出貨記錄 (記錄到2016/06/24)
		//出貨記錄已出貨 出貨修改已出貨 ++ (2016/06/24之前的記錄就不看)
		//出貨記錄已退貨 --(2016/06/24之前的記錄就不看)
		//需要判斷每一天若同一天有其他紀錄的話 以出貨修改為主 出貨修改優先順序則是已退貨 > 已出貨 
		if(shipmentRecordDetails.isEmpty()) {
			shipmentRecordDetails = new ArrayList<>();
		}
		if(shipmentChanges.isEmpty()) {
			shipmentChanges = new ArrayList<>();
		}
		List<Date> allDates = shipmentRecordDetails.stream().map(shipmentRecordDetail -> {
			return shipmentRecordDetail.getShipmentRecord().getDate();
		}).collect(Collectors.toList());
		
		allDates.addAll(shipmentChanges.stream().map(shipmentChange -> {
			return shipmentChange.getApplyDate();
		}).collect(Collectors.toList()));
		
		//去除重複
		allDates = new ArrayList<>(new HashSet<>(allDates));
		
		final List<ShipmentRecordDetail> fixedShipmentRecordDetails = shipmentRecordDetails;
		final List<ShipmentChange> fixedShipmentChanges = shipmentChanges;
		
		int total = allDates.stream().filter(date -> {
			return date.after(DateUtil.toDate("2016-06-24"));
		}).map(date -> {
			List<ShipmentRecordDetail> matchedShipmentRecordDetails = fixedShipmentRecordDetails.stream().filter(shipmentRecordDetail -> {
				return shipmentRecordDetail.getShipmentRecord().getDate().equals(date);
			}).collect(Collectors.toList());
			
			List<ShipmentChange> matchedshipmentChanges = fixedShipmentChanges.stream().filter(shipmentChange -> {
				return shipmentChange.getApplyDate().equals(date);
			}).collect(Collectors.toList());
			
			//退貨
			boolean foundReturn = matchedshipmentChanges.stream().anyMatch(shipmentChange -> {
				return shipmentChange.getApplyDate().equals(date) && 
						shipmentChange.getShipmentChangeType().getOptionName().equals(shipmentReturn.getOptionName().toString());
			});
			
			//在記錄或者是變更裡面發現都是有
			boolean foundDelivered = matchedshipmentChanges.stream().anyMatch(shipmentChange -> {
				return shipmentChange.getApplyDate().equals(date) && 
						shipmentChange.getShipmentChangeType().getOptionName().equals(shipmentDelivered.getOptionName().toString());
			}) || matchedShipmentRecordDetails.stream().anyMatch(shipmentRecordDetail -> {
				return shipmentRecordDetail.getShipmentRecord().getDate().equals(date);
			});
			
			
			int count = 0;
			if(foundReturn) {
				count --;
			}
			
			if(foundDelivered) {
				count ++;
			}
			return count;
		}).reduce(0, (a, b) -> a + b);
		
		return total + (customerOrder.getShipmentCount() == null ? 0 : customerOrder.getShipmentCount());
	}

	@Override
	public List<ShipmentChange> findShipmentChangesByCustomerOrders(
			List<CustomerOrder> customerOrders) {
		List<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		return shipmentChanges;
	}

	@Override
	public List<ShipmentRecordDetail> findShipmentRecordDetailsByCustomerOrders(List<CustomerOrder> customerOrders) {
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderInAndValidFlag(customerOrders, VALID_FLAG.VALID.value());
		return shipmentRecordDetails;
	}

}
