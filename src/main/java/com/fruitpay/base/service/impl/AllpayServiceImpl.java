package com.fruitpay.base.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import org.springframework.stereotype.Service;

import com.fruitpay.allpayInvoice.machine.InvoiceMachine;
import com.fruitpay.allpayInvoice.machine.MachineFactory;
import com.fruitpay.allpayInvoice.machine.MachineType;
import com.fruitpay.allpayInvoice.model.AllpayInvoice;
import com.fruitpay.allpayInvoice.util.AllpayURLEncoder;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.Invoice;
import com.fruitpay.base.model.InvoiceDetail;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.ShipmentRecordDetail;
import com.fruitpay.base.service.AllPayService;

@Service
public class AllpayServiceImpl implements AllPayService {

	private final String TEST_HASH_KEY = "ejCk326UnaZWKisg";
	private final String TEST_HASH_IV = "q9jcZX8Ib9LM8wYk";
	private final String TEST_MERCHANT_ID = "2000132";

	@Override
	public Map<String, String> postInvoice(Invoice invoice) {
		
		Map<String, String>  response = new LinkedHashMap<String, String>();
		
		InvoiceMachine invoiceMachine = MachineFactory.getInvoiceMachine(MachineType.CREATE)
				.setHashIV(TEST_HASH_IV)
				.setHashKey(TEST_HASH_KEY)
				.setMerchantId(TEST_MERCHANT_ID);
		
		ShipmentRecordDetail shipmentRecordDetail = invoice.getShipmentRecordDetail();
		CustomerOrder customerOrder = shipmentRecordDetail.getCustomerOrder();
		Customer customer = customerOrder.getCustomer();
		List<InvoiceDetail> invoiceDetails = invoice.getInvoiceDetail();
		int salesAmount = invoiceDetails
				.stream()
				.mapToInt(i -> i.getItemCount() * i.getItemPrice())
				.sum();
		
		AllpayInvoice allpayInvoice = new AllpayInvoice()
				.setRelateNumber(invoice.getRelateNumber())
				.setTaxType(AllpayInvoice.TaxTypeEnum.DUTY_FREE)
				.setSalesAmount(salesAmount)
				.setTimeStamp(new Date());
		
		allpayInvoice.getCustomer()
			.setCustomerName(customer.getLastName() + customer.getFirstName())
			.setCustomerAddr(customer.getAddress())
			.setCustomerEmail(customer.getEmail())
			.setCustomerId(customer.getCustomerId().toString())
			.setCustomerPhone(customer.getCellphone());
		
		for(InvoiceDetail invoiceDetail : invoiceDetails){
			allpayInvoice.createItem()
				.setItemName(invoiceDetail.getItemName())
				.setItemCount(invoiceDetail.getItemCount())
				.setItemPrice(invoiceDetail.getItemPrice())
				.setItemWord(invoiceDetail.getItemWord())
				.setItemRemark(invoiceDetail.getItemRemark());
		}
		
		try {
			response.putAll(invoiceMachine.post(allpayInvoice));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
