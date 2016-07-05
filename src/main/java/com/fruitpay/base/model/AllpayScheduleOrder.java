package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="AllpayScheduleOrder.findAll", query="SELECT a FROM AllpayScheduleOrder a")
@Cacheable(false)
public class AllpayScheduleOrder extends AbstractEntity implements Serializable {
	
	@Id
	@Column(name="allpay_schedule_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer allpayScheduleId;

	@Column(name="merchant_trade_no")
	private Integer merchantTradeNo;
	
	@Column(name="rtn_code")
	private Integer rtnCode;
	
	@Column(name="rtn_message")
	private String rtnMessage;
	
	@Column(name="period_type")
	private String periodType;
	
	@Column(name="frequency")
	private Integer frequency;
	
	@Column(name="exec_times")
	private Integer execTimes;
	
	@Column(name="amount")
	private Integer amount;
	
	@Column(name="gwsr")
	private Integer gwsr;
	
	@Column(name="process_date")
	private Date processDate;
	
	@Column(name="auth_code")
	private String authCode;
	
	@Column(name="first_auth_amount")
	private Double firstAuthAmount;
	
	@Column(name="total_success_times")
	private Integer totalSuccessTimes;

	public Integer getAllpayScheduleId() {
		return allpayScheduleId;
	}

	public void setAllpayScheduleId(Integer allpayScheduleId) {
		this.allpayScheduleId = allpayScheduleId;
	}

	public Integer getMerchantTradeNo() {
		return merchantTradeNo;
	}

	public void setMerchantTradeNo(Integer merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}

	public Integer getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(Integer rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMessage() {
		return rtnMessage;
	}

	public void setRtnMessage(String rtnMessage) {
		this.rtnMessage = rtnMessage;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getExecTimes() {
		return execTimes;
	}

	public void setExecTimes(Integer execTimes) {
		this.execTimes = execTimes;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getGwsr() {
		return gwsr;
	}

	public void setGwsr(Integer gwsr) {
		this.gwsr = gwsr;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Double getFirstAuthAmount() {
		return firstAuthAmount;
	}

	public void setFirstAuthAmount(Double firstAuthAmount) {
		this.firstAuthAmount = firstAuthAmount;
	}

	public Integer getTotalSuccessTimes() {
		return totalSuccessTimes;
	}

	public void setTotalSuccessTimes(Integer totalSuccessTimes) {
		this.totalSuccessTimes = totalSuccessTimes;
	}
	
	
}
