package com.fruitpay.comm.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.comm.utils.StringUtil;

public class OrderExcelBean implements Serializable {
	
	private String programId;	//方案名稱
	private String periodName;	//頻率
	private Integer shipmentCount; //配送次數(目前無)
	private String programName; //品名
	private String paymentModeName;	//付款方式
	private String platformName;	//平台
	private Date orderDate;	//訂單日期
	private String orderId;	//訂單編號
	private String shipmentTime; //配送時段
	private String preferences;	//水果偏好
	private String receiveWay;	//收貨方式
	private String remark;	//備註
	private String receiverName;	//收件人名稱
	private String receiverPostalCode;	//收件人區郵遞區號
	private String receiverTowership;	//收件人區域
	private String receiverAddress;	//收件人地址
	private String receiverCellphone;	//收件人手機
	private String receiverHousePhone;	//收件人電話
	private String email;	//信箱
	private Integer programNum;	//數量
	private String coupons;	//優惠券
	private Integer shippingCost;	//運費
	private Integer totalPrice; //總價
	private String customerReponse;	//客戶回應(目前無)
	private String cancelReason;	//取消原因(目前無)
	private String comingFrom;	//得知我們
	private String name;	//訂購人
	private String postalCode; //訂購人郵遞區號
	private String Towership;	//訂購人區域
	private String address;	//訂購人地址
	private String cellphone;	//訂購人手機
	private String housePhone;	//訂購人電話
	private String receiptWay;	//發票方式
	private String receiptTitle;	//抬頭
	private String receiptVatNumber;	//統一編號
	private String allowForeignFruit;	//是否要進口水果
	private String orderStatus;	//訂單狀態
	private String unlike; //不吃水果
	private String payOnReceive; //貨到付款
	private Map<String, Object> orderExcelMap;
	
	public OrderExcelBean(CustomerOrder customerOrder){
		setOrderExcelMap(new HashMap<String,Object>());
		
		this.orderStatus = customerOrder.getOrderStatus().getOrderStatusName();
		orderExcelMap.put(String.valueOf(Order.orderStatus), orderStatus);
		
		this.programName = customerOrder.getOrderProgram().getProgramName();
		orderExcelMap.put(String.valueOf(Order.programName), programName);
		
		this.periodName = customerOrder.getShipmentPeriod().getPeriodName();
		orderExcelMap.put(String.valueOf(Order.periodName), periodName);
		
		this.platformName = customerOrder.getOrderPlatform().getPlatformName();
		orderExcelMap.put(String.valueOf(Order.platformName), platformName);
		
		this.orderDate = customerOrder.getOrderDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年M月dd日");
		orderExcelMap.put(String.valueOf(Order.orderDate), format.format(orderDate));
		
		this.orderId = String.valueOf(customerOrder.getOrderId());
		orderExcelMap.put(String.valueOf(Order.orderId), orderId);
		
		this.shipmentTime = String.valueOf(customerOrder.getShipmentTime().getOptionId() - 4); //轉成要的數字格式
		orderExcelMap.put(String.valueOf(Order.shipmentTime), shipmentTime);
		
		this.unlike = getUnlikeStr(customerOrder, customerOrder.getOrderPreferences());
		orderExcelMap.put(String.valueOf(Order.unlike), unlike);
		
		this.receiveWay = customerOrder.getReceiveWay().getOptionName();
		orderExcelMap.put(String.valueOf(Order.receiveWay), receiveWay);
		
		this.remark = customerOrder.getRemark();
		orderExcelMap.put(String.valueOf(Order.remark), remark);
		
		this.payOnReceive = getPayOnReceiveStr(customerOrder);
		orderExcelMap.put(String.valueOf(Order.payOnReceive), payOnReceive);
		
		this.receiverName = customerOrder.getReceiverLastName() + customerOrder.getReceiverFirstName();
		orderExcelMap.put(String.valueOf(Order.receiverName), receiverName);
		
		this.receiverAddress = customerOrder.getPostalCode().getFullName() + customerOrder.getReceiverAddress();
		orderExcelMap.put(String.valueOf(Order.receiverAddress), receiverAddress);
		
		this.receiverCellphone = customerOrder.getReceiverCellphone();
		orderExcelMap.put(String.valueOf(Order.receiverCellphone), receiverCellphone);
		
		this.receiverHousePhone = customerOrder.getReceiverHousePhone();
		orderExcelMap.put(String.valueOf(Order.receiverHousePhone), receiverHousePhone);
		
		this.email = StringUtil.isEmptyOrSpace(customerOrder.getCustomer().getEmail())?"":customerOrder.getCustomer().getEmail();
		orderExcelMap.put(String.valueOf(Order.email), email);
		
		this.totalPrice = customerOrder.getTotalPrice();
		orderExcelMap.put(String.valueOf(Order.totalPrice), totalPrice);
		
		this.comingFrom = customerOrder.getComingFrom().getOptionDesc();
		orderExcelMap.put(String.valueOf(Order.comingFrom), comingFrom);
		
		this.name = customerOrder.getCustomer().getLastName() + customerOrder.getCustomer().getFirstName();
		orderExcelMap.put(String.valueOf(Order.name), name);
		
		this.cellphone = customerOrder.getCustomer().getCellphone();
		orderExcelMap.put(String.valueOf(Order.cellphone), cellphone);
		
		this.housePhone = customerOrder.getCustomer().getHousePhone();
		orderExcelMap.put(String.valueOf(Order.housePhone), housePhone);
		
		this.customerReponse = "";
		orderExcelMap.put(String.valueOf(Order.customerReponse), "");
	}
	
