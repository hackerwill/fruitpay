package com.fruitpay.base.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fruitpay.base.dao.ScheduledRecordDAO;
import com.fruitpay.base.model.CachedBean;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ScheduledRecord;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.service.CachedService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.comm.utils.DateUtil;

@Component
public class TaskScheduler {
	
	private enum IsSuccessful {
		Y,
		N;
	}
	
	private final int MAX_SIZE = 1024;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private ShipmentService shipmentService;
	@Inject
	private CachedService cachedService;
	@Inject
	private ScheduledRecordDAO scheduledRecordDAO;
	
	@Scheduled(cron="0 0 6-24 * * *")
	public void calulcateShipmentData() {
		cachedService.setShipmentPreviewBean();
	}
	
	@Scheduled(cron="0 0 22 * * *")
	public void shipmentRecord() {
		logger.debug("Enter TaskScheduler.shipmentRecord");
		ScheduledRecord scheduledRecord = new ScheduledRecord();
		scheduledRecord.setMethodName("shipmentRecord");
		
		try {
			LocalDate now = LocalDate.now();
			List<Integer> orderIds = shipmentService.listAllOrderIdsByDate(now);
			if(orderIds.isEmpty()) {
				return;
			}
			ShipmentRecord shipmentRecord = new ShipmentRecord();
			shipmentRecord.setDate(DateUtil.toDate(now));
			ShipmentRecord oldShipmentRecord = shipmentService.findOneShipmentRecord(DateUtil.toDate(now));
			if(oldShipmentRecord != null) {
				shipmentService.invalidate(oldShipmentRecord);
			}
			shipmentRecord = shipmentService.add(shipmentRecord, orderIds);
			scheduledRecord.setIsSuccessful(IsSuccessful.Y.name());
			scheduledRecord.setMessage("Record added, shipmentRecordId: " + shipmentRecord.getShipmentRecordId());
		} catch(Exception e) {
			logger.error(e);
			scheduledRecord.setIsSuccessful(IsSuccessful.N.name());
			scheduledRecord.setMessage(getErrorMessage(e));
		} finally {
			scheduledRecord.setCreateDate(new Date());
			scheduledRecordDAO.save(scheduledRecord);
		}
		
	}
	
	private String getErrorMessage(Exception e) {
		StringBuilder sb = new StringBuilder();
		Throwable child =  e;
		while(child != null) {
			sb.append(child.getMessage() + System.lineSeparator());
			child = child.getCause();
		}
		return sb.substring(0, sb.length() > MAX_SIZE ? MAX_SIZE : sb.length()).toString();
	}

}
