package com.fruitpay.comm.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.MessagingException;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.fruitpay.comm.model.MessageBean;  
  
@Component
public class ZohoMailUtil {  
	
	private static String defaultUserEmail = null;
	private static String defaultUserName = null;
	private static String defaultUserPassword = null;
	private static Properties props = null;
	private static Session session = null;
	
	@PostConstruct
	public void init() {
		defaultUserEmail = "info@fruitpay.com.tw";
		defaultUserName = "果物配團隊";
		defaultUserPassword = "A564r2b3c";
		props = new Properties();
		props.put("mail.smtp.host", "smtp.zoho.com");  
        props.put("mail.smtp.socketFactory.port", "465");  
        props.put("mail.smtp.socketFactory.class",  
                "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.port", "465");  
        
        session = Session.getInstance(props, new Authenticator() {  
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(defaultUserEmail, defaultUserPassword);  
            }  
        });
	}
	
	public boolean send(MessageBean messageBean){
		
		try {  
			  
            Message message = new MimeMessage(session);  
            message.setFrom(new InternetAddress(defaultUserEmail, defaultUserName));  
            message.setRecipients(Message.RecipientType.TO, InternetAddress  
                    .parse(messageBean.getSendToListStr()));  
            message.setSubject(messageBean.getSubject());  
            message.setContent(messageBean.getText(), "text/html; charset=utf-8");  
            
            Transport.send(message);
  
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {  
            throw new RuntimeException(e);  
        }  
		
	}
	
	/*
    public static void main(String[] args) {  
    	List<String> sendToList = new ArrayList<String>();
    	sendToList.add("u9734017@gmail.com");
    	sendToList.add("deviant604@hotmail.com");
    	MessageBean messageBean = new MessageBean(sendToList, "TEST", "TEST123");
    	send( messageBean);
        
    }
    */
} 
