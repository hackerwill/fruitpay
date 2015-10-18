package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailTestServiceImpl extends EmailContentService{
	
	@Override
	protected EmailComponent getEmailComponet() {
		EmailComponent topComponent = new EmailComponent("TEST_BODY", "test/TEST_BODY");
		EmailComponent secondContentComponent = new EmailComponent("TEST_CONTENT", "test/TEST_CONTENT");
		EmailComponent secondFooterComponent = new EmailComponent("TEST_FOOTER", "test/TEST_FOOTER");
		EmailComponent thirdBottomComponent = new EmailComponent("TEST_BOTTOM", "test/TEST_BOTTOM");
		secondFooterComponent.addChild(thirdBottomComponent);
		topComponent.addChild(secondContentComponent);
		topComponent.addChild(secondFooterComponent);
		return topComponent;
	}	
	
	/**
	 * 沒有輸入條件
	 * */
	@Override
	protected Map<String, String> getConditionMap(Object obj) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("HTML_TITLE", "TITLE");
		map.put("HTML_FOOTER", "CONTENT");
		map.put("HTML_HEADER", "HEADER");
		map.put("HTML_RANDOM", "RAMDOM");
		return map;
	}

	@Override
	protected String getEmailSubject() {
		return "測試使用";
	}

}
