package com.fruitpay.comm.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.lang3.text.StrSubstitutor;

import com.fruitpay.comm.utils.FileReadUtil;

public class EmailComponent implements Serializable {
	
	private final String PREFIX = "${";
	private final String SUFFIX = "}";
	private final String HTML_SUFFIX = ".html";
	
	private String templateName = null;
	private String template = null;
	private EmailComponent parentComponet = null;
	private Queue<EmailComponent> childComponents = null;
	
	public EmailComponent(String templateName){
		setConstructor(templateName, templateName);
	}
	
	public EmailComponent(String templateName, String filePath){
		setConstructor(templateName, filePath);
	}
	
	private void setConstructor(String templateName, String filePath){
		this.templateName = templateName;
		this.template = FileReadUtil.getResourceFile(
				filePath + HTML_SUFFIX);
	}
	
	public void addChild(EmailComponent childComponent){
		if(childComponents == null){
			childComponents = new LinkedList<EmailComponent>();
		}
		childComponents.add(childComponent);
		childComponent.setParentComponet(this);
	}
	
	private void setParentComponet(EmailComponent parentComponet){
		this.parentComponet = parentComponet;
	}
	
	public String buildTemplate(){
		while(childComponents != null && !childComponents.isEmpty()){
			EmailComponent childComponent = childComponents.poll();
			childComponent.buildTemplate();
			childComponent.replaceParentRelatedTag();
		}
		return this.template;
	}
	
	public void replace(EmailComponent childComponent){
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put(childComponent.templateName, childComponent.template);
		this.template = StrSubstitutor.replace(
				this.template, valueMap, PREFIX, SUFFIX);
	}
	
	private void replaceParentRelatedTag(){
		if(this.parentComponet != null){
			parentComponet.replace(this);
		}
	}

}
