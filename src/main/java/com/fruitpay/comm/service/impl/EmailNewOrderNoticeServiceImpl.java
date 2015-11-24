package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailNewOrderNoticeServiceImpl extends EmailContentService<CustomerOrder> {

	@Override
	protected EmailComponent getEmailComponet() {
		EmailComponent topComponent = new EmailComponent("BODY_TEMPLATE", "template/email/BODY_TEMPLATE");
		EmailComponent secondContentComponent = new EmailComponent("CONTENT_TEMPLATE", "template/email/content/NEW_ORDER_NOTICE_TEMPLATE");
		topComponent.addChild(secondContentComponent);
		return topComponent;
	}

	@Override
	protected Map<String, String> getConditionMap(CustomerOrder order) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("MESSAGE_TITLE", "注意事項");
		map.put("FIRST_NAME", order.getCustomer().getFirstName());
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "果物配訂單注意事項";
	}

}
