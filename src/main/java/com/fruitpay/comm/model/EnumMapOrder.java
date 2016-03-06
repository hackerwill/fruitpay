package com.fruitpay.comm.model;

import java.util.EnumMap;
import java.util.Map;



public class EnumMapOrder {

	public static Map<Order, String> getOrderColEnumMap() {
		Map<Order, String> map = new EnumMap<Order, String>(Order.class);

		map.put(Order.programId, "方案名稱");
		map.put(Order.periodName, "頻率");
		map.put(Order.shipmentCount, "配送次數(目前無)");
		map.put(Order.programName, "品名");
		map.put(Order.paymentModeName, "付款方式");
		map.put(Order.platformName, "平台");
		map.put(Order.orderDate, "訂單日期");
		map.put(Order.orderId, "訂單編號");
		map.put(Order.shipmentTime, "配送時段");
		map.put(Order.preferences, "水果偏好");
		map.put(Order.receiveWay, "收貨方式");
		map.put(Order.remark, "備註");
		map.put(Order.receiverName, "收件人名稱");
		map.put(Order.receiverPostalCode, "收件人區郵遞區號");
		map.put(Order.receiverTowership, "收件人區域");
		map.put(Order.receiverAddress, "收件人地址");
		map.put(Order.receiverCellphone, "收件人手機");
		map.put(Order.receiverHousePhone, "收件人電話");
		map.put(Order.email, "信箱");
		map.put(Order.programNum, "數量");
		map.put(Order.coupons, "優惠券");
		map.put(Order.shippingCost, "運費");
		map.put(Order.totalPrice, "總價");
		map.put(Order.customerReponse, "客戶回應(目前無)");
		map.put(Order.cancelReason, "取消原因(目前無)");
		map.put(Order.comingFrom, "得知我們");
		map.put(Order.name, "訂購人");
		map.put(Order.postalCode, "訂購人郵遞區號");
		map.put(Order.Towership, "訂購人區域");
		map.put(Order.address, "訂購人地址");
		map.put(Order.cellphone, "訂購人手機");
		map.put(Order.housePhone, "訂購人電話");
		map.put(Order.receiptWay, "發票方式");
		map.put(Order.receiptTitle, "抬頭");
		map.put(Order.receiptVatNumber, "統一編號");
		map.put(Order.allowForeignFruit, "是否要進口水果");
		map.put(Order.orderStatus, "訂單狀態");

		return map;
	}

}
