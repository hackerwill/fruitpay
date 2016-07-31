package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Invoice;
import com.fruitpay.base.model.ShipmentRecordDetail;

public interface InvoiceService {
	
	/**
	 * 每個出貨明細都需要開立發票，新增一筆出貨明細的發票資料。
	 * @param shipmentRecordDetail 出貨明細
	 */
	public void add(ShipmentRecordDetail shipmentRecordDetail);
	
	/**
	 * 撈出Allpay尚未開立的發票
	 * @return List<Invoice> 尚未開立的發票
	 */
	public List<Invoice> findUnpostInvoice();
	
	/**
	 * 更新發票狀態為開立成功
	 * @param invoice 發票資訊
	 */
	public void updateInvoiceSuccessed(Invoice invoice);
	
	/**
	 * 更新發票狀態為開立失敗
	 * @param invoice 發票資訊
	 */
	public void updateInvoiceFailed(Invoice invoice);
}
