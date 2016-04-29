package com.fruitpay.base.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
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
import com.fruitpay.base.dao.ShipmentChangeDAO;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
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
	
	//if one delivery day is pulse, the next delivery day plus day amount
	private final int JUMP_DAY = 7;
	
	private ConstantOption shipmentPulse = null;
	private ConstantOption shipmentDeliver = null;
	private ConstantOption shipmentCancel = null;
	
	@PostConstruct
	public void init(){
		shipmentDeliver = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentDeliver.toString());
		shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString()); 
		shipmentCancel = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentCancel.toString()); 
	}
	
	@Override
	public List<ShipmentChange> findByOrderId(int orderId) {
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
		
		List<ShipmentChange> shipmentChanges = this.findByOrderId(orderId);
		LocalDate firstDeliveryDate = staticDataService.getNextReceiveDay(customerOrder.getOrderDate(), 
				DayOfWeek.of(Integer.valueOf(customerOrder.getDeliveryDay().getOptionName())));
		int duration = customerOrder.getShipmentPeriod().getDuration();
		
		//unnecessary to count
		if(endDate.before(DateUtil.toDate(firstDeliveryDate)))
			return new ArrayList<ShipmentDeliveryStatus>();
		
		if(startDate.before(DateUtil.toDate(firstDeliveryDate)))
			startDate = DateUtil.toDate(firstDeliveryDate);
		
		List<ShipmentDeliveryStatus> deliveryStatuses = new ArrayList<ShipmentDeliveryStatus>();
		ShipmentDeliveryStatus first = getFirst(firstDeliveryDate, shipmentChanges, duration);
		do {
			deliveryStatuses.add(first);
			//若該次已經取消, 則是最後一次
			if(ShipmentStatus.shipmentCancel.toString().equals(first.getShipmentChangeType().getOptionName()))
				break;
			first = getNext(first, shipmentChanges, duration);
		} while (!first.getApplyDate().after(endDate));
		
		//過濾掉僅從查詢開始日期算
		final Date searchStateDate = startDate;
		deliveryStatuses = deliveryStatuses.stream().filter(status -> {
			return !status.getApplyDate().before(searchStateDate);
		}).collect(Collectors.toList());
		return deliveryStatuses;
	}
	
	private ShipmentDeliveryStatus getFirst(LocalDate fristDeliveryLocalDate, List<ShipmentChange> shipmentChanges, int duration){
		//若日期小於今天, 到資料庫查詢是否有已配送紀錄, 若是今天以後的日期, 則為待配送狀態
		Date firstDeliveryDate = DateUtil.toDate(fristDeliveryLocalDate);
		Date today = new Date();
		if(firstDeliveryDate.before(today)){
			//沒有配送過且也沒有暫停取消的, 則跳過該次
			if(!hasRecord(firstDeliveryDate, shipmentChanges)){
				return getFirst(fristDeliveryLocalDate.plusDays(duration), shipmentChanges, duration);
			}
		}
		
		ShipmentDeliveryStatus status = new ShipmentDeliveryStatus();
		status.setApplyDate(DateUtil.toDate(fristDeliveryLocalDate));
		status.setShipmentChangeType(setChageType(status, shipmentChanges));
		return status;
	}
	
	private ShipmentDeliveryStatus getNext(ShipmentDeliveryStatus status, List<ShipmentChange> shipmentChanges, int duration){
		//若日期小於今天, 到資料庫查詢是否有已配送紀錄, 若是今天以後的日期, 則為待配送狀態
		Date today = new Date();
		if(status.getApplyDate().before(today)){
			//沒有配送過且也沒有暫停取消的, 則跳過該次
			if(!hasRecord(status.getApplyDate(), shipmentChanges)){
				return getNext(status, shipmentChanges, duration);
			}
		}
		
		ShipmentDeliveryStatus nextStatus = new ShipmentDeliveryStatus();
		int nextDuration = setNextDuration(status, duration);
		LocalDate nextDate = DateUtil.toLocalDate(status.getApplyDate()).plusDays(nextDuration);
		nextStatus.setApplyDate(DateUtil.toDate(nextDate));
		nextStatus.setShipmentChangeType(setChageType(nextStatus, shipmentChanges));
		return nextStatus;
	}
	
	private boolean hasRecord(Date applyDate, List<ShipmentChange> shipmentChanges){
		
		boolean hasRecord = false;
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			if(applyDate.getTime() == shipmentChange.getApplyDate().getTime()){
				hasRecord = true;
				break;
			}
		}
		
		return hasRecord;
	}
	
	private int setNextDuration(ShipmentDeliveryStatus status, int duration){
		if(ShipmentStatus.shipmentPulse.toString().equals(status.getShipmentChangeType().getOptionName())){
			return JUMP_DAY;
		}else{
			return duration;
		}
	}
	
	private ConstantOption setChageType(ShipmentDeliveryStatus status, List<ShipmentChange> shipmentChanges){
		
		for (Iterator<ShipmentChange> iterator = shipmentChanges.iterator(); iterator.hasNext();) {
			ShipmentChange shipmentChange = iterator.next();
			if(DateUtils.isSameDay(shipmentChange.getApplyDate(), status.getApplyDate())){
				if(ShipmentStatus.shipmentPulse.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
					return shipmentPulse;
				}else if(ShipmentStatus.shipmentCancel.toString().equals(shipmentChange.getShipmentChangeType().getOptionName())){
					return shipmentCancel;
				}
			}
		}
		
		return shipmentDeliver; 
	}

	@Override
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size) {
		Page<ShipmentChange> shipmentChanges = shipmentChangeDAO.findByValidFlag(
				validFlag.value(), new PageRequest(page, size, new Sort(Sort.Direction.DESC, "applyDate")));
		return shipmentChanges;
	}

}
