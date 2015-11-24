package com.fruitpay.comm.service;

import com.fruitpay.comm.service.impl.EmailContentFactory.MailType;

public interface EmailSendService {
	
	public <T> boolean sendTo(MailType maiType, String sendTo, T t);

}
