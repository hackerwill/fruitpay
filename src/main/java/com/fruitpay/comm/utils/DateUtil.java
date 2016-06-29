package com.fruitpay.comm.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	public static final String DEFALUT_DATE_PATTERN = "yyyy-MM-dd";
	
	public static String parseLocalDate(LocalDate localDate){
		return parseLocalDate(localDate, DEFALUT_DATE_PATTERN);
	}
	
	public static String parseLocalDate(LocalDate localDate, String pattern){
		return parseDate(toDate(localDate), pattern);
	}
	
	public static LocalDate toLocalDate(String dateStr){
		return toLocalDate(dateStr, DEFALUT_DATE_PATTERN);
	}
	
	public static LocalDate toLocalDate(String dateStr, String pattern){
		return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));	
	}
	
	public static LocalDate toLocalDate(Date date){
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String parseDate(Date date){
		return parseDate(date, DEFALUT_DATE_PATTERN);
	}
	
	public static String parseDate(Date date, String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Date toDate(String dateStr){
		return toDate(dateStr, DEFALUT_DATE_PATTERN);
	}
	
	public static Date toDate(String dateStr, String pattern){
		DateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date toDate(LocalDate localDate){
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date getMaxDate(){
		// only to make sure it's the maxima date
		Date date = null;
		String string = "3000-01-01";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static int getPreviousDayInt(int day){
		if(day == 1)
			return 7;
		else
			return day - 1;
	}
	

}
