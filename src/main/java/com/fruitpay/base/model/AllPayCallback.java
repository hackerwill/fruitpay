package com.fruitpay.base.model;

import java.io.Serializable;

public class AllPayCallback  implements Serializable {
	
	String szMerchantID = "";
	String szPaymentDate = "";
	String szPaymentType = "";
	String szPaymentTypeChargeFee = "";
	String szRtnCode = "";
	String szRtnMsg = "";
	String szSimulatePaid = "";
	String szTradeAmt = "";
	String szTradeDate = "";
	String szTradeNo = ""; /* 使用定期定額交易時，回傳的額外參數 */
	String szPeriodType = "";
	String szFrequency = "";
	String szExecTimes = "";
	String szAmount = "";
	String szGwsr = "";
	String szProcessDate = "";
	String szAuthCode = "";
	String szFirstAuthAmount = "";
	String szTotalSuccessTimes = ""; // 取得資料
	public String getSzMerchantID() {
		return szMerchantID;
	}
	public void setSzMerchantID(String szMerchantID) {
		this.szMerchantID = szMerchantID;
	}
	public String getSzPaymentDate() {
		return szPaymentDate;
	}
	public void setSzPaymentDate(String szPaymentDate) {
		this.szPaymentDate = szPaymentDate;
	}
	public String getSzPaymentType() {
		return szPaymentType;
	}
	public void setSzPaymentType(String szPaymentType) {
		this.szPaymentType = szPaymentType;
	}
	public String getSzPaymentTypeChargeFee() {
		return szPaymentTypeChargeFee;
	}
	public void setSzPaymentTypeChargeFee(String szPaymentTypeChargeFee) {
		this.szPaymentTypeChargeFee = szPaymentTypeChargeFee;
	}
	public String getSzRtnCode() {
		return szRtnCode;
	}
	public void setSzRtnCode(String szRtnCode) {
		this.szRtnCode = szRtnCode;
	}
	public String getSzRtnMsg() {
		return szRtnMsg;
	}
	public void setSzRtnMsg(String szRtnMsg) {
		this.szRtnMsg = szRtnMsg;
	}
	public String getSzSimulatePaid() {
		return szSimulatePaid;
	}
	public void setSzSimulatePaid(String szSimulatePaid) {
		this.szSimulatePaid = szSimulatePaid;
	}
	public String getSzTradeAmt() {
		return szTradeAmt;
	}
	public void setSzTradeAmt(String szTradeAmt) {
		this.szTradeAmt = szTradeAmt;
	}
	public String getSzTradeDate() {
		return szTradeDate;
	}
	public void setSzTradeDate(String szTradeDate) {
		this.szTradeDate = szTradeDate;
	}
	public String getSzTradeNo() {
		return szTradeNo;
	}
	public void setSzTradeNo(String szTradeNo) {
		this.szTradeNo = szTradeNo;
	}
	public String getSzPeriodType() {
		return szPeriodType;
	}
	public void setSzPeriodType(String szPeriodType) {
		this.szPeriodType = szPeriodType;
	}
	public String getSzFrequency() {
		return szFrequency;
	}
	public void setSzFrequency(String szFrequency) {
		this.szFrequency = szFrequency;
	}
	public String getSzExecTimes() {
		return szExecTimes;
	}
	public void setSzExecTimes(String szExecTimes) {
		this.szExecTimes = szExecTimes;
	}
	public String getSzAmount() {
		return szAmount;
	}
	public void setSzAmount(String szAmount) {
		this.szAmount = szAmount;
	}
	public String getSzGwsr() {
		return szGwsr;
	}
	public void setSzGwsr(String szGwsr) {
		this.szGwsr = szGwsr;
	}
	public String getSzProcessDate() {
		return szProcessDate;
	}
	public void setSzProcessDate(String szProcessDate) {
		this.szProcessDate = szProcessDate;
	}
	public String getSzAuthCode() {
		return szAuthCode;
	}
	public void setSzAuthCode(String szAuthCode) {
		this.szAuthCode = szAuthCode;
	}
	public String getSzFirstAuthAmount() {
		return szFirstAuthAmount;
	}
	public void setSzFirstAuthAmount(String szFirstAuthAmount) {
		this.szFirstAuthAmount = szFirstAuthAmount;
	}
	public String getSzTotalSuccessTimes() {
		return szTotalSuccessTimes;
	}
	public void setSzTotalSuccessTimes(String szTotalSuccessTimes) {
		this.szTotalSuccessTimes = szTotalSuccessTimes;
	}

}
