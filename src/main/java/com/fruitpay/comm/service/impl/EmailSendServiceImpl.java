package com.fruitpay.comm.service.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fruitpay.comm.service.EmailContentService;
import com.fruitpay.comm.service.EmailSendService;
import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;

@Service
public class EmailSendServiceImpl implements EmailSendService {
	
	@Inject
	private EmailContentFactory emailContentFactory;
	@Inject
	private EmailConsumer emailConsumer;
	
	@PostConstruct
	public void init(){
		emailConsumer.runConsume();
	}

	@Override
	public <T> boolean sendTo(MailType maiType, String sendTo, T t){
		
		EmailContentService<T> emailContentervice = emailContentFactory.getEmailContentServiceImpl(maiType, t);
		emailConsumer.add(emailContentervice.getEmailMessageBean(t, sendTo));
		
		return true;
	}

}
