package com.fruitpay.comm.model;

import java.util.EnumMap;
import java.util.Map;



public class EnumMapOrder {

	public static Map<Order, String> getOrderColEnumMap() {
		Map<Order, String> map = new EnumMap<Order, String>(Order.class);

		map.put(Order.orderStatus, "訂單狀態");
		map.put(Order.programName, "品名");
		map.put(Order.periodName, "週期");
		map.put(Order.platformName, "平台");
		map.put(Order.orderDate, "訂單日期");
		map.put(Order.orderId, "訂單編號");
		map.put(Order.shipmentTime, "配送時間");
		map.put(Order.unlike, "不吃");
		map.put(Order.receiveWay, "收貨方式");
		map.put(Order.remark, "備註");
		map.put(Order.payOnReceive, "貨到付款");
		map.put(Order.receiverName, "收件人");
		map.put(Order.receiverAddress, "地址");
		map.put(Order.receiverCellphone, "手機");
		map.put(Order.receiverHousePhone, "電話");
		map.put(Order.email, "信箱");
		map.put(Order.totalPrice, "售價");
		map.put(Order.comingFrom, "得知我們");
		map.put(Order.customerReponse, "客戶回應");
		map.put(Order.name, "訂購人");
		map.put(Order.cellphone, "訂購人手機");
		map.put(Order.housePhone, "訂購人電話");
		
		return map;
	}

}