	private String getPayOnReceiveStr(CustomerOrder customerOrder){
		String str= "";
		if(customerOrder.getPaymentMode().getPaymentExtraPrice() > 0)
			str = String.valueOf(customerOrder.getTotalPrice());
		return str;
	}
	
	private String getUnlikeStr(CustomerOrder customerOrder, List<OrderPreference> list){
		StringBuilder str = new StringBuilder();
		
		if("N".equals(customerOrder.getAllowForeignFruits())){
			str.append("進口水果.");
		}
		
		for (Iterator<OrderPreference> iterator = list.iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			if(orderPreference.getLikeDegree() == 0){
				str.append(orderPreference.getProduct().getProductName() + "." );
			}
		}
		return str.toString();
	}
	
	private String toCouponStirng(List<Coupon> list){
		StringBuilder str = new StringBuilder();
		for (Iterator<Coupon> iterator = list.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			str.append(coupon.getCouponDesc() +  "," );
		}
		return str.toString();
	}
	
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Integer getShipmentCount() {
		return shipmentCount;
	}
	public void setShipmentCount(Integer shipmentCount) {
		this.shipmentCount = shipmentCount;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getPaymentModeName() {
		return paymentModeName;
	}
	public void setPaymentModeName(String paymentModeName) {
		this.paymentModeName = paymentModeName;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShipmentTime() {
		return shipmentTime;
	}
	public void setShipmentTime(String shipmentTime) {
		this.shipmentTime = shipmentTime;
	}
	public String getPreferences() {
		return preferences;
	}
	public void setPreferences(String preferences) {
		this.preferences = preferences;
	}
	public String getReceiveWay() {
		return receiveWay;
	}
	public void setReceiveWay(String receiveWay) {
		this.receiveWay = receiveWay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReceiverPostalCode() {
		return receiverPostalCode;
	}
	public void setReceiverPostalCode(String receiverPostalCode) {
		this.receiverPostalCode = receiverPostalCode;
	}
	public String getReceiverTowership() {
		return receiverTowership;
	}
	public void setReceiverTowership(String receiverTowership) {
		this.receiverTowership = receiverTowership;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverCellphone() {
		return receiverCellphone;
	}
	public void setReceiverCellphone(String receiverCellphone) {
		this.receiverCellphone = receiverCellphone;
	}
	public String getReceiverHousePhone() {
		return receiverHousePhone;
	}
	public void setReceiverHousePhone(String receiverHousePhone) {
		this.receiverHousePhone = receiverHousePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getProgramNum() {
		return programNum;
	}
	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}
	public String getCoupons() {
		return coupons;
	}
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}
	public Integer getShippingCost() {
		return shippingCost;
	}
	public void setShippingCost(Integer shippingCost) {
		this.shippingCost = shippingCost;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCustomerReponse() {
		return customerReponse;
	}
	public void setCustomerReponse(String customerReponse) {
		this.customerReponse = customerReponse;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getComingFrom() {
		return comingFrom;
	}
	public void setComingFrom(String comingFrom) {
		this.comingFrom = comingFrom;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getTowership() {
		return Towership;
	}
	public void setTowership(String Towership) {
		this.Towership = Towership;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getHousePhone() {
		return housePhone;
	}
	public void setHousePhone(String housePhone) {
		this.housePhone = housePhone;
	}
	public String getReceiptWay() {
		return receiptWay;
	}
	public void setReceiptWay(String receiptWay) {
		this.receiptWay = receiptWay;
	}
	public String getReceiptTitle() {
		return receiptTitle;
	}
	public void setReceiptTitle(String receiptTitle) {
		this.receiptTitle = receiptTitle;
	}
	public String getReceiptVatNumber() {
		return receiptVatNumber;
	}
	public void setReceiptVatNumber(String receiptVatNumber) {
		this.receiptVatNumber = receiptVatNumber;
	}
	public String getAllowForeignFruit() {
		return allowForeignFruit;
	}
	public void setAllowForeignFruit(String allowForeignFruit) {
		this.allowForeignFruit = allowForeignFruit;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUnlike() {
		return unlike;
	}

	public void setUnlike(String unlike) {
		this.unlike = unlike;
	}

	public Map<String, Object> getOrderExcelMap() {
		return orderExcelMap;
	}

	public void setOrderExcelMap(Map<String, Object> orderExcelMap) {
		this.orderExcelMap = orderExcelMap;
	}

}
