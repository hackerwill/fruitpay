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
import com.fruitpay.comm.annotation.ColumnName;
import com.fruitpay.comm.utils.StringUtil;

public class OrderExcelBean extends AbstractExcelBean {
	
	@ColumnName("訂單狀態")
	private String orderStatus;	//訂單狀態
	@ColumnName("品名")
	private String programName; //品名
	@ColumnName("頻率")
	private String periodName;	//頻率
	@ColumnName("平台")
	private String platformName;	//平台
	@ColumnName("訂單日期")
	private Date orderDate;	//訂單日期
	@ColumnName("訂單編號")
	private String orderId;	//訂單編號
	@ColumnName("配送時段")
	private String shipmentTime; //配送時段
	@ColumnName("不吃水果")
	private String unlike; //不吃水果
	@ColumnName("收貨方式")
	private String receiveWay;	//收貨方式
	@ColumnName("備註")
	private String remark;	//備註
	@ColumnName("貨到付款")
	private String payOnReceive; //貨到付款
	@ColumnName("收件人名稱")
	private String receiverName;	//收件人名稱
	@ColumnName("收件人地址")
	private String receiverAddress;	//收件人地址
	@ColumnName("收件人手機")
	private String receiverCellphone;	//收件人手機
	@ColumnName("收件人電話")
	private String receiverHousePhone;	//收件人電話
	@ColumnName("信箱")
	private String email;	//信箱
	@ColumnName("總價")
	private Integer totalPrice; //總價
	@ColumnName("得知我們")
	private String comingFrom;	//得知我們
	@ColumnName("客戶回應")
	private String customerReponse;	//客戶回應(目前無)
	@ColumnName("訂購人")
	private String name;	//訂購人
	@ColumnName("訂購人手機")
	private String cellphone;	//訂購人手機
	@ColumnName("訂購人電話")
	private String housePhone;	//訂購人電話
	@ColumnName("優惠券")
	private String coupons;	//優惠券
	@ColumnName("收據類型")
	private String receiptType; //收據類型
	@ColumnName("抬頭")
	private String receiptTitle;	//抬頭
	@ColumnName("統一編號")
	private String receiptVatNumber;	//統一編號
	@ColumnName("出貨日")
	private String deliveryDay; //出貨日
	@ColumnName("數量")
	private Integer programNum;	//數量
	
	public OrderExcelBean() {
		super();
	}
	
	public OrderExcelBean(CustomerOrder customerOrder) {
		super();
		
		this.orderStatus = customerOrder.getOrderStatus().getOrderStatusName();
		
		this.programName = customerOrder.getOrderProgram().getProgramName();
		
		this.periodName = customerOrder.getShipmentPeriod().getPeriodName();
		
		this.platformName = customerOrder.getOrderPlatform().getPlatformName();
		
		this.orderDate = customerOrder.getOrderDate();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年M月dd日");
		
		this.orderId = String.valueOf(customerOrder.getOrderId());
		
		this.shipmentTime = String.valueOf(customerOrder.getShipmentTime().getOptionId() - 4); //轉成要的數字格式
		
		this.unlike = getUnlikeStr(customerOrder, customerOrder.getOrderPreferences());
		
		this.receiveWay = customerOrder.getReceiveWay().getOptionName();
		
		this.remark = customerOrder.getRemark();
		
		this.payOnReceive = getPayOnReceiveStr(customerOrder);
		
		this.receiverName = customerOrder.getReceiverLastName() + customerOrder.getReceiverFirstName();
		
		this.receiverAddress = customerOrder.getPostalCode().getFullName() + customerOrder.getReceiverAddress();
		
		this.receiverCellphone = customerOrder.getReceiverCellphone();
		
		this.receiverHousePhone = customerOrder.getReceiverHousePhone();
		
		this.email = StringUtil.isEmptyOrSpace(customerOrder.getCustomer().getEmail())?"":customerOrder.getCustomer().getEmail();
		
		this.totalPrice = customerOrder.getTotalPrice();
		
		this.comingFrom = customerOrder.getComingFrom().getOptionDesc();
		
		this.name = customerOrder.getCustomer().getLastName() + customerOrder.getCustomer().getFirstName();
		
		this.cellphone = customerOrder.getCustomer().getCellphone();
		
		this.housePhone = customerOrder.getCustomer().getHousePhone();
		
		this.customerReponse = "";
		
		this.coupons = toCouponStirng(customerOrder);
		
		this.receiptType = customerOrder.getReceiptWay().getOptionDesc();
		
		this.receiptVatNumber = customerOrder.getReceiptVatNumber();
		
		this.receiptTitle = customerOrder.getReceiptTitle();
		
		this.deliveryDay = customerOrder.getDeliveryDay().getOptionDesc();
		
		this.programNum = customerOrder.getProgramNum();
		
	}
	
	
	private String toCouponStirng(CustomerOrder customerOrder){
		StringBuilder str = new StringBuilder();
		for (Iterator<Coupon> iterator = customerOrder.getCoupons().iterator(); iterator.hasNext();) {
			Coupon coupon = iterator.next();
			str.append(coupon.getCouponDesc() + ".");
		}
		return str.toString();
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


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getProgramName() {
		return programName;
	}


	public void setProgramName(String programName) {
		this.programName = programName;
	}


	public String getPeriodName() {
		return periodName;
	}


	public void setPeriodName(String periodName) {
		this.periodName = periodName;
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


	public String getUnlike() {
		return unlike;
	}


	public void setUnlike(String unlike) {
		this.unlike = unlike;
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


	public String getPayOnReceive() {
		return payOnReceive;
	}


	public void setPayOnReceive(String payOnReceive) {
		this.payOnReceive = payOnReceive;
	}


	public String getReceiverName() {
		return receiverName;
	}


	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}


	public String getReceiverAddress() {
		return receiverAddress;
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


	public Integer getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}


	public String getComingFrom() {
		return comingFrom;
	}


	public void setComingFrom(String comingFrom) {
		this.comingFrom = comingFrom;
	}


	public String getCustomerReponse() {
		return customerReponse;
	}


	public void setCustomerReponse(String customerReponse) {
		this.customerReponse = customerReponse;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public String getCoupons() {
		return coupons;
	}


	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}


	public String getReceiptType() {
		return receiptType;
	}


	public void setReceiptType(String receiptType) {
		this.receiptType = receiptType;
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


	public String getDeliveryDay() {
		return deliveryDay;
	}


	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}


	public Integer getProgramNum() {
		return programNum;
	}


	public void setProgramNum(Integer programNum) {
		this.programNum = programNum;
	}

}
