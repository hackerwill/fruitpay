package com.fruitpay.comm.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fruitpay.comm.model.MessageBean;
import com.fruitpay.comm.utils.ZohoMailUtil;

@Component
public class EmailConsumer{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private ZohoMailUtil zohoMailUtil;
	private Queue<MessageBean> queue = null;
	
	@PostConstruct
	public void init(){
    	queue = new LinkedList<MessageBean>();
	}
    
    public synchronized void add(MessageBean messageBean){
    	queue.add(messageBean);
    	notify();
    }
    
    public synchronized void addAll(List<MessageBean> messageBeans){
    	queue.addAll(messageBeans);
    	notify();
    }
    
    @Async 
    public void runConsume(){
    	while (true) {
    		consume();
    	}
    }
    
    public synchronized void consume(){
    	if(queue.size() != 0){
    		logger.info("send email to " + 
    				queue.peek().getSendToListStr());
        	zohoMailUtil.send(queue.poll());
		}else{
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
 
}
