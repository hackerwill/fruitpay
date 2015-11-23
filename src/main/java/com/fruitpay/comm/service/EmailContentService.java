package com.fruitpay.comm.service;

import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fruitpay.comm.model.EmailComponent;
import com.fruitpay.comm.model.MessageBean;

public abstract class EmailContentService<T> {
	
	private final String PREFIX = "${";
	private final String SUFFIX = "}";
	
	/**
	 * 得到要使用的MessageBean
	 * */
	public MessageBean getEmailMessageBean(T t, String... emails){
		MessageBean messageBean = new MessageBean();
		messageBean.setSendToList(emails);
		messageBean.setSubject(getEmailSubject());		
		messageBean.setText(getEmailContent(t));
		return messageBean;
	};
	
	/**
	 * 得到要使用的信件內容
	 * */
	private String getEmailContent(T t){
		return replace(getEmailComponet().buildTemplate(), getConditionMap(t));
	}
	
	/**
	 * 設定信件標題
	 * */
	protected abstract String getEmailSubject();
	
	/**
	 * 設定模板的階層關係
	 * */
	protected abstract EmailComponent getEmailComponet();
	
	/**
	 * 得到取代的條件map
	 * */
	protected abstract Map<String, String> getConditionMap(T t);
	
	/**
	 * 將字串合併為單一份文件
	 * */
	private String replace(String content, Map<String,String> map){
		StrSubstitutor sub = new StrSubstitutor(map, PREFIX, SUFFIX);
		return sub.replace(content);
	};

}
