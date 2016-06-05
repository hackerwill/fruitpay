package com.fruitpay.comm.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import org.junit.Assert;
import org.junit.Test;

import com.fruitpay.comm.annotation.ColumnName;


public class AbstractExcelBeanTest {
	
	private class TestBean {
		private String column1;
		private int column2;
		private Date column3;
		
		public TestBean(String column1, int column2, Date column3) {
			super();
			this.column1 = column1;
			this.column2 = column2;
			this.column3 = column3;
		}
		public String getColumn1() {
			return column1;
		}
		public void setColumn1(String column1) {
			this.column1 = column1;
		}
		public int getColumn2() {
			return column2;
		}
		public void setColumn2(int column2) {
			this.column2 = column2;
		}
		public Date getColumn3() {
			return column3;
		}
		public void setColumn3(Date column3) {
			this.column3 = column3;
		}
	}
	
	private class ChildExcelBean extends AbstractExcelBean {
		@ColumnName("欄位1")
		private String column1;
		@ColumnName("欄位2")
		private String column2;
		@ColumnName("欄位3")
		private String column3;
		
		public ChildExcelBean(TestBean testBean) {
			this.column1 = testBean.getColumn1();
			this.column2 = String.valueOf(testBean.getColumn2());
			this.column3 = String.valueOf(testBean.getColumn3());
		}
		
		public String getColumn1() {
			return column1;
		}
		public void setColumn1(String column1) {
			this.column1 = column1;
		}
		public String getColumn2() {
			return column2;
		}
		public void setColumn2(String column2) {
			this.column2 = column2;
		}
		public String getColumn3() {
			return column3;
		}
		public void setColumn3(String column3) {
			this.column3 = column3;
		}
		
	}
	
	@Test
	public void childExcelBeanShouldReturnMapAndList() throws IllegalArgumentException, IllegalAccessException {
		
		ChildExcelBean childExcelBean = new ChildExcelBean(new TestBean("value1", 2, new Date()));
		Map<String, Object> map = childExcelBean.getMap();
		Assert.assertEquals("value1", map.get("column1"));
		Assert.assertEquals("2", map.get("column2"));
		Assert.assertNotEquals(0, map.get("column3").toString().length());
		
		List<ExcelColumn> colList = childExcelBean.getColList();
		Assert.assertEquals("欄位1", colList.get(0).getColumnName());
		Assert.assertEquals("欄位2", colList.get(1).getColumnName());
		Assert.assertEquals("欄位3", colList.get(2).getColumnName());
	}

}
