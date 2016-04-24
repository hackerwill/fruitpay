package com.fruitpay.base.service;

import java.util.Date;
import java.util.List;

import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;

public interface ShipmentService {
	
	public List<ShipmentChange> findByOrderId(int orderId);

	public ShipmentChange add(ShipmentChange shipmentChange);
	
	public ShipmentChange update(ShipmentChange shipmentChange);
	
	public Boolean delete(ShipmentChange shipmentChange);
	
	public List<ShipmentDeliveryStatus> getAllDeliveryStatus(Date startDate, Date endDate, int orderId);
	

}
