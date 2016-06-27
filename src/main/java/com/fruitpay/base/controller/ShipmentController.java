package com.fruitpay.base.controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.comm.OrderStatus;
import com.fruitpay.base.comm.ShipmentStatus;
import com.fruitpay.base.comm.AllowRole;
import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentChangeCondition;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentPreferenceBean;
import com.fruitpay.base.model.ShipmentRecord;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.model.ShipmentRecordPostBean;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.ShipmentService;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.annotation.UserAccessValidate;
import com.fruitpay.comm.model.ShipmentChangeExcelBean;
import com.fruitpay.comm.model.ShipmentExcelBean;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.DateUtil;
import com.fruitpay.comm.utils.ExcelUtil;

@Controller
@RequestMapping("shipmentCtrl")
public class ShipmentController {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Inject
	private ShipmentService shipmentService;
	@Inject
	private CustomerOrderService customerOrderService;
	@Inject
	private StaticDataService staticDataService;
	
	private Integer[] lalaAreaPostCode = {
			 100 //臺北市 中正區
			,103 //臺北市 大同區
			,104 //臺北市 中山區
			,105 //臺北市 松山區
			,106 //臺北市 大安區
			,108 //臺北市 萬華區
			,110 //臺北市 信義區
			,111 //臺北市 士林區
			,112 //臺北市 北投區
			,114 //臺北市 內湖區
			,115 //臺北市 南港區
			,116 //臺北市 文山區
			,220 //新北市 板橋區
			,221 //新北市 汐止區
			,231 //新北市 新店區
			,234 //新北市 永和區
			,235 //新北市 中和區
			,236 //新北市 土城區
			,241 //新北市 三重區
			,242 //新北市 新莊區
			,247 //新北市 蘆洲區
	};
	
