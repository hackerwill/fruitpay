package com.fruitpay.base.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {
	public List<Invoice> findByInvoiceStatus(ConstantOption invoiceStatus);

}
