package com.fruitpay.comm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImportService {
	private Workbook workbook;
	private int startRow = 0;
	private int startCol = 0;
	
	public static void main(String[] arg0) throws Exception{
		FileInputStream file = new FileInputStream(new File("C:/Users/clarewu/Downloads/KPI028DashDtl_20140722103608.xls"));
		ExcelImportService service = new ExcelImportService(file,"xls");
		String[] colNames = {
				"dash_id","yyyymm","kpi_cycle","cycle_no","org_lev","org_nam","kpi_type_name","kpi_no","kpi_nam","unit","kpi_trend","section_num","point1","point2","point3","point4","point5","forecast"
		};
		service.setStartRow(2);
		List list = service.getDataList(0, colNames);
	}
	
	public ExcelImportService(InputStream in,String type)throws Exception{
		if("xlsx".equals(type)){
			workbook = new XSSFWorkbook(in);
		}else{
			workbook = new HSSFWorkbook(in);
		}
	}
	
	public List<Map<String,String>> getDataList(int sheetIndex,String[] colNames){
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		
		List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
		Map<String,String> dataMap = null;
		String value;
		String key;
		Row row;
		int blankCount = 0;
		for(int i = startRow;i<sheet.getPhysicalNumberOfRows();i++){
			row = sheet.getRow(i);
			dataMap = new HashMap<String,String>();
			if(blankCount>=colNames.length)
				break;
			for(int j = startCol;j<colNames.length+startCol;j++){
				key = colNames[j-startCol];
				value = row.getCell(j).toString().replaceAll("\\n|\\t|\\s", "");
				if("".equals(value)){
					blankCount++;
				}
				dataMap.put(key,(value==null)?"":value);
			}
			dataList.add(dataMap);
		}
		return dataList;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}
	
	
}
