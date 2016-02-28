package com.fruitpay.comm.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.fruitpay.comm.model.OrderExcelBean;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	
	public static <T> boolean exportExcelFile(List<T> list){
		
		boolean successful = false;
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File("D:/outputExcelTest.xls"));
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			WritableCellFormat cellFormat1 = new WritableCellFormat();
			cellFormat1.setBackground(Colour.BRIGHT_GREEN);
			cellFormat1.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
			int row = 0;
			Label label;
			for(T bean : list){
				Map<String, String> properties = BeanUtils.describe(bean);
				BeanUtils.populate(bean, properties);
				properties.remove("class"); //除去不需要的class name
				List<String> keys = new ArrayList<>(properties.keySet());
				int col = 0;
				int keyCount = 0;
				//添加欄位名稱
				keys.add(0, "No.");
				if(row == 0){
					for(String key : keys){
						label = new Label(keyCount, row, key, cellFormat1);
						sheet.addCell(label);
						keyCount++;
					}
				}
				
				//添加序號
				label = new Label(col, row+1, String.valueOf(row+1), cellFormat1); 
				sheet.addCell(label);
				
				//寫入bean的值
				List<String> beanValues = new ArrayList<>(properties.values());
				for(String colValue : beanValues){
					label = new Label(col+1, row+1, colValue, cellFormat1); 
					col++;
					sheet.addCell(label);
				}
				row++;
			}
			
			workbook.write();
            workbook.close();
            
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e){
			
		}
		
		return successful;
	}
	
	public static void main(String[] args){
		
		List<OrderExcelBean> list = new ArrayList<OrderExcelBean>();
		OrderExcelBean firstOne = new OrderExcelBean();
		firstOne.setPlatform("PLATFORM1");
		firstOne.setType("TYPE1");
		
		OrderExcelBean secondOne = new OrderExcelBean();
		secondOne.setPlatform("PLATFORM2");
		secondOne.setType("TYPE2");
		
		list.add(firstOne);
		list.add(secondOne);
		
		exportExcelFile(list);
		
	}
	
}
