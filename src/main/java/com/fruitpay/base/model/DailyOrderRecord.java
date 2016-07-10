package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;




/**
 * The persistent class for the PaymentStatus database table.
 * 
 */
@Entity
@NamedQuery(name="DailyOrderRecord.findAll", query="SELECT d FROM DailyOrderRecord d")
@Cacheable
public class DailyOrderRecord extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="daily_order_record_id")
	private Integer dailyOrderRecordId;

//	@Column(name="every_week_single_new")
//	private Integer everyWeekSingleNew;
//	
//	@Column(name="double_week_single_new")
//	private Integer doubleWeekSingleNew;
//	
//	@Column(name="every_week_family_new")
//	private Integer everyWeekFamilyNew;
//	
//	@Column(name="double_week_family_new")
//	private Integer doubleWeekFamilyNew;
//	
//	@Column(name="every_week_single_cancel")
//	private Integer everyWeekSingleCancel;
//	
//	@Column(name="double_week_single_cancel")
//	private Integer doubleWeekSingleCancel;
//	
//	@Column(name="every_week_family_cancel")
//	private Integer everyWeekFamilyCancel;
//	
//	@Column(name="double_week_family_cancel")
//	private Integer doubleWeekFamilyCancel;
//	
//	@Column(name="every_week_single_total")
//	private Integer everyWeekSingleTotal;
//	
//	@Column(name="double_week_single_total")
//	private Integer doubleWeekSingleTotal;
//	
//	@Column(name="every_week_family_total")
//	private Integer everyWeekFamilyTotal;
//	
//	@Column(name="double_week_family_total")
//	private Integer doubleWeekFamilyTotal;
	
	@Column(name="in_order_ids", length=4096, nullable=false)
	private String inOrderIds;
	
	@Column(name="count", nullable=false)
	private Integer count;
	
	@Column(name="order_date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderDate;
	
	@Column(name="date", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Integer getDailyOrderRecordId() {
		return dailyOrderRecordId;
	}

	public void setDailyOrderRecordId(Integer dailyOrderRecordId) {
		this.dailyOrderRecordId = dailyOrderRecordId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getInOrderIds() {
		return inOrderIds;
	}

	public void setInOrderIds(String inOrderIds) {
		this.inOrderIds = inOrderIds;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}