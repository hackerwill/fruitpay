package com.fruitpay.comm.utils;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fruitpay.comm.model.EnumMapOrder;
import com.fruitpay.comm.model.ExcelColumn;
import com.fruitpay.comm.model.Order;
import com.fruitpay.comm.service.ExcelExportService;

public class ExcelUtil {
	
	/**
	 * 產生excel
	 * @param param
	 * @param output
	 * @throws Exception
	 */
	
	public static void doExcelExport(Map<String,String> param, List<Map<String, Object>> list, OutputStream output,String fileType) throws Exception{
		ExcelExportService xlsService = new ExcelExportService(fileType);
		List<ExcelColumn> colList = new LinkedList<ExcelColumn>();
		
		for(Entry<Order, String> entry : EnumMapOrder.getOrderColEnumMap().entrySet()) {	
			colList.add(new ExcelColumn(String.valueOf(entry.getKey()),(String)entry.getValue()));
		}		
		xlsService.createSheet("order", "", colList, list);
		xlsService.write(output);
		output.close();
	}
	
//	public static <T> boolean exportExcelFile(List<T> list, OutputStream output){
//		
//		boolean successful = false;
//		try {
//			WritableWorkbook workbook = Workbook.createWorkbook(new File("D:/outputExcelTest.xls"));//output);
//			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
//			WritableCellFormat cellFormat1 = new WritableCellFormat();
//			cellFormat1.setBackground(Colour.BRIGHT_GREEN);
//			cellFormat1.setBorder(Border.ALL, BorderLineStyle.MEDIUM);
//			int row = 0;
//			Label label;
//			List<OrderExcelBean> list2 = new ArrayList<OrderExcelBean>();			
//			OrderExcelBean firstOne = new OrderExcelBean();
//			firstOne.setPlatform("PLATFORM1");
//			firstOne.setType("TYPE1");
//			
//			OrderExcelBean secondOne = new OrderExcelBean();
//			secondOne.setPlatform("PLATFORM2");
//			secondOne.setType("TYPE2");
//			
//			list2.add(firstOne);
//			list2.add(secondOne);
//			for(T bean : (List<T>)list2){
//				Map<String, String> properties = BeanUtils.describe(bean);
//				BeanUtils.populate(bean, properties);
//				properties.remove("class"); //除去不需要的class name
//				List<String> keys = new ArrayList<>(properties.keySet());
//				int col = 0;
//				int keyCount = 0;
//				//添加欄位名稱
//				keys.add(0, "No.");
//				if(row == 0){
//					for(String key : keys){
//						label = new Label(keyCount, row, key, cellFormat1);
//						sheet.addCell(label);
//						keyCount++;
//					}
//				}
//				
//				//添加序號
//				label = new Label(col, row+1, String.valueOf(row+1), cellFormat1); 
//				sheet.addCell(label);
//				
//				//寫入bean的值
//				List<String> beanValues = new ArrayList<>(properties.values());
//				for(String colValue : beanValues){
//					label = new Label(col+1, row+1, colValue, cellFormat1); 
//					col++;
//					sheet.addCell(label);
//				}
//				row++;
//			}
//			
//			workbook.write();
//            workbook.close();
//            
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return successful;
//	}
	
	
}
