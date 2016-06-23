package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CustomerOrderDAO;
import com.fruitpay.base.dao.OrderPreferenceDAO;
import com.fruitpay.base.dao.ShipmentChangeDAO;
import com.fruitpay.base.dao.ShipmentRecordDAO;
import com.fruitpay.base.dao.ShipmentRecordDetailDAO;
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
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
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
	
	//if one delivery day is pulse, the next delivery day plus day amount
	private final int JUMP_DAY = 7;
	
	private ConstantOption shipmentPulse = null;
	private ConstantOption shipmentDeliver = null;
	private ConstantOption shipmentCancel = null;
	private ConstantOption shipmentDelivered = null;
	private ConstantOption shipmentReady = null;
	private ConstantOption shipmentReturn = null;
	private List<ProductItem> cacheProductItems = null;
	
	@PostConstruct
	public void init(){
		shipmentDelivered = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDelivered.toString());
		shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString()); 
		shipmentCancel = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentCancel.toString()); 
		shipmentReady = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentReady.toString()); 
		shipmentReturn = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentReturn.toString()); 
		cacheProductItems = staticDataService.getAllProductItems();
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
		List<ShipmentChange> shipmentChanges = this.findChangesByOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = this.findRecordDetailsByOrderId(orderId);
		List<ShipmentDeliveryStatus> deliveryStatuses = new ArrayList<ShipmentDeliveryStatus>();
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), dayOfWeek);
		int duration = customerOrder.getShipmentPeriod().getDuration();
		//檢查是否有時間在第一次之前 若有的話 以最早的為主
		firstDeliveryDate = checkAndModifiedDeliveryDate(firstDeliveryDate, shipmentChanges, shipmentRecordDetails);
				
		//unnecessary to count
		if(endDate.before(DateUtil.toDate(firstDeliveryDate)))
			return new ArrayList<ShipmentDeliveryStatus>();
		
		if(startDate.before(DateUtil.toDate(firstDeliveryDate)))
			startDate = DateUtil.toDate(firstDeliveryDate);
		LocalDate date = DateUtil.toLocalDate(startDate);
		
		while(!date.isAfter(DateUtil.toLocalDate(endDate))){
			ConstantOption shipmentChangeType = getDateStatus(date, firstDeliveryDate, shipmentChanges, shipmentRecordDetails, dayOfWeek, duration);
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
	
	private LocalDate checkAndModifiedDeliveryDate(LocalDate firstDeliveryDate, List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecordDetails) {
		List<LocalDate> sortDateList = new ArrayList<>();
		sortDateList.addAll(shipmentChanges.stream().map(shipmentChange -> {
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
		
		return firstDeliveryDate;
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
	public List<ShipmentRecordDetail> findRecordDetailsByOrderId(int orderId) {
		CustomerOrder customerOrder = new CustomerOrder();
		customerOrder.setOrderId(orderId);
		List<ShipmentRecordDetail> shipmentRecordDetails = shipmentRecordDetailDAO.findByCustomerOrderAndValidFlag(customerOrder, VALID_FLAG.VALID.value());
		return shipmentRecordDetails;
	}
	
	@Override
	public List<CustomerOrder> listAllCustomerOrdersByDate(LocalDate date) {
		if(date == null) 
			return new ArrayList<CustomerOrder>();
		
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
			return new ArrayList<CustomerOrder>();
		
		List<CustomerOrder> customerOrders = customerOrderDAO.findByValidFlagAndDeliveryDayAndOrderStatusIn(
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
			firstDeliveryDate = checkAndModifiedDeliveryDate(firstDeliveryDate, shipmentChanges, shipmentRecordDetails);
			
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
	public List<Integer> listAllOrderIdsByDate(LocalDate date) {
		return listAllCustomerOrdersByDate(date).stream()
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
				condition.getStartDate(),
				condition.getEndDate(),
				condition.getValidFlag(),
				condition.getOrderId(),
				condition.getName(),
				condition.getReceiverCellphone(),
				new PageRequest(page, size, new Sort(Sort.Direction.DESC, "applyDate")));
		return shipmentChanges;
	}
	
	@Override
	public List<ShipmentChange> findAllByConditions(ShipmentChangeCondition condition) {
		List<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByConditions(
				condition.getStartDate(),
				condition.getEndDate(),
				condition.getOrderId(),
				condition.getName(),
				condition.getReceiverCellphone(),
				condition.getValidFlag());
		return shipmentChanges;
	}

	@Override
	public ShipmentPreferenceBean findInitialShipmentPreference(LocalDate date, List<String> categoryItemIds) {
		List<CustomerOrder> customerOrders =  this.listAllCustomerOrdersByDate(date);	
		List<OrderPreference> orderPreferences = orderPreferenceDAO.findByCustomerOrderIn(customerOrders);
		List<ShipmentInfoBean> shipmentInfoBeans = customerOrders.stream().map(customerOrder -> {
			return new ShipmentInfoBean(customerOrder.getOrderId(), 0, 
					customerOrder.getReceiverLastName().trim() + customerOrder.getReceiverFirstName().trim(),
					0, customerOrder.getOrderProgram().getAmount());
		}).collect(Collectors.toList());
		
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
			List<ProductStatusBean> productStatusBeans = customerOrders.stream().map(customeOrder -> {
				List<OrderPreference> preferences = orderPreferences.stream()
						.filter(preference -> preference.getCustomerOrder().getOrderId().equals(customeOrder.getOrderId()))
						.filter(orderPreference -> orderPreference.getProduct().getProductId().equals(productItem.getProduct().getProductId()))
						.collect(Collectors.toList());
				
				String status;
				StatusInteger statusInteger = null;
				if(!preferences.isEmpty() && preferences.get(0).getLikeDegree() == 0) {
					status = ProductStatusBean.STATUS.UNLIKE.value();
					statusInteger = new StatusInteger(StatusInteger.Status.fixed.toString(), 0);
				} else {
					status = ProductStatusBean.STATUS.NO.value();
					statusInteger = new StatusInteger(StatusInteger.Status.none.toString(), 0);
				}
				return new ProductStatusBean(customeOrder.getOrderId(), 0, status, statusInteger);
			}).collect(Collectors.toList());
			//計算某一個產品的總數
			int count = productStatusBeans.stream()
				.map(productStatusBean -> {
					return productStatusBean.getStatus().equals(ProductStatusBean.STATUS.UNLIKE.value()) ? 0 : Integer.valueOf(productStatusBean.getStatus());
				})
				.reduce(0, (a,b) -> {
					return a + b;
				});
			return new ChosenProductItemBean(productItem.getProductItemId(), productItem.getName(), productStatusBeans, null, 0.0);
		})
		.collect(Collectors.toList());
		
		ShipmentPreferenceBean shipmentPreferenceBean = new ShipmentPreferenceBean(
				0, DateUtil.toDate(date), shipmentInfoBeans, chosenProductItemBeans);
		
		shipmentPreferenceBean = this.calculate(shipmentPreferenceBean);
		
		return shipmentPreferenceBean;
	}
	
	public ShipmentPreferenceBean calculate(ShipmentPreferenceBean shipmentPreferenceBean) {
		//先跑邏輯一: 限制每一個產品數量加總不能大於最大的限制 若沒有設定限制則排到所有出貨單都排滿為止
		//再跑邏輯二: 限制每一張出貨單的數量都必須要剛好等於需要的數量
		List<Integer> rowLimits = shipmentPreferenceBean.getShipmentInfoBeans().stream().map(ShipmentInfoBean -> {
			return ShipmentInfoBean.getRequiredAmount();
		}).collect(Collectors.toList());
		
		List<ChosenProductItemBean> chosenProductItemBeans = shipmentPreferenceBean.getChosenProductItemBeans().stream()
			.map(chosenProductItemBean -> {
				int maxLimit = chosenProductItemBean.getMaxLimit() != null ? chosenProductItemBean.getMaxLimit() : rowLimits.size(); //若沒限制的話只要等於所有訂單數量即可
				chosenProductItemBean.setMaxLimit(maxLimit);
				return chosenProductItemBean;
			}).collect(Collectors.toList());
		
		List<Integer> colLimits = chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			return chosenProductItemBean.getMaxLimit();
		}).collect(Collectors.toList());
		
		List<List<StatusInteger>> statusIntegerLists = chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			return chosenProductItemBean.getProductStatusBeans().stream().map(productStatusBean -> {
				return productStatusBean.getRequiredAmount();
			}).collect(Collectors.toList());
		}).collect(Collectors.toList());
		
		statusIntegerLists = this.calculate(rowLimits, colLimits, statusIntegerLists);
		
		int col = 0;
		for(ChosenProductItemBean chosenProductItemBean: chosenProductItemBeans) {
			int row = 0;
			for(ProductStatusBean productStatusBean: chosenProductItemBean.getProductStatusBeans()) {
				productStatusBean.setRequiredAmount(statusIntegerLists.get(col).get(row));
				row++;
			}
			col++;
		}
		chosenProductItemBeans.stream().map(chosenProductItemBean -> {
			int actualTotal = chosenProductItemBean.getProductStatusBeans().stream()
				.map(productStatusBean -> {
					return productStatusBean.getRequiredAmount().getInteger();
				})
				.mapToInt(Integer::intValue)
				.sum();
			chosenProductItemBean.setActualTotal(actualTotal);
			return chosenProductItemBean;
		}).collect(Collectors.toList());
		shipmentPreferenceBean.setChosenProductItemBeans(chosenProductItemBeans);
		
		
		return shipmentPreferenceBean;
	}

	@Override
	public List<List<StatusInteger>> calculate(List<Integer> rowLimits, List<Integer> colLimits,
			List<List<StatusInteger>> statusIntegerLists) {
 		if(colLimits.size() < Collections.max(rowLimits)) {
			throw new IllegalArgumentException("The column number must be equal or greater then row max required amount.");
		};
		
		if(rowLimits.stream().mapToInt(Integer::intValue).sum() > colLimits.stream().mapToInt(Integer::intValue).sum()) {
			throw new IllegalArgumentException("The column sum must be equal or greater then row sum.");
		}
		
		try{
		//隨機產生一組巢狀陣列 必須要每張的出貨單數量都滿足 產生完之後再檢查是否有通過每一種水果的上限的檢查
			statusIntegerLists = this.getRandomLists(statusIntegerLists, rowLimits, colLimits);
		} catch(StackOverflowError e) {
			String list = printCalculatedResult(colLimits, rowLimits, statusIntegerLists);
			throw new IllegalArgumentException("Unable to calculate matched map. Please check you conditions." + list);
		}
		return statusIntegerLists;
	}
	
	public List<List<StatusInteger>> getRandomLists(List<List<StatusInteger>> statusIntegerLists, List<Integer> rowLimits, List<Integer> colLimits) {
		List<List<StatusInteger>> rowLists = ListTranspose.transpose(statusIntegerLists);
		
		int row = 0;
		for(List<StatusInteger> rowList: rowLists) {
			rowList = getRandomRowList(rowList, rowLimits.get(row));
			row++;
		}
		
		if(checkConstraints(statusIntegerLists, colLimits)) {
			return statusIntegerLists;
		} else {
			return getRandomLists(statusIntegerLists, rowLimits, colLimits);
		}
	}
	
	public boolean checkConstraints(List<List<StatusInteger>> statusIntegerLists, List<Integer> colLimits) {
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
	}
	
	public List<StatusInteger> getRandomRowList(List<StatusInteger> rowList, int requiredAmount) {
		int totalCount = rowList.stream().filter(statusInteger -> {
			return statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
		}).map(statusInteger -> {
			return statusInteger.getInteger();
		}).reduce(0, (a, b) -> a + b);
		int needAmount = requiredAmount - totalCount;
		if(needAmount < 0) {
			throw new IllegalArgumentException("The needAmount shouldn't be negative value. ");
		}
		
		List<StatusInteger> filterdList = rowList.stream().filter(statusInteger -> {
			return !statusInteger.getStatus().equals(StatusInteger.Status.fixed.toString());
		}).map(statusInteger -> {
			statusInteger.setInteger(0);
			return statusInteger;
		}).collect(Collectors.toList());
		
		if(needAmount > filterdList.size()) {
			throw new IllegalArgumentException("Required fruit amount is not enough for user, please do some modification");
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
	
	
}
