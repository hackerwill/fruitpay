package com.fruitpay.base.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fruitpay.base.comm.InvoiceStatus;
import com.fruitpay.base.dao.InvoiceDAO;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Invoice;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.service.InvoiceService;
import com.fruitpay.base.service.StaticDataService;

@Service
public class InvoiceServiceImpl implements InvoiceService{
	@Inject
	private InvoiceDAO invoiceDAO;
	@Inject
	private StaticDataService staticDataService;
	
	@Override
	public void add(ShipmentRecordDetail shipmentRecordDetail) {
		Invoice invoice = new Invoice();
		invoice.setRelateNumber(String.valueOf(new Date().getTime()));
		invoice.setShipmentRecordDetail(shipmentRecordDetail);
		invoice.setInvoiceStatus(staticDataService.getConstantOptionByName(InvoiceStatus.invoiceUnpost.toString()));
		invoiceDAO.save(invoice);
	}

	@Override
	public List<Invoice> findUnpostInvoice() {
		ConstantOption invoiceStatus = staticDataService.getConstantOptionByName(InvoiceStatus.invoiceUnpost.toString());
		return invoiceDAO.findByInvoiceStatus(invoiceStatus);
	}

	@Override
	public void updateInvoiceSuccessed(Invoice invoice) {
		ConstantOption invoiceStatus = staticDataService.getConstantOptionByName(InvoiceStatus.invoiceSuccessed.toString());
		invoice.setInvoiceStatus(invoiceStatus);
		invoiceDAO.save(invoice);
	}

	@Override
	public void updateInvoiceFailed(Invoice invoice) {
		 ConstantOption invoiceStatus = staticDataService.getConstantOptionByName(InvoiceStatus.invoiceFailed.toString());
		 invoice.setInvoiceStatus(invoiceStatus);
		 invoiceDAO.save(invoice);
	}

}
