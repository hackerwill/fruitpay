package com.fruitpay.comm.utils;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fruitpay.comm.model.EnumMapOrder;
import com.fruitpay.comm.model.ExcelColumn;
import com.fruitpay.comm.model.Order;
import com.fruitpay.comm.service.ExcelExportService;
import com.fruitpay.comm.session.FPSessionUtil;

public class ExcelUtil {
	
	/**
	 * 產生excel
	 * @param param
	 * @param output
	 * @throws Exception
	 */
	
	public static void doExcelExport(HttpServletRequest request, HttpServletResponse response, 
			String fileType, List<Map<String, Object>> list, List<ExcelColumn> colList) throws Exception{
		String fileName = FPSessionUtil.getHeader(request, "fileName");
		ExcelExportService xlsService = new ExcelExportService(fileType);
		OutputStream output = response.getOutputStream();
		
		request.setAttribute("fileName", fileName);
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Accept-Language", "zh-TW");
		response.setHeader("contentType",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8");
		
		xlsService.createSheet("order", "", colList, list);
		xlsService.write(output);
		output.close();
	}
	
}
