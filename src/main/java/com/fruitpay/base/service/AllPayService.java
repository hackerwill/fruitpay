package com.fruitpay.base.service;

import java.util.Map;

import com.fruitpay.base.model.Invoice;
import com.fruitpay.base.model.ShipmentRecordDetail;

public interface AllPayService {
	/**
	 * 向Allpay開立發票。
	 * @param invoice fruitpay內部發票。
	 * @return Map<String, String> allpay回傳參數
	 */
	public Map<String, String> postInvoice(Invoice invoice);
}
