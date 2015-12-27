package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailNewOrderServiceImpl extends EmailContentService<CustomerOrder> {

	@Override
	protected EmailComponent getEmailComponet() {
		EmailComponent topComponent = new EmailComponent("BODY_TEMPLATE", "template/email/BODY_TEMPLATE");
		EmailComponent secondContentComponent = new EmailComponent("CONTENT_TEMPLATE", "template/email/content/NEW_ORDER_TEMPLATE");
		topComponent.addChild(secondContentComponent);
		return topComponent;
	}

	@Override
	protected Map<String, String> getConditionMap(CustomerOrder order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("MESSAGE_TITLE", "您的訂單內容");
		map.put("FIRST_NAME", order.getCustomer().getFirstName());
		map.put("ORDER_ID", String.valueOf(order.getOrderId()));
		map.put("ORDER_DATE", order.getOrderDate().toString());
		map.put("ORDER_PROGRAM_NAME", order.getOrderProgram().getProgramName());
		map.put("TOTAL_PRICE", String.valueOf(order.getOrderProgram().getPrice() + order.getPaymentMode().getPaymentExtraPrice()));
		map.put("PRICE", String.valueOf(order.getOrderProgram().getPrice()));
		map.put("PAYMENT_MODE_NAME", order.getPaymentMode().getPaymentModeName());
		map.put("PAYMENT_EXTRA_PRICE", String.valueOf(order.getPaymentMode().getPaymentExtraPrice()));
		map.put("EMAIL", order.getCustomer().getEmail());
		map.put("RECEIVER_CELLPHONE", order.getCustomer().getCellphone() );
		map.put("RECEIVER_HOUSE_PHONE", order.getCustomer().getHousePhone());
		map.put("RECEIVER_LAST_NAME", order.getReceiverLastName());
		map.put("RECEIVER_FIRST_NAME", order.getReceiverFirstName());
		map.put("RECEIVER_ADDRESS", order.getPostalCode().toString() + order.getReceiverAddress());
		
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "新訂單成立通知";
	}

}
