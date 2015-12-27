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
		NEW_MEMBER_FROM_ORDER,
		NEW_MEMBER,
		FORGET_PASSWORD;
	}
	
	Map<MailType, EmailContentService> map= null;
	@Inject
	EmailNewMemberServiceImpl emailNewMemberServiceImpl;
	@Inject
	EmailNewOrderServiceImpl emailNewOrderServiceImpl;
	@Inject
	EmailForgetPasswordServiceImpl emailForgetPasswordServiceImpl;
	@Inject
	EmailNewMemberFromOrderServiceImpl emailNewMemberFromOrderServiceImpl;
	
	
	@PostConstruct
	public void init(){
		map = new HashMap<MailType, EmailContentService>();
		map.put(MailType.NEW_MEMBER, emailNewMemberServiceImpl);
		map.put(MailType.NEW_MEMBER_FROM_ORDER, emailNewMemberFromOrderServiceImpl);
		map.put(MailType.NEW_ORDER, emailNewOrderServiceImpl);
		map.put(MailType.FORGET_PASSWORD, emailForgetPasswordServiceImpl);
	}
	
	public <T> EmailContentService<T> getEmailContentServiceImpl(MailType mailType, T t){
		EmailContentService<T> emailContentService = map.get(mailType);
		return emailContentService;
	}
}
