package com.fruitpay.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.model.StatusInteger;
import com.fruitpay.base.service.impl.ShipmentServiceImpl;
import com.fruitpay.comm.utils.ListTranspose;

public class ShipmentServicePureTest {
	
	@Test
	public void testWithPureCalcaulteShouldPassCheck() {
		List<Integer> rowLimits = Arrays.asList(new Integer[]{5, 5, 5, 6});
		List<Integer> colLimits = Arrays.asList(new Integer[]{7, 5, 6, 6, 7,8});
		
		testWithData(rowLimits, colLimits);
	}
	
	@Test(expected=HttpServiceException.class)
	public void testCalculateWithoutEnoughColumnShouldThrowIllegalArgumentException() {
		List<Integer> rowLimits = Arrays.asList(new Integer[]{5, 6});
		List<Integer> colLimits = Arrays.asList(new Integer[]{100, 200, 30, 50, 70});
		
		testWithData(rowLimits, colLimits);
	}
		
	@Test(expected=HttpServiceException.class)
	public void testWithPureCalcaulteShouldThrowIllegalArgumentException() {
		List<Integer> rowLimits = Arrays.asList(new Integer[]{5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5});
		List<Integer> colLimits = Arrays.asList(new Integer[]{100, 200, 30, 50, 70, 20});
		
		testWithData(rowLimits, colLimits);
	}
	
	@Test(expected=HttpServiceException.class)
	public void testWithRealCase() {
		List<Integer> rowLimits = Arrays.asList(new Integer[]{
				5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 
				5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 
				5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 
				5, 5, 5, 6, 6, 6, 5, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 
				6, 6, 6, 5, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 
				6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 5, 6, 6, 
				6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 
				5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 5, 6, 6, 6, 6, 5, 5, 5, 5, 
				5, 5, 5, 5, 5, 6, 6, 6, 5, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 
				5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 
				6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 
				6, 6, 6, 5, 6, 6, 6, 6, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6,
				});
		List<Integer> colLimits = Arrays.asList(new Integer[]{300, 200, 300, 300, 300, 100, 200,});
		
		testWithData(rowLimits, colLimits);
	}
	
	private List<List<StatusInteger>> testWithData(List<Integer> rowLimits, List<Integer> colLimits) {
		List<List<StatusInteger>> statusIntegerLists = colLimits.stream().map(col -> {
			return rowLimits.stream().map(row -> {
				return new StatusInteger(StatusInteger.Status.none.toString(), 0);
			}).collect(Collectors.toList());
		}).collect(Collectors.toList());
		
		Assert.assertTrue(statusIntegerLists.size() == colLimits.size());
		
		ShipmentServiceImpl shipmentService = new ShipmentServiceImpl();
		statusIntegerLists = shipmentService.calculate(rowLimits, colLimits, statusIntegerLists, null, null);
		
		shipmentService.printCalculatedResult(colLimits, rowLimits, statusIntegerLists);
		
		int col = 0;
		for(List<StatusInteger> statusIntegerList: statusIntegerLists) {
			int colSum = statusIntegerList.stream().map(statusInteger -> statusInteger.getInteger())
				.mapToInt(Integer::intValue)
				.sum();
			
			Assert.assertTrue(colSum <= colLimits.get(col));
			col++;
		}
		
		List<List<StatusInteger>> rowLists = ListTranspose.transpose(statusIntegerLists);
		int row = 0;
		for(List<StatusInteger> rowList: rowLists) {
			int rolSum = rowList.stream().map(statusInteger -> statusInteger.getInteger())
				.mapToInt(Integer::intValue)
				.sum();
			
			Assert.assertTrue(rolSum == rowLimits.get(row));
			row++;
		}
		
		return statusIntegerLists;
	}
	
	@Test
	public void testWithGetRandomRowList() {
		int requiredAmount = 4;
		List<StatusInteger> rowList = new ArrayList<StatusInteger>();
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.fixed.toString(), 1));
		rowList.add(new StatusInteger(StatusInteger.Status.fixed.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		rowList.add(new StatusInteger(StatusInteger.Status.none.toString(), 0));
		
		ShipmentServiceImpl shipmentService = new ShipmentServiceImpl();
		rowList = shipmentService.getRandomRowList(rowList, requiredAmount, 0);
		Assert.assertTrue(rowList.get(1).getInteger().equals(1));
		Assert.assertTrue(rowList.get(2).getInteger().equals(0));
		
		int totalCount = rowList.stream().map(statusInteger -> {
			return statusInteger.getInteger();
		}).reduce(0, (a, b) -> a + b);
		Assert.assertEquals(requiredAmount, totalCount);
	}
	
}