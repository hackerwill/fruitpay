package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailNewMemberFromOrderServiceImpl extends EmailContentService<Customer> {

	@Override
	protected EmailComponent getEmailComponet() {
		EmailComponent topComponent = new EmailComponent("BODY_TEMPLATE", "template/email/BODY_TEMPLATE");
		EmailComponent secondContentComponent = new EmailComponent("CONTENT_TEMPLATE", "template/email/content/NEW_MEMBER_FROM_ORDER_TEMPLATE");
		topComponent.addChild(secondContentComponent);
		return topComponent;
	}

	@Override
	protected Map<String, String> getConditionMap(Customer customer) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("MESSAGE_TITLE", "歡迎您加入果物配");
		map.put("FIRST_NAME", customer.getFirstName());
		map.put("LAST_NAME", customer.getLastName());
		map.put("EMAIL", customer.getEmail());
		map.put("PASSWORD", customer.getPassword());
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "果物配新會員帳號密碼通知";
	}

}
