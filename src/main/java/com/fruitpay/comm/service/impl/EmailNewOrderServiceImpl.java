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
		EmailComponent topComponent = new EmailComponent("BODY_TEMPLATE", "template/email/");
		EmailComponent secondContentComponent = new EmailComponent("CONTENT_TEMPLATE", "template/email/content/");
		topComponent.addChild(secondContentComponent);
		return topComponent;
	}

	@Override
	protected Map<String, String> getConditionMap(CustomerOrder order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("MESSAGE_TITLE", "測試抬頭");
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "新訂單成立通知";
	}

}
