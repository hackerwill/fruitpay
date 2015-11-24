package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailContentFactory {
	
	public enum MailType{
		NEW_ORDER,
		NEW_ORDER_NOTICE,
		NEW_MEMBER;
	}
	
	Map<MailType, EmailContentService> map= null;
	@Inject
	EmailNewMemberServiceImpl emailNewMemberServiceImpl;
	@Inject
	EmailNewOrderServiceImpl emailNewOrderServiceImpl;
	@Inject
	EmailNewOrderNoticeServiceImpl emailNewOrderNoticeServiceImpl;
	
	
	@PostConstruct
	public void init(){
		map = new HashMap<MailType, EmailContentService>();
		map.put(MailType.NEW_MEMBER, emailNewMemberServiceImpl);
		map.put(MailType.NEW_ORDER, emailNewOrderServiceImpl);
		map.put(MailType.NEW_ORDER_NOTICE, emailNewOrderNoticeServiceImpl);
	}
	
	public <T> EmailContentService<T> getEmailContentServiceImpl(MailType mailType, T t){
		EmailContentService<T> emailContentService = map.get(mailType);
		return emailContentService;
	}
}
