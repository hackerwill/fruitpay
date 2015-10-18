package com.fruitpay.comm.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fruitpay.comm.service.EmailContentService;

@Component
public class EmailContentFactory {
	
	public enum MailType{
		NEW_ORDER,
		NEW_MEMBER;
	}
	
	Map<MailType, EmailContentService> map= null;
	@Autowired
	EmailNewMemberServiceImpl emailNewMemberServiceImpl;
	@Autowired
	EmailNewOrderServiceImpl emailNewOrderServiceImpl;
	
	
	@PostConstruct
	public void init(){
		map = new HashMap<MailType, EmailContentService>();
		map.put(MailType.NEW_MEMBER, emailNewMemberServiceImpl);
		map.put(MailType.NEW_ORDER, emailNewOrderServiceImpl);
	}
	
	public <T> EmailContentService<T> getEmailContentServiceImpl(MailType mailType, T t){
		EmailContentService<T> emailContentService = map.get(mailType);
		return emailContentService;
	}
}
