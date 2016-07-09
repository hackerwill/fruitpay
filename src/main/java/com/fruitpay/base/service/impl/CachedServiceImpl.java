package com.fruitpay.base.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fruitpay.base.comm.ProgramId;
import com.fruitpay.base.comm.ShipmentPeriod;
import com.fruitpay.base.model.CachedBean;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDisplayRecord;
import com.fruitpay.base.service.CachedService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.comm.utils.DateUtil;

@Service
public class CachedServiceImpl implements CachedService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private final int TOTAL_AFTER_DAYS = 14;
	@Inject
	private ShipmentService shipmentService;
	
	private Map<LocalDate, CachedBean<List<CustomerOrder>>> shipmentPreviewMap = null;
	
	private List<ShipmentChange> shipmentChangeList = null;

	@Override
	public CachedBean<List<CustomerOrder>> getShipmentPreviewBean(LocalDate date, boolean forceUpdate) {
		if(this.shipmentPreviewMap != null && forceUpdate) {
			this.setShipmentPreviewBean(date);
		}
		
		if(this.shipmentPreviewMap == null) {
			this.setShipmentPreviewBean();
		}
		
		if(shipmentPreviewMap.containsKey(date)) {
			CachedBean<List<CustomerOrder>> cachedBean = shipmentPreviewMap.get(date);
			return cachedBean;
		} else {
			return null;
		}
	}
	
	@Override
	public Map<LocalDate, CachedBean<List<CustomerOrder>>> getShipmentPreviewMap(boolean forceUpdate) {
		if(this.shipmentPreviewMap == null) {
			return null;
		}
		return this.shipmentPreviewMap;
	}
	
	@Override
	public void setShipmentPreviewBean() {
		LocalDate date = LocalDate.now();
		LocalDate maxDate = date.plusDays(TOTAL_AFTER_DAYS);
		
		Map<LocalDate, CachedBean<List<CustomerOrder>>> map = new LinkedHashMap<>();
		while(date.isBefore(maxDate)) {
			List<CustomerOrder> customerOrders = shipmentService.listAllCustomerOrdersByDate(date);
			if(!customerOrders.isEmpty()) {
				map.put(date, new CachedBean<List<CustomerOrder>>(customerOrders));
			}
			date = date.plusDays(1);
		}
		
		this.shipmentPreviewMap = map;
	}
	
	@Override
	public void setShipmentPreviewBean(LocalDate date) {
		List<CustomerOrder> customerOrders = shipmentService.listAllCustomerOrdersByDate(date);
		Map<LocalDate, CachedBean<List<CustomerOrder>>> map = this.shipmentPreviewMap;
		if(!map.isEmpty() || !customerOrders.isEmpty()) {
			map.put(date, new CachedBean<List<CustomerOrder>>(customerOrders));
		}
	}

	@Override
	public List<ShipmentDisplayRecord> getShipmentDisplayRecords() {
		if(this.shipmentPreviewMap == null) {
			this.setShipmentPreviewBean();
		}
		
		List<LocalDate> list = new LinkedList<LocalDate>(this.shipmentPreviewMap.keySet());
		List<ShipmentDisplayRecord> shipmentDisplayRecords = list.stream().map(localDate -> {
			CachedBean<List<CustomerOrder>> cachedBean = this.shipmentPreviewMap.get(localDate);
			
			int total = cachedBean.getValue().size();
			int familyAndSingleWeekTotal = (int)cachedBean.getValue().stream().filter(customerOrder -> {
				return customerOrder.getOrderProgram().getProgramId().equals(ProgramId.FAMILY.value()) &&
						customerOrder.getShipmentPeriod().getPeriodId().equals(ShipmentPeriod.EVERY_WEEK.value());
			}).count();
			int familyAndDoubleWeekTotal = (int)cachedBean.getValue().stream().filter(customerOrder -> {
				return customerOrder.getOrderProgram().getProgramId().equals(ProgramId.FAMILY.value()) &&
						customerOrder.getShipmentPeriod().getPeriodId().equals(ShipmentPeriod.DOUBLE_WEEK.value());
			}).count();
			int singleAndEveryWeekTotal = (int)cachedBean.getValue().stream().filter(customerOrder -> {
				return customerOrder.getOrderProgram().getProgramId().equals(ProgramId.SINGLE.value()) &&
						customerOrder.getShipmentPeriod().getPeriodId().equals(ShipmentPeriod.EVERY_WEEK.value());
			}).count();
			int singleAndDoubleWeekTotal = (int)cachedBean.getValue().stream().filter(customerOrder -> {
				return customerOrder.getOrderProgram().getProgramId().equals(ProgramId.SINGLE.value()) &&
						customerOrder.getShipmentPeriod().getPeriodId().equals(ShipmentPeriod.DOUBLE_WEEK.value());
			}).count();
			
			int familytotal = familyAndSingleWeekTotal + familyAndDoubleWeekTotal;
			int singletotal = singleAndEveryWeekTotal + singleAndDoubleWeekTotal;
			
			return new ShipmentDisplayRecord(
					DateUtil.toDate(localDate), 
					cachedBean.getDate(), 
					total, 
					familytotal,
					singletotal,
					familyAndSingleWeekTotal, 
					familyAndDoubleWeekTotal,
					singleAndEveryWeekTotal,
					singleAndDoubleWeekTotal);
		}).collect(Collectors.toList());
		
		return shipmentDisplayRecords;
	}
	
}
