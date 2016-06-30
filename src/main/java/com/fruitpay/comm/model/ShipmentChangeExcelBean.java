package com.fruitpay.comm.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
	@ColumnName("異動原因")
	private String reason;	//異動原因
	@ColumnName("付款方式")
	private String paymentMode;	//付款方式
	@ColumnName("下次配送日")
	private String nextShipmentDate;	//下次配送日
	@ColumnName("暫停次數")
	private String pauseTimes;	//暫停次數
	@ColumnName("全部暫停日期")
	private String pauseDates;	//暫停日期
	@ColumnName("修改人")
	private String updateUser;	//修改人
	@ColumnName("修改時間")
	private String updateDate;	//修改時間
	
	public ShipmentChangeExcelBean() {
		super();
	}
	
	public ShipmentChangeExcelBean(ShipmentChange shipmentChange, List<ShipmentChange> orderPauseChanges, LocalDate nextShipmentLocalDate) {
		super();
		
		this.orderId = String.valueOf(shipmentChange.getCustomerOrder().getOrderId());
		this.receiverName = String.valueOf(shipmentChange.getCustomerOrder().getReceiverLastName() + shipmentChange.getCustomerOrder().getReceiverFirstName());
		this.shipmentCount = String.valueOf(shipmentChange.getCustomerOrder().getShipmentCount() == null ? "" : shipmentChange.getCustomerOrder().getShipmentCount() );
		this.type = shipmentChange.getShipmentChangeType().getOptionDesc();
		this.applyDate = DateUtil.parseDate(shipmentChange.getApplyDate(), "yyyy年MM月dd日");
		this.updateUser = shipmentChange.getUpdateUserName();
		this.updateDate = DateUtil.parseDate(shipmentChange.getUpdateDate(), "yyyy年MM月dd日");
		this.reason = shipmentChange.getReason();
		this.paymentMode = shipmentChange.getCustomerOrder().getPaymentMode().getPaymentModeName();
		this.pauseTimes = String.valueOf(orderPauseChanges.size());
		this.pauseDates = formatPauseDates(orderPauseChanges);
		this.nextShipmentDate = nextShipmentLocalDate == null ? "已取消" : DateUtil.parseLocalDate(nextShipmentLocalDate, "yyyy年MM月dd日");
	}
	
	private String formatPauseDates(List<ShipmentChange> orderPauseChanges) {
		List<String> pauseDates = orderPauseChanges.stream().map(orderPauseChange -> {
			return DateUtil.parseDate(orderPauseChange.getApplyDate(), "yyyy年MM月dd日");
		}).collect(Collectors.toList());
		
		return String.join(System.lineSeparator(), pauseDates);
	}
	
}
