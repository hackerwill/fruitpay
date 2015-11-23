package com.fruitpay.comm.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fruitpay.comm.model.MessageBean;
import com.fruitpay.comm.utils.FileReadUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet-junit.xml"})

public class EmailComponentTest {
	
	@Autowired 
	EmailTestServiceImpl emailTestServiceImpl;
	@Autowired 
	EmailNewMemberServiceImpl emailNewMemberServiceImpl;
	
	@Test
	public void testTestMailContent(){
		MessageBean messageBean = emailTestServiceImpl.getEmailMessageBean(null, "deviant604@hotmail.com");
		
		assertEquals("測試使用", messageBean.getSubject());
		assertEquals(FileReadUtil.getResourceFile("test/TEST_RESULT.html"), messageBean.getText());
	}
/*
	@Test
	public void testNewOrderMailContent(){
		
		String content = emailNewMemberServiceImpl.getEmailContent();
		String compareContent = FileReadUtil.getResourceFile("test/TEST_RESULT.html");
		assertEquals(compareContent, content);
	}
	*/
}
