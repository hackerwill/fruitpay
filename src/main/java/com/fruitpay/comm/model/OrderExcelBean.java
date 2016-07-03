package com.fruitpay.comm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.comm.annotation.ColumnName;
import com.fruitpay.comm.utils.DateUtil;
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
	private String orderDate;	//訂單日期
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
	@ColumnName("配送次數")
	private Integer shipmentCount;	//配送次數
	
	public OrderExcelBean() {
		super();
	}
	
	public OrderExcelBean(CustomerOrder customerOrder) {
		super();
		
		this.orderStatus = customerOrder.getOrderStatus().getOrderStatusName();
		
		this.programName = customerOrder.getOrderProgram().getProgramName();
		
		this.periodName = customerOrder.getShipmentPeriod().getPeriodName();
		
		this.platformName = customerOrder.getOrderPlatform().getPlatformName();
		

		this.orderDate = DateUtil.parseDate(customerOrder.getOrderDate(), "yyyy年M月dd日 HH點mm分");
		
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
		
		this.shipmentCount = customerOrder.getShipmentCount();
		
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

}