	@RequestMapping(value = "/shipmentChange", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER, AllowRole.CUSTOMER })
	public @ResponseBody ShipmentChange addShipmentChange(@RequestBody ShipmentChange shipmentChange) {

		if (AssertUtils.isEmpty(shipmentChange))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		shipmentChange = shipmentService.add(shipmentChange);
		
		CustomerOrder customerOrder = customerOrderService.getCustomerOrder(shipmentChange.getCustomerOrder().getOrderId());
		if(shipmentChange.getShipmentChangeType().getOptionName().equals(ShipmentStatus.shipmentCancel.toString())){
			customerOrder.setOrderStatus(staticDataService.getOrderStatus(OrderStatus.AlreayCancel.getStatus()));
			customerOrderService.updateCustomerOrder(customerOrder);
		}
		
		return shipmentChange;
	}
	
	@RequestMapping(value = "/shipmentChange/invalid", method = RequestMethod.PUT)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentChange updateShipmentChange(@RequestBody ShipmentChange shipmentChange) {

		if (AssertUtils.isEmpty(shipmentChange))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		shipmentChange = shipmentService.updateValidFlag(shipmentChange, CommConst.VALID_FLAG.INVALID);
		
		if(shipmentChange.getShipmentChangeType().getOptionName().equals(ShipmentStatus.shipmentCancel.toString())){
			customerOrderService.recoverOrderStatus(shipmentChange.getCustomerOrder().getOrderId());
		}
		
		return shipmentChange;
	}
	
	@RequestMapping(value = "/shipmentChange", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Page<ShipmentChange> getAllShipmentChanges(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "validFlag", required = false, defaultValue = "1") String validFlag,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "receiverCellphone", required = false, defaultValue = "") String receiverCellphone,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		
		ShipmentChangeCondition condition = new ShipmentChangeCondition(startDate, endDate, validFlag, orderId, name, receiverCellphone);
		Page<ShipmentChange> shipmentChanges = shipmentService.findAllByConditions(condition, page, size);

		return shipmentChanges;
	}
	
	@RequestMapping(value = "/exportShipmentChanges", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody HttpServletResponse exportShipmentRecords(HttpServletRequest request, HttpServletResponse response,
			@RequestBody  List<ShipmentChange> shipmentChanges,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date,
			@RequestParam(value = "validFlag", required = false, defaultValue = "1") String validFlag,
			@RequestParam(value = "orderId", required = false, defaultValue = "") String orderId,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "receiverCellphone", required = false, defaultValue = "") String receiverCellphone,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
		
		if(shipmentChanges.isEmpty()) {
			ShipmentChangeCondition condition = new ShipmentChangeCondition(startDate, endDate, validFlag, orderId, name, receiverCellphone);
			shipmentChanges = shipmentService.findAllByConditions(condition);
		}
		ConstantOption shipmentPulse = staticDataService.getConstantOptionByName(ShipmentStatus.shipmentPulse.toString()); 
		
		List<CustomerOrder> customerOrders = customerOrderService.findByOrderIdsIncludingPreferenceAndComments(
				shipmentChanges.stream()
					.map(shipmentChange -> shipmentChange.getCustomerOrder().getOrderId())
					.collect(Collectors.toList()));
		
		List<ShipmentChange> allShipmentChanges = shipmentService.findShipmentChangesByCustomerOrders(customerOrders);
		List<ShipmentRecordDetail> allShipmentRecordDetails = shipmentService.findShipmentRecordDetailsByCustomerOrders(customerOrders);
		List<ShipmentChange> allPauseShipmentChanges = allShipmentChanges.stream().filter(ShipmentChange -> {
			return ShipmentChange.getShipmentChangeType().getOptionName().equals(shipmentPulse.getOptionName());
		}).collect(Collectors.toList());
		
		
		//計算總出貨次數
		List<CustomerOrder> customerOrderWithCountMofieds = shipmentService.countShipmentTimes(customerOrders);
		shipmentChanges = shipmentChanges.stream().map(shipmentChange -> {
			CustomerOrder matchOrder = customerOrderWithCountMofieds.stream()
					.filter(order -> order.getOrderId().equals(shipmentChange.getCustomerOrder().getOrderId()))
					.findFirst()
					.get();
			shipmentChange.setCustomerOrder(matchOrder);
			return shipmentChange;
		}).sorted((a, b) -> a.getApplyDate().compareTo(b.getApplyDate()))
		.collect(Collectors.toList());
		
		List<Map<String, Object>> map = shipmentChanges.stream().map(shipmentChange -> {
			List<ShipmentChange> pauseShipmentChanges = allPauseShipmentChanges.stream()
				.filter(PauseShipmentChange -> {
					return PauseShipmentChange.getCustomerOrder().getOrderId().equals(shipmentChange.getCustomerOrder().getOrderId());
				}).sorted((a, b) -> a.getApplyDate().compareTo(b.getApplyDate()))
				.collect(Collectors.toList());
			
			List<ShipmentChange> thisShipmentChanges = allShipmentChanges.stream()
					.filter(PauseShipmentChange -> {
						return PauseShipmentChange.getCustomerOrder().getOrderId().equals(shipmentChange.getCustomerOrder().getOrderId());
					}).sorted((a, b) -> a.getApplyDate().compareTo(b.getApplyDate()))
					.collect(Collectors.toList());
			
			List<ShipmentRecordDetail> thisShipmentRecordDetails = allShipmentRecordDetails.stream()
					.filter(shipmentRecordDetail -> {
						return shipmentRecordDetail.getCustomerOrder().getOrderId().equals(shipmentChange.getCustomerOrder().getOrderId());
					})
					.collect(Collectors.toList());
 			try {
 				LocalDate nextShipmentDate = shipmentService.getNextNeedShipmentDate(shipmentChange.getCustomerOrder(), thisShipmentChanges, thisShipmentRecordDetails);
				return new ShipmentChangeExcelBean(shipmentChange, pauseShipmentChanges, nextShipmentDate).getMap();
			} catch (Exception e) {
				throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
			}
		}).collect(Collectors.toList());
		
		try {
			ExcelUtil.doExcelExport(request, response, "xls", map, new ShipmentChangeExcelBean().getColList());
		} catch (Exception e) {
			throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
		}

		return response;
		
	}

	@RequestMapping(value = "/shipmentChange/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<ShipmentChange> getShipmentChanges(@PathVariable Integer orderId) {

		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());

		List<ShipmentChange> shipmentChanges = shipmentService.findShipmentChangesByOrderId(orderId);

		return shipmentChanges;
	}
	
	@RequestMapping(value = "/shipmentPeriod/{orderId}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody List<ShipmentDeliveryStatus> getShipmentChanges(@PathVariable Integer orderId,
			@RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
			@RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
			
		if(AssertUtils.isEmpty(startDate)){
			startDate = DateUtil.toDate("2016-04-26", "YYYY-MM-DD");
		}
		//現在開始下三個月
		if(AssertUtils.isEmpty(endDate)){
			endDate = DateUtil.toDate(DateUtil.toLocalDate(new Date()).plusMonths(3));
		}
		if (AssertUtils.isEmpty(orderId))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		
		if(endDate.before(startDate))
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		
		List<ShipmentDeliveryStatus> shipmentDeliveryStatuses = shipmentService.getAllDeliveryStatus(startDate, endDate, orderId);
		
		return shipmentDeliveryStatuses;
	}
	
	@RequestMapping(value = "/exportShipments", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody HttpServletResponse exportShipments(HttpServletRequest request, HttpServletResponse response,
			@RequestBody  List<Integer> orderIds,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		
		if(orderIds.size() == 0 || date == null) {
			throw new HttpServiceException(ReturnMessageEnum.Common.NotFound.getReturnMessage());
		}
		
		LocalDate localDate = DateUtil.toLocalDate(date);
		
		List<CustomerOrder> customerOrders = customerOrderService.findByOrderIdsIncludingPreferenceAndComments(orderIds);
		
	    Comparator<CustomerOrder> comparator = (o1, o2) -> {
	    	int programCompare;
	    	if(o1.getOrderProgram().getProgramId() > o2.getOrderProgram().getProgramId()) {
	    		programCompare = 1;
	    	} else if(o1.getOrderProgram().getProgramId() < o2.getOrderProgram().getProgramId()) {
	    		programCompare = -1;
	    	} else {
	    		programCompare = 0;
	    	}
	    	
	    	int postcode1 = Integer.valueOf(o1.getPostalCode().getPostCode());
    		int postcode2 = Integer.valueOf(o2.getPostalCode().getPostCode());
    		boolean postcode1Check = checkInLala(postcode1);
    		boolean postcode2Check = checkInLala(postcode2);
	    	
	    	if (!postcode1Check && !postcode2Check) {
	    		return -1 * programCompare;
	    	} else if (postcode1Check && !postcode2Check) {
	    		return 1;
	    	} else if (!postcode1Check && postcode2Check) {
	    		return -1;
	    	} else {
	    		return programCompare;
	    	}
	    };
		 
	    customerOrders = customerOrders.stream().sorted(comparator).collect(Collectors.toList());
	    
		List<Map<String, Object>> map = customerOrders.stream().map(customerOrder -> {
			try {
				String isLala = checkInLala(Integer.valueOf(customerOrder.getPostalCode().getPostCode())) ? "1" : "";
				return new ShipmentExcelBean(customerOrder, localDate.minusDays(1), localDate, isLala).getMap();
			} catch (Exception e) {
				throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
			}
		}).collect(Collectors.toList());
		
		try {
			ExcelUtil.doExcelExport(request, response, "xls", map, new ShipmentExcelBean().getColList());
		} catch (Exception e) {
			throw new HttpServiceException(ReturnMessageEnum.Common.UnexpectedError.getReturnMessage());
		}

		return response;

	}
	
	private boolean checkInLala(Integer postCode) {
		return Arrays.asList(lalaAreaPostCode).stream().anyMatch(value -> value.equals(postCode));
	}
	
	@RequestMapping(value = "/shipmentPreview", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentPreview getShipmentPreview(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		
		LocalDate localDate = null;
		if(date != null){
			localDate = DateUtil.toLocalDate(date);	
		}
		
		List<Integer> orderIds = shipmentService.listAllOrderIdsByDate(localDate);
		Page<CustomerOrder> customerOrders = shipmentService.listAllOrdersPageable(orderIds, page, size);
		
		List<CustomerOrder> duplicatedCustomerOrders = customerOrders.getContent().stream()
				.filter(thisOrder -> {
					return customerOrders.getContent().stream().filter(order -> {
						return order.getCustomer().getCustomerId().equals(thisOrder.getCustomer().getCustomerId());
					}).count() > 1;
				}).collect(Collectors.toList());
		ShipmentPreview shipmentPreview = new ShipmentPreview(customerOrders, orderIds, duplicatedCustomerOrders);
		return shipmentPreview;
	}
	
	@RequestMapping(value = "/shipmentRecord", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentRecord addShipmentRecord(@RequestBody ShipmentRecordPostBean shipmentRecordPostBean) {
		ShipmentRecord shipmentRecord = shipmentRecordPostBean.getShipmentRecord();
		List<Integer> orderIds = shipmentRecordPostBean.getOrderIds();
		
		if (AssertUtils.isEmpty(shipmentRecord) || AssertUtils.anyIsEmpty(orderIds))
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		//時間格式設定
		shipmentRecord.setDate(DateUtil.toDate(DateUtil.toLocalDate(shipmentRecord.getDate())));
		
		ShipmentRecord oldShipmentRecord = shipmentService.findOneShipmentRecord(shipmentRecord.getDate());
		if(oldShipmentRecord != null) {
			shipmentService.invalidate(oldShipmentRecord);
		}
		
		ShipmentRecord shipmentrecord = shipmentService.add(shipmentRecord, orderIds);
		
		return shipmentrecord;
	}
	
	@RequestMapping(value = "/shipmentRecord", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody Page<ShipmentRecord> getShipmentRecords(
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		
		Page<ShipmentRecord> shipmentrecords = shipmentService.getShipmentRecordWithDetails(date, page, size);
		return shipmentrecords;
	}
	
	@RequestMapping(value = "/shipmentRecord/date/{startDate}", method = RequestMethod.GET)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentRecord getShipmentRecord(@PathVariable String startDate) {
		
		if(AssertUtils.isEmpty(startDate)) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}
		
		ShipmentRecord shipmentrecord = shipmentService.findOneShipmentRecord(DateUtil.toDate(startDate));
		return shipmentrecord;
	}
	
	@RequestMapping(value = "/shipmentRecord/invalidate", method = RequestMethod.POST)
	@UserAccessValidate(value = { AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentRecord invalidate(@RequestBody ShipmentRecord shipmentRecord) {
		
		if(AssertUtils.isEmpty(shipmentRecord)) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}
		
		shipmentRecord = shipmentService.invalidate(shipmentRecord);
		return shipmentRecord;
	}
	
	private class ShipmentPreview implements Serializable{
		Page<CustomerOrder> customerOrders;
		List<Integer> orderIds;
		List<CustomerOrder> duplicateOrders;
		
		public ShipmentPreview(Page<CustomerOrder> customerOrders, List<Integer> orderIds, List<CustomerOrder> duplicateOrders) {
			super();
			this.customerOrders = customerOrders;
			this.orderIds = orderIds;
			this.duplicateOrders = duplicateOrders;
		}
		public Page<CustomerOrder> getCustomerOrders() {
			return customerOrders;
		}
		public void setCustomerOrders(Page<CustomerOrder> customerOrders) {
			this.customerOrders = customerOrders;
		}
		public List<Integer> getOrderIds() {
			return orderIds;
		}
		public void setOrderIds(List<Integer> orderIds) {
			this.orderIds = orderIds;
		}
		public List<CustomerOrder> getDuplicateOrders() {
			return duplicateOrders;
		}
		public void setDuplicateOrders(List<CustomerOrder> duplicateOrders) {
			this.duplicateOrders = duplicateOrders;
		}
	}
	
	@RequestMapping(value = "/shipmentPreference", method = RequestMethod.GET)
	//@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentPreferenceBean getShipmentPreference(
			@RequestParam(value = "categoryItemIdsStr", required = false, defaultValue = "0") String categoryItemIdsStr,
			@DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		
		if(AssertUtils.isEmpty(categoryItemIdsStr) || date == null) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}
		
		List<String> categoryItemIds = Arrays.asList(categoryItemIdsStr.split(",")).stream()
				.map(categoryItemId -> categoryItemId)
				.collect(Collectors.toList());
		ShipmentPreferenceBean shipmentPreferenceBean = shipmentService.findInitialShipmentPreference(DateUtil.toLocalDate(date), categoryItemIds);
		return shipmentPreferenceBean;
	}
	
	@RequestMapping(value = "/shipmentPreferenceCalculate", method = RequestMethod.POST)
	//@UserAccessValidate(value = { AllowRole.CUSTOMER, AllowRole.SYSTEM_MANAGER })
	public @ResponseBody ShipmentPreferenceBean getShipmentPreferenceCal(
			@RequestParam(value = "categoryItemIdsStr", required = false, defaultValue = "0") String categoryItemIdsStr,
			@RequestBody ShipmentPreferenceBean shipmentPreferenceBean) {
		if(AssertUtils.isEmpty(categoryItemIdsStr) || shipmentPreferenceBean == null) {
			throw new HttpServiceException(ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage());
		}
		
		List<String> categoryItemIds = Arrays.asList(categoryItemIdsStr.split(",")).stream()
				.map(categoryItemId -> categoryItemId)
				.collect(Collectors.toList());
		shipmentPreferenceBean = shipmentService.calculate(shipmentPreferenceBean, categoryItemIds);
		return shipmentPreferenceBean;
	}

}
