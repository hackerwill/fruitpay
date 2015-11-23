package com.fruitpay.comm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class SendToArrayList extends ArrayList<String> implements Serializable {
	
	private static final long serialVersionUID = 5337898146117445994L;
	
	public SendToArrayList(String... sendTo){
		super();
		addAll(sendTo);
	}

	@Override
	public boolean add(String sendTo) {
		return sendTo.indexOf(",") != -1 ? addAll(sendTo) : super.add(sendTo);
	}
	
	public boolean addAll(String... sendTo) {
		boolean successful = false;
		for (int i = 0; i < sendTo.length; i++) {
			successful = super.add(sendTo[i].trim());
			if(!successful) 
				return false;
		}
		
		return true;
	}
	
	public String parseSendToList() throws IllegalArgumentException{
		StringBuilder appendedSendToStr = new StringBuilder();
		if(this.isEmpty())
			throw new IllegalArgumentException("sendToList is empty : ");
		
		for (Iterator<String> iterator = this.iterator(); iterator.hasNext();) {
			String sendTo = iterator.next();
			appendedSendToStr.append(appendedSendToStr.length() == 0 ? sendTo  : "," + sendTo);
		}
		if(appendedSendToStr.length() == 0)
			throw new IllegalArgumentException(
					"sendToList Format is wrong : " + this.toArray(new String[this.size()]));
		
		return appendedSendToStr.toString();
	}
	
}
