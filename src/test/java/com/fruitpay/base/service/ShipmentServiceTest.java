package com.fruitpay.base.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.util.AbstractSpringJnitTest;

public class ShipmentServiceTest extends AbstractSpringJnitTest{
	
	@Inject
	ShipmentService shipmentService;
	
	@Test
	@Transactional
	@Rollback(true)
	public void testWithListAllOrdersByDate() throws Exception {
		LocalDate nextFriday = calcNextFriday(LocalDate.now());
		
		Page<CustomerOrder> customerOrders =  shipmentService.listAllOrdersByDate(nextFriday, 1, 10);
		Assert.assertNotEquals(customerOrders.getContent().size(), 0);
	}
	
	private LocalDate calcNextFriday(LocalDate d) {
		  return d.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
		}
	
}
