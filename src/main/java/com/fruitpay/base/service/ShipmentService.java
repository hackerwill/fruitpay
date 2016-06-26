package com.fruitpay.base.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.CommConst.VALID_FLAG;
import com.fruitpay.base.model.ChosenProductItemBean;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentChangeCondition;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentInfoBean;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.model.StatusInteger;

public interface ShipmentService {
	
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size);
	
	public List<ShipmentChange> findShipmentChangesByOrderId(int orderId);
	
	public List<ShipmentChange> findShipmentChangesByCustomerOrders(List<CustomerOrder> customerOrders);
	
	public List<ShipmentRecordDetail> findShipmentRecordDetailsByOrderId(int orderId);
	
	public List<ShipmentRecordDetail> findShipmentRecordDetailsByCustomerOrders(List<CustomerOrder> customerOrders);

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
	
	public ShipmentPreferenceBean findInitialShipmentPreference(LocalDate date, List<String> categoryItemIds); 
	
	public ShipmentPreferenceBean calculate(ShipmentPreferenceBean shipmentPreferenceBean, List<String> categoryItemIds); 

	public List<List<StatusInteger>> calculate(List<Integer> colLimits, List<Integer> rowLimits, List<List<StatusInteger>> statusIntegers, List<ChosenProductItemBean> chosenProductItemBeans, List<ShipmentInfoBean> shipmentInfoBeans);
	
	public List<ChosenProductItemBean> calculateChosenProductItemBeans(List<ChosenProductItemBean> chosenProductItemBeans, List<ShipmentInfoBean> shipmentInfoBeans);
	
	public String printCalculatedResult(List<Integer> colLimits, List<Integer> rowLimits, List<List<StatusInteger>> statusIntegerLists); 
	
	public int countShipmentTimes(CustomerOrder customerOrder);
	
	public List<CustomerOrder> countShipmentTimes(List<CustomerOrder> customerOrders);
	
	public int countShipmentTimes(CustomerOrder customerOrder, List<ShipmentRecordDetail> shipmentRecordDetails, List<ShipmentChange> shipmentChanges);

	public LocalDate getNextNeedShipmentDate(CustomerOrder customerOrder, List<ShipmentChange> shipmentChanges, List<ShipmentRecordDetail> shipmentRecordDetails);
	
}
