package com.fruitpay.comm.model;

import java.io.Serializable;
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
	private Map<String, Object> orderExcelMap;
	
	public OrderExcelBean(CustomerOrder customerOrder){
		setOrderExcelMap(new HashMap<String,Object>());
		this.programId = String.valueOf(customerOrder.getOrderProgram().getProgramId());
		orderExcelMap.put(String.valueOf(Order.programId), programId);
		this.periodName = customerOrder.getShipmentPeriod().getPeriodName();
		orderExcelMap.put(String.valueOf(Order.periodName), periodName);
		this.shipmentCount = 0;
		orderExcelMap.put(String.valueOf(Order.shipmentCount), 0);
		this.programName = customerOrder.getOrderProgram().getProgramName();
		orderExcelMap.put(String.valueOf(Order.programName), programName);
		this.paymentModeName = customerOrder.getPaymentMode().getPaymentModeName();
		orderExcelMap.put(String.valueOf(Order.paymentModeName), paymentModeName);
		this.platformName = customerOrder.getOrderPlatform().getPlatformName();
		orderExcelMap.put(String.valueOf(Order.platformName), platformName);
		this.orderDate = customerOrder.getOrderDate();
		orderExcelMap.put(String.valueOf(Order.orderDate), orderDate);
		this.orderId = String.valueOf(customerOrder.getOrderId());
		orderExcelMap.put(String.valueOf(Order.orderId), orderId);
		this.shipmentTime = customerOrder.getShipmentTime().getOptionName();
		orderExcelMap.put(String.valueOf(Order.shipmentTime), shipmentTime);
		this.preferences = toPreferenceStirng(customerOrder.getOrderPreferences());
		orderExcelMap.put(String.valueOf(Order.preferences), preferences);
		this.receiveWay = customerOrder.getReceiveWay().getOptionName();
		orderExcelMap.put(String.valueOf(Order.receiveWay), receiveWay);
		this.remark = customerOrder.getRemark();
		orderExcelMap.put(String.valueOf(Order.remark), remark);
		this.receiverName = customerOrder.getReceiverLastName() + customerOrder.getReceiverFirstName();
		orderExcelMap.put(String.valueOf(Order.receiverName), receiverName);
		this.receiverPostalCode = customerOrder.getPostalCode().getFullName();
		orderExcelMap.put(String.valueOf(Order.receiverPostalCode), receiverPostalCode);
		this.receiverTowership = customerOrder.getPostalCode().getTowershipName();
		orderExcelMap.put(String.valueOf(Order.receiverTowership), receiverTowership);
		this.receiverAddress = customerOrder.getReceiverAddress();
		orderExcelMap.put(String.valueOf(Order.receiverAddress), receiverAddress);
		this.receiverCellphone = customerOrder.getReceiverCellphone();
		orderExcelMap.put(String.valueOf(Order.receiverCellphone), receiverCellphone);
		this.receiverHousePhone = customerOrder.getReceiverHousePhone();
		orderExcelMap.put(String.valueOf(Order.receiverHousePhone), receiverHousePhone);
		this.email = StringUtil.isEmptyOrSpace(customerOrder.getCustomer().getEmail())?"":customerOrder.getCustomer().getEmail();
		orderExcelMap.put(String.valueOf(Order.email), email);
		this.programNum = customerOrder.getProgramNum();
		orderExcelMap.put(String.valueOf(Order.programNum), programNum);
		this.coupons = toCouponStirng(customerOrder.getCoupons());
		orderExcelMap.put(String.valueOf(Order.coupons), coupons);
		this.shippingCost = customerOrder.getShippingCost();
		orderExcelMap.put(String.valueOf(Order.shippingCost), shippingCost);
		this.totalPrice = customerOrder.getTotalPrice();
		orderExcelMap.put(String.valueOf(Order.totalPrice), totalPrice);
		this.customerReponse = "";
		orderExcelMap.put(String.valueOf(Order.customerReponse), "");
		this.cancelReason = "";
		orderExcelMap.put(String.valueOf(Order.cancelReason), "");
		this.comingFrom = customerOrder.getComingFrom().getOptionName();
		orderExcelMap.put(String.valueOf(Order.comingFrom), comingFrom);
		this.name = customerOrder.getCustomer().getLastName() + customerOrder.getCustomer().getFirstName();
		orderExcelMap.put(String.valueOf(Order.name), name);
		this.postalCode = customerOrder.getPostalCode().getCountyName();
		orderExcelMap.put(String.valueOf(Order.postalCode), postalCode);
		this.Towership = customerOrder.getPostalCode().getTowershipName();
		orderExcelMap.put(String.valueOf(Order.Towership), Towership);
		this.address = customerOrder.getCustomer().getAddress();
		orderExcelMap.put(String.valueOf(Order.address), address);
		this.cellphone = customerOrder.getCustomer().getCellphone();
		orderExcelMap.put(String.valueOf(Order.cellphone), cellphone);
		this.housePhone = customerOrder.getCustomer().getHousePhone();
		orderExcelMap.put(String.valueOf(Order.housePhone), housePhone);
		this.receiptWay = customerOrder.getReceiptWay().getOptionName();
		orderExcelMap.put(String.valueOf(Order.receiptWay), receiptWay);
		this.receiptTitle = customerOrder.getReceiptTitle();
		orderExcelMap.put(String.valueOf(Order.receiptTitle), receiptTitle);
		this.receiptVatNumber = customerOrder.getReceiptVatNumber();
		orderExcelMap.put(String.valueOf(Order.receiptVatNumber), receiptVatNumber);
		this.allowForeignFruit = customerOrder.getAllowForeignFruits();
		orderExcelMap.put(String.valueOf(Order.allowForeignFruit), allowForeignFruit);
		this.orderStatus = customerOrder.getOrderStatus().getOrderStatusName();
		orderExcelMap.put(String.valueOf(Order.orderStatus), orderStatus);
	}
	
	private String toCouponStirng(List<Coupon> list){
		StringBuilder str = new StringBuilder();
		for (Iterator<Coupon> iterator = list.iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			str.append(coupon.getCouponDesc() +  "," );
		}
		return str.toString();
	}
	
	private String toPreferenceStirng(List<OrderPreference> list){
		StringBuilder str = new StringBuilder();
		for (Iterator<OrderPreference> iterator = list.iterator(); iterator.hasNext();) {
			OrderPreference orderPreference = iterator.next();
			if(orderPreference.getLikeDegree() != 3){
				str.append(orderPreference.getProduct().getProductName() + ":" + orderPreference.getLikeDegree() + "," );
			}
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

	public Map<String, Object> getOrderExcelMap() {
		return orderExcelMap;
	}

	public void setOrderExcelMap(Map<String, Object> orderExcelMap) {
		this.orderExcelMap = orderExcelMap;
	}

}
