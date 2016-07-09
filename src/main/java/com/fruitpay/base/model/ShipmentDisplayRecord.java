package com.fruitpay.base.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class ShipmentDisplayRecord implements Serializable {
	
	private Date date;
	private Date cachedDate;
	private int total;
	private int familyTotal;
	private int singleTotal;
	private int familyAndEveryWeekTotal;
	private int familyAndTwoWeekTotal;
	private int singleAndEveryWeekTotal;
	private int singleAndTwoWeekTotal;

	public ShipmentDisplayRecord() {
		super();
	}
	
	public ShipmentDisplayRecord(Date date, Date cachedDate, int total, int familyTotal, int singleTotal, int familyAndEveryWeekTotal,
			int familyAndTwoWeekTotal, int singleAndEveryWeekTotal, int singleAndTwoWeekTotal) {
		super();
		this.date = date;
		this.cachedDate = cachedDate;
		this.total = total;
		this.familyAndEveryWeekTotal = familyAndEveryWeekTotal;
		this.familyAndTwoWeekTotal = familyAndTwoWeekTotal;
		this.singleAndEveryWeekTotal = singleAndEveryWeekTotal;
		this.singleAndTwoWeekTotal = singleAndTwoWeekTotal;
		this.familyTotal = familyTotal;
		this.singleTotal = singleTotal;
	}

	public int getFamilyTotal() {
		return familyTotal;
	}

	public void setFamilyTotal(int familyTotal) {
		this.familyTotal = familyTotal;
	}

	public int getSingleTotal() {
		return singleTotal;
	}

	public void setSingleTotal(int singleTotal) {
		this.singleTotal = singleTotal;
	}

	public int getFamilyAndEveryWeekTotal() {
		return familyAndEveryWeekTotal;
	}

	public void setFamilyAndEveryWeekTotal(int familyAndEveryWeekTotal) {
		this.familyAndEveryWeekTotal = familyAndEveryWeekTotal;
	}

	public int getFamilyAndTwoWeekTotal() {
		return familyAndTwoWeekTotal;
	}

	public void setFamilyAndTwoWeekTotal(int familyAndTwoWeekTotal) {
		this.familyAndTwoWeekTotal = familyAndTwoWeekTotal;
	}

	public int getSingleAndEveryWeekTotal() {
		return singleAndEveryWeekTotal;
	}

	public void setSingleAndEveryWeekTotal(int singleAndEveryWeekTotal) {
		this.singleAndEveryWeekTotal = singleAndEveryWeekTotal;
	}

	public int getSingleAndTwoWeekTotal() {
		return singleAndTwoWeekTotal;
	}

	public void setSingleAndTwoWeekTotal(int singleAndTwoWeekTotal) {
		this.singleAndTwoWeekTotal = singleAndTwoWeekTotal;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getCachedDate() {
		return cachedDate;
	}
	public void setCachedDate(Date cachedDate) {
		this.cachedDate = cachedDate;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
