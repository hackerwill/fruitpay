package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name="Invoice.findAll", query="SELECT c FROM Invoice c")
public class Invoice extends AbstractEntity implements Serializable{
	
	@Id
	@Column(name="invoice_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	private Integer invoiceId;
	
	@Column(name="relate_number")
	private String relateNumber;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	private CustomerOrder customerOrder;
	
	@OneToOne
	@JoinColumn(name="shipment_record_detail_id")
	private ShipmentRecordDetail shipmentRecordDetail;
	
	@ManyToOne
	@JoinColumn(name="invoice_status")
	private ConstantOption invoiceStatus;
	
	@OneToMany(mappedBy="invoice", cascade={CascadeType.ALL})
	private List<InvoiceDetail> invoiceDetail;

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getRelateNumber() {
		return relateNumber;
	}

	public void setRelateNumber(String relateNumber) {
		this.relateNumber = relateNumber;
	}

	public ShipmentRecordDetail getShipmentRecordDetail() {
		return shipmentRecordDetail;
	}

	public void setShipmentRecordDetail(ShipmentRecordDetail shipmentRecordDetail) {
		this.shipmentRecordDetail = shipmentRecordDetail;
	}

	public ConstantOption getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(ConstantOption invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public List<InvoiceDetail> getInvoiceDetail() {
		return invoiceDetail;
	}

	public void setInvoiceDetail(List<InvoiceDetail> invoiceDetail) {
		this.invoiceDetail = invoiceDetail;
	}
	
	
}
