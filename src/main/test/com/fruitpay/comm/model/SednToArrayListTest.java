package com.fruitpay.comm.model;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet-junit.xml"})
public class SednToArrayListTest {
	
	@Test
	public void testAddOneMailStr(){
		SendToArrayList list = new SendToArrayList();
		list.add("u9734017@gmail.com");
		assertEquals("u9734017@gmail.com", list.get(0));
	}
	
	@Test
	public void testAddMultipleMailStrBlank(){
		SendToArrayList list = new SendToArrayList();
		list.add("u9734017@gmail.com, deviant604@hotmail.com");
		assertEquals("u9734017@gmail.com", list.get(0));
		assertEquals("deviant604@hotmail.com", list.get(1));
	}
	
	@Test
	public void testAddMultipleMailStrNoBlank(){
		SendToArrayList list = new SendToArrayList();
		list.add("u9734017@gmail.com,deviant604@hotmail.com");
		assertEquals("u9734017@gmail.com", list.get(0));
		assertEquals("deviant604@hotmail.com", list.get(1));
	}
	
	@Test
	public void testAddAllSendToArrayList(){
		SendToArrayList list = new SendToArrayList();
		list.addAll("u9734017@gmail.com,deviant604@hotmail.com");
		assertEquals("u9734017@gmail.com", list.get(0));
		assertEquals("deviant604@hotmail.com", list.get(1));
	}

}
