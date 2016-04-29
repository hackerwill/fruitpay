package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.Customer;
import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailNewMemberFromWordpressServiceImpl extends EmailContentService<Customer> {

	@Override
	protected EmailComponent getEmailComponet() {
		EmailComponent topComponent = new EmailComponent("BODY_TEMPLATE", "template/email/BODY_TEMPLATE");
		EmailComponent secondContentComponent = new EmailComponent("CONTENT_TEMPLATE", "template/email/content/NEW_MEMBER_FROM_WORDPRESS_TEMPLATE");
		topComponent.addChild(secondContentComponent);
		return topComponent;
	}

	@Override
	protected Map<String, String> getConditionMap(Customer customer) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("MESSAGE_TITLE", "謝謝您對果物配的支持");
		map.put("CUSTOMER_EMAIL", customer.getEmail());
		map.put("CUSTOMER_LAST_NAME", customer.getLastName());
		map.put("CUSTOMER_FIRST_NAME", customer.getFirstName());
		map.put("CUSTOMER_PASSWORD", customer.getPassword());
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "果物配會員資料移轉通知";
	}

}
