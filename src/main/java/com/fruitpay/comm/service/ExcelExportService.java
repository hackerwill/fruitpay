package com.fruitpay.comm.service;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fruitpay.comm.model.ExcelColumn;

public class ExcelExportService {
	private Workbook workbook;
	private CellStyle titleStyle;
	private CellStyle cellNameStyle;
	private CellStyle cellValueStyle;
	private int startRow = 0;
	private int startCol = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public ExcelExportService(String type) throws IOException {
		if ("xlsx".equals(type)) {
			workbook = new XSSFWorkbook();
		} else {
			workbook = new HSSFWorkbook();
		}
		initTitleStyle();
		initCellNameStyle();
		initCellValueStyle();

	}

	private void initTitleStyle() {
		Font font = workbook.createFont();
		font.setFontName("標楷體");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 20);

		titleStyle = workbook.createCellStyle();
		// titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//設置可填充儲存格底色
		// titleStyle.setFillForegroundColor(HSSFColor.BLUE.index);//
		titleStyle.setFont(font); // 設定字體
		// titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平置中
		// titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// //垂直置中

		// 設定框線
		titleStyle.setBorderBottom((short) 1);
		titleStyle.setBorderTop((short) 1);
		titleStyle.setBorderLeft((short) 1);
		titleStyle.setBorderRight((short) 1);
		titleStyle.setWrapText(true);// 自動換行
	}

	private void initCellNameStyle() {
		Font font = workbook.createFont();
		font.setFontName("Microsoft JhengHei");
		font.setBold(true);
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);

		cellNameStyle = workbook.createCellStyle();
		cellNameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 設置可填充儲存格底色
		cellNameStyle.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);//
		cellNameStyle.setFont(font); // 設定字體
		cellNameStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平置中
		cellNameStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直置中

		// 設定框線
		cellNameStyle.setBorderBottom((short) 1);
		cellNameStyle.setBorderTop((short) 1);
		cellNameStyle.setBorderLeft((short) 1);
		cellNameStyle.setBorderRight((short) 1);
		cellNameStyle.setWrapText(true);// 自動換行
	}

	private void initCellValueStyle() {
		Font font = workbook.createFont();
		font.setFontName("Microsoft JhengHei");
		font.setFontHeightInPoints((short) 11);

		cellValueStyle = workbook.createCellStyle();
		// cellValueStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//設置可填充儲存格底色
		// cellValueStyle.setFillForegroundColor(HSSFColor.YELLOW.index);//
		cellValueStyle.setFont(font); // 設定字體
		cellValueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平置中
		cellValueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直置中

		// 設定框線
		cellValueStyle.setBorderBottom((short) 1);
		cellValueStyle.setBorderTop((short) 1);
		cellValueStyle.setBorderLeft((short) 1);
		cellValueStyle.setBorderRight((short) 1);
		cellValueStyle.setWrapText(true);// 自動換行
	}

	/**
	 * 
	 * @param sheetName
	 * @param title
	 * @param columnNames
	 * @param dataList
	 * @throws Exception
	 */
	public void createSheet(String sheetName, String title, List<ExcelColumn> colList,
			List<Map<String, Object>> dataList) throws Exception {
		if (sheetName == null || "".equals(sheetName)) {
			throw new Exception("sheetName is NULL or sheetName is blank");
		}

		if (colList == null) {
			throw new Exception("columnNames is NULL");
		}

		if (dataList == null) {
			throw new Exception("dataList is NULL");
		}

		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName(sheetName));
		int nextRow = startRow;
		int nextCol = startCol;

		// 設定title
		setValue(sheet.createRow(nextRow), nextCol, title, HSSFCellStyle.ALIGN_CENTER, titleStyle);
		// 跨欄合併
		sheet.addMergedRegion(new CellRangeAddress(nextRow, (short) nextCol, nextRow, (short) (colList.size() - 1)));
		nextRow++;
		int[] wSize = new int[colList.size()];
		int colCount = 0;
		Row colNameRow = sheet.createRow(nextRow++);
		// 寫入欄位名稱
		for (ExcelColumn col : colList) {
			setValue(colNameRow, nextCol++, col.getColumnName(), HSSFCellStyle.ALIGN_CENTER, cellNameStyle);
			// 紀錄最寬欄位
			int sSize = ((Double) (String.valueOf(col.getColumnName()).length() * 2.5)).intValue();
			if (sSize > wSize[colCount]) {
				wSize[colCount] = (sSize > 50) ? 50 : sSize;
			}
			colCount++;
		}

		// 寫入資料
		for (Map<String, Object> map : dataList) {
			int heightSize = 1;
			colCount = 0;
			nextCol = startCol;
			Row row = sheet.createRow(nextRow++);
			for (ExcelColumn col : colList) {
				Object value = map.get(col.getColumnCode());

				setValue(row, nextCol++, (Object) value, HSSFCellStyle.ALIGN_CENTER, cellValueStyle);

				// 紀錄最寬欄位
				int sSize = ((Double) (String.valueOf(value).length() * 2.5)).intValue();
				if (sSize > wSize[colCount]) {
					wSize[colCount] = (sSize > 50) ? 50 : sSize;
				}
				// 紀錄最高
				// int height = (sSize/50)+1;
				// heightSize = height>heightSize?height:heightSize;
				colCount++;
			}
			// 設定行高度
			// row.setHeightInPoints((short)heightSize*340);

		}

		// 設定欄寬度
		if (dataList.size() > 0) {
			for (int c = 0; c < colList.size(); c++) {
				if (wSize[c] != 0) {
					sheet.setColumnWidth(c, wSize[c] * 200);
				}
			}
		}
	}

	public void write(OutputStream out) throws IOException {
		if (workbook == null)
			return;
		workbook.write(out);
		out.close();
	}

	public void setValue(Row row, int col, Object value, short align, CellStyle cellStyle) {
		if (value == null) {
			setValue(row, col, "", align, cellStyle);
		} else if (value instanceof String) {
			setValue(row, col, (String) value, align, cellStyle);
		} else if (value instanceof Integer) {
			setValue(row, col, (Integer) value, align, cellStyle);
		} else if (value instanceof Double) {
			setValue(row, col, (Double) value, align, cellStyle);
		} else if (value instanceof Long) {
			setValue(row, col, (Long) value, align, cellStyle);
		} else if (value instanceof BigDecimal) {
			setValue(row, col, ((BigDecimal) value).doubleValue(), align, cellStyle);
		} else if (value instanceof Date) {
			setValue(row, col, sdf.format(value), align, cellStyle);
		}
	}

	public void setValue(Row row, int col, String value, short align, CellStyle cellStyle) {
		cellStyle.setAlignment(align);
		Cell cell = row.createCell(col);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value.replaceAll("<[\\w\\s\\/\\\"\\-'=;:]*>", ""));
	}

	public void setValue(Row row, int col, Integer value, short align, CellStyle cellStyle) {
		cellStyle.setAlignment(align);
		Cell cell = row.createCell(col);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	public void setValue(Row row, int col, double value, short align, CellStyle cellStyle) {
		cellStyle.setAlignment(align);
		Cell cell = row.createCell(col);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

	public void setValue(Row row, int col, long value, short align, CellStyle cellStyle) {
		cellStyle.setAlignment(align);
		Cell cell = row.createCell(col);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(value);
	}

}
