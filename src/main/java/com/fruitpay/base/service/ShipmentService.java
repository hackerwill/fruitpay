package com.fruitpay.base.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentChangeCondition;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.model.ShipmentRecordDetail;

public interface ShipmentService {
	
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size);
	
	public List<ShipmentChange> findChangesByOrderId(int orderId);
	
	public List<ShipmentRecordDetail> findRecordDetailsByOrderId(int orderId);

	public ShipmentChange add(ShipmentChange shipmentChange);
	
	public ShipmentChange updateValidFlag(ShipmentChange shipmentChange, CommConst.VALID_FLAG validFlag);
	
	public ShipmentChange update(ShipmentChange shipmentChange);
	
	public Boolean delete(ShipmentChange shipmentChange);
	
	public List<ShipmentDeliveryStatus> getAllDeliveryStatus(Date startDate, Date endDate, int orderId);
	
	public List<Integer> listAllOrderIdsByDate(LocalDate date);
	
	public List<CustomerOrder> listAllCustomerOrdersByDate(LocalDate date);
	
	public Page<CustomerOrder> listAllOrdersPageable(List<Integer> orderIds, int page, int size);
	
	public Page<CustomerOrder> findByOrderIdIn(List<Integer> orderIds, int page, int size);
	
	public ShipmentRecord add(ShipmentRecord shipmentRecord, List<Integer> orderIds);
	
	public Page<ShipmentRecord> getShipmentRecordWithDetails(Date date, int page, int size);
	
	public ShipmentRecord findOneShipmentRecord(int shipmentRecordId);
	
	public ShipmentRecord invalidate(ShipmentRecord shipmentRecord);
	
	public ShipmentRecord findOneShipmentRecord(Date date);
	
	public Page<ShipmentChange> findAllByConditions(ShipmentChangeCondition condition, int page, int size);
	
	public List<ShipmentChange> findAllByConditions(ShipmentChangeCondition condition); 
	
	public ShipmentPreferenceBean findInitialShipmentPreference(LocalDate date, List<Integer> productIds); 

}
