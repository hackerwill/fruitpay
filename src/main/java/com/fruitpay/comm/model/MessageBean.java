package com.fruitpay.comm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageBean implements Serializable {
	
	private static final long serialVersionUID = 4937278256073083972L;
	
	private SendToArrayList sendToList;
	private String subject;
	private String text;
	
	public void setSendToList(SendToArrayList sendToList) {
		this.sendToList = sendToList;
	}
	
	public void setSendToList(String... emails) {
		sendToList = new SendToArrayList(emails);
	}

	public List<String> getSendToList() {
		return sendToList;
	}
	
	public String getSendToListStr() {
		return sendToList.parseSendToList();
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	

}
