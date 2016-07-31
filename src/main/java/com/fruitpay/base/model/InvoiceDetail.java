package com.fruitpay.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="InvoiceDetail.findAll",query="SELECT c FROM InvoiceDetail c")
public class InvoiceDetail {
	
	@Id
	@Column(name="invoice_detail_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer invoiceDetailId;
	
	@ManyToOne
	@JoinColumn(name="invoice_id")
	private Invoice invoice;
	
	@Column(name="item_name", nullable=false)
	private String itemName;
	
	@Column(name="item_count", nullable=false)
	private Integer itemCount;
	
	@Column(name="item_word", nullable=false)
	private String itemWord;
	
	@Column(name="item_price", nullable=false)
	private Integer itemPrice;
	
	@Column(name="item_remark")
	private String itemRemark;

	public Integer getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(Integer invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemWord() {
		return itemWord;
	}

	public void setItemWord(String itemWord) {
		this.itemWord = itemWord;
	}

	public Integer getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Integer itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}
	
}
