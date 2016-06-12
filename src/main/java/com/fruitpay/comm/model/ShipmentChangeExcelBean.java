package com.fruitpay.comm.model;

import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.comm.annotation.ColumnName;
import com.fruitpay.comm.utils.DateUtil;

public class ShipmentChangeExcelBean extends AbstractExcelBean {
	
	@ColumnName("申請日期")
	private String applyDate;	//日期
	@ColumnName("訂單編號")
	private String orderId;	//訂單編號
	@ColumnName("收件人")
	private String receiverName; //收件人
	@ColumnName("已配送次數")
	private String shipmentCount;	//已配送次數
	@ColumnName("類型")
	private String type;	//類型
	@ColumnName("修改人")
	private String updateUser;	//修改人
	@ColumnName("修改時間")
	private String updateDate;	//修改時間

	public ShipmentChangeExcelBean() {
		super();
	}
	
	public ShipmentChangeExcelBean(ShipmentChange shipmentChange) {
		super();
		
		this.orderId = String.valueOf(shipmentChange.getCustomerOrder().getOrderId());
		this.receiverName = String.valueOf(shipmentChange.getCustomerOrder().getReceiverLastName() + shipmentChange.getCustomerOrder().getReceiverFirstName());
		this.shipmentCount = String.valueOf(shipmentChange.getCustomerOrder().getShipmentCount() == null ? "" : shipmentChange.getCustomerOrder().getShipmentCount() );
		this.type = shipmentChange.getShipmentChangeType().getOptionDesc();
		this.applyDate = DateUtil.parseDate(shipmentChange.getApplyDate(), "yyyy年MM月dd日");
		this.updateUser = shipmentChange.getUpdateUserName();
		this.updateDate = DateUtil.parseDate(shipmentChange.getUpdateDate(), "yyyy年MM月dd日");
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getShipmentCount() {
		return shipmentCount;
	}

	public void setShipmentCount(String shipmentCount) {
		this.shipmentCount = shipmentCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
}
