package com.fruitpay.comm.model;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderComment;
import com.fruitpay.base.model.OrderPreference;
import com.fruitpay.comm.annotation.ColumnName;
import com.fruitpay.comm.utils.DateUtil;

public class ShipmentExcelBean extends AbstractExcelBean {
	
	@ColumnName("收件人名稱")
	private String receiverName;	//收件人名稱
	@ColumnName("收件人地址")
	private String receiverAddress;	//收件人地址
	@ColumnName("收件人手機")
	private String receiverCellphone;	//收件人手機
	@ColumnName("收件人電話")
	private String receiverHousePhone;	//收件人電話
	@ColumnName("出貨日")
	private String shipmentDay; //出貨日
	@ColumnName("到貨日")
	private String deliveryDay; //到貨日
	@ColumnName("品名")
	private String programName; //品名
	@ColumnName("備註")
	private String remark;	//備註
	@ColumnName("溫層")
	private String temperate;	//溫層
	@ColumnName("規格")
	private String spec;	//規格
	@ColumnName("配送時段")
	private String shipmentTime; //配送時段
	@ColumnName("貨到付款")
	private String payOnReceive; //貨到付款
	@ColumnName("不吃水果")
	private String unlike; //不吃水果
	@ColumnName("數量")
	private Integer programNum;	//數量
	@ColumnName("售價")
	private Integer totalPrice; //售價
	@ColumnName("註解")
	private String comments; //註解
	
	public ShipmentExcelBean() {
		super();
	}
	
	public ShipmentExcelBean(CustomerOrder customerOrder, LocalDate shipmentDate, LocalDate deliveryDate) {
		super();
		try {
		this.shipmentTime = String.valueOf(customerOrder.getShipmentTime().getOptionId() - 4); //轉成要的數字格式
		
		this.unlike = getUnlikeStr(customerOrder, customerOrder.getOrderPreferences());
		
		this.remark = customerOrder.getRemark();
		
		this.payOnReceive = getPayOnReceiveStr(customerOrder);
		
		this.receiverName = customerOrder.getReceiverLastName().trim() + customerOrder.getReceiverFirstName().trim();
		
		this.receiverAddress = customerOrder.getPostalCode().getFullName() + customerOrder.getReceiverAddress();
		
		this.receiverCellphone = customerOrder.getReceiverCellphone();
		
		this.receiverHousePhone = customerOrder.getReceiverHousePhone();
		
		this.totalPrice = customerOrder.getTotalPrice();
		
		this.deliveryDay = customerOrder.getDeliveryDay().getOptionDesc();
		
		this.programNum = customerOrder.getProgramNum();
		
		this.programName = customerOrder.getOrderProgram().getProgramName() + "(" +  customerOrder.getShipmentPeriod().getPeriodName() + ")";
		
		this.shipmentDay = DateUtil.parseLocalDate(shipmentDate, "dd/MM/yyyy");
		
		this.deliveryDay = DateUtil.parseLocalDate(deliveryDate, "dd/MM/yyyy");
		
		this.temperate = "2";
		
		this.spec = "1";
		
		this.comments = customerOrder.getOrderComments() == null ? "" : getComments(customerOrder.getOrderComments());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private String getComments(List<OrderComment> orderComments) {
		return orderComments.stream()
				.map(orderComment -> orderComment.getComment())
				.reduce("", (a, b) -> a.length() > 0 ? a + "," + b : b);
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
