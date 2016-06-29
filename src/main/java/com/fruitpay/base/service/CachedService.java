package com.fruitpay.base.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fruitpay.base.model.CachedBean;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentDisplayRecord;

public interface CachedService {
	
	public CachedBean<List<CustomerOrder>> getShipmentPreviewBean(LocalDate date, boolean forceUpdate);
	
	public Map<LocalDate, CachedBean<List<CustomerOrder>>> getShipmentPreviewMap(boolean forceUpdate);
	
	public void setShipmentPreviewBean();
	
	public List<ShipmentDisplayRecord> getShipmentDisplayRecords();
	
	public void setShipmentPreviewBean(LocalDate date);

}
