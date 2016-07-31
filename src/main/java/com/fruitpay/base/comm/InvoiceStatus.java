package com.fruitpay.base.comm;
/**
 * 發票狀態
 * @author Churu
 *
 */
public enum InvoiceStatus {
	/**
	 * 尚未開立
	 */
	invoiceUnpost,
	/**
	 * 作廢
	 */
	invoiceCancel,
	/**
	 * 開立成功
	 */
	invoiceSuccessed,
	/**
	 * 開立失敗
	 */
	invoiceFailed,
	
}
