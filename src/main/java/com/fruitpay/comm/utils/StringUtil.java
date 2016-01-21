package com.fruitpay.comm.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 類描述
 * <p>
 * 處理字符串的工具類
 * <p>
 * <strong>className類初使化方法</strong>
 * <ul>
 * <li>方法1.
 * <li>方法2.
 * </ul>
 * 
 * @author Andy Wen
 * @version 1.0, 2013/12/7
 * @since JDK1.7
 */
public class StringUtil {
	private static final char[] LT_ENCODE = "&lt;".toCharArray();
	private static final char[] GT_ENCODE = "&gt;".toCharArray();

	/**
	 * 判斷一個字符串是否全是數字,eg: isNumeric("1234")
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午2:42:11
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 去除字符串首尾的空串，如果為null或者為字符串"null"(不區分大小寫)，則轉換為""
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午2:45:41
	 */
	public static String trimString(String s) {
		if ((s == null) || (s.equalsIgnoreCase("null")))
			return "";
		else
			return s.trim();
	}

	/**
	 * 去除字符串首尾的空串，如果為null或undefined或者為字符串"null"或者"undefined"(不區分大小寫)，則轉換為""
	 * <p>
	 * 
	 * @author peter
	 * @date 2013/12/18 上午9:48:20
	 */
	public static String trimUndefString(String s) {
		if ((s == null) || (s.equalsIgnoreCase("null")) || (s.equalsIgnoreCase("undefined")))
			return "";
		else
			return s.trim();
	}

	/**
	 * 去除字符串首尾的空串，如果為null或者為字符串"null"(不區分大小寫)，則轉換為""
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午2:48:17
	 */
	public static String trimString(Object s) {
		if (s == null || s.toString().equalsIgnoreCase("null"))
			return "";
		else
			return s.toString();
	}

	/**
	 * 將字符串轉換為UTF-8格式
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午2:50:30
	 */
	public static String convertStringToUTF8(String s) {
		String s1 = "";
		if (s != null) {
			try {
				s1 = new String(s.trim().getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s1;
	}

	/**
	 * 將數組轉換為UTF-8編碼格式
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午2:54:19
	 */
	public String[] arraysStringUTF8(String as[]) {
		String as1[] = as;
		if (as == null) {
			return null;
		}
		for (int i = 0; i < as1.length; i++) {
			try {
				as1[i] = new String(as1[i].trim().getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception exception) {
				as1[i] = "";
			}
		}
		return as1;
	}

	/**
	 * 根據給定的分隔符標識分隔字符串
	 * <p>
	 * 
	 * @param s String 要分隔的字符串
	 * @param delim 分隔符標識。eg:",/;"代表三個分隔符，分別為,、 /和;
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:00:08
	 */
	public static String[] split(String s, String delim) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(s, delim);
		while (token.hasMoreTokens()) {
			String a = token.nextToken();
			list.add(a);
		}
		String[] stringArray = new String[list.size()];
		list.toArray(stringArray);
		return stringArray;
	}

	/**
	 * 用新字符串替換舊字符串，不區分大小寫
	 * <p>
	 * 
	 * @param line String 要替換的字符串
	 * @param oldString String 舊字符串
	 * @param newString String 新字符串
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:05:19
	 */
	public static final String replaceIgnoreCase(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	/**
	 * 替換字符串中的"<"為"&lt;"、">"為"&gt;"
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:08:32
	 */
	public static final String escapeHTMLTags(String in) {
		if (in == null) {
			return null;
		}
		char ch;
		int i = 0;
		int last = 0;
		char[] input = in.toCharArray();
		int len = input.length;
		StringBuffer out = new StringBuffer((int) (len * 1.3));
		for (; i < len; i++) {
			ch = input[i];
			if (ch > '>') {
				continue;
			} else if (ch == '<') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(LT_ENCODE);
			} else if (ch == '>') {
				if (i > last) {
					out.append(input, last, i - last);
				}
				last = i + 1;
				out.append(GT_ENCODE);
			}
		}
		if (last == 0) {
			return in;
		}
		if (i > last) {
			out.append(input, last, i - last);
		}
		return out.toString();
	}

	/**
	 * 將數組轉換為字符串
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:10:29
	 */
	public static String arrayToSqlString(String[] arr) {
		String sqlStr = "";
		if (arr == null || arr.length == 0) {
			return "''";
		} else {
			for (int i = 0; i < arr.length; i++) {
				if (i == 0) {
					sqlStr += "'" + arr[i] + "'";
				} else {
					sqlStr += ",'" + arr[i] + "'";
				}
			}
		}
		return sqlStr;
	}

	/**
	 * 將字符串轉換為long,如果字符串為"","null"或"NULL"，轉換為0
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:16:16
	 */
	public static long stringToLong(String str) {
		long l = 0;
		if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
			if ("0.0".equals(str)) {
				l = 0;
			} else {
				l = Long.parseLong(str);
			}
		} else {
			l = 0;
		}
		return l;
	}

	/**
	 * 將字符串轉換為int,如果字符串為"","null"或"NULL"，轉換為0
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:16:16
	 */
	public static int stringToInt(String str) {
		int i = 0;
		if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
			i = Integer.parseInt(str);
		} else {
			i = 0;
		}
		return i;
	}

	/**
	 * 將字符串轉換為double,如果字符串為"","null"或"NULL"，轉換為0
	 * <p>
	 * 
	 * @author Andy Wen
	 * @date 2013/12/7 下午3:16:16
	 */
	public static double stringToDouble(String str) {
		double d = 0;
		if (!"NULL".equalsIgnoreCase(str) && str != null && !"".equals(str)) {
			d = Double.parseDouble(str);
		} else {
			d = 0;
		}
		return d;
	}

	/**
	 * 將數組轉換為字符串, 如果為數字(int, long)數組,轉換為不帶單引號的字符串,均用英文逗號分隔; 如果為其他數組,轉換為帶單引號的字符串
	 * <p>
	 * 
	 * @param arr Object[] 待轉換數組
	 * @author Andy Wen
	 * @date 2013/12/10 下午1:56:38
	 */
	public static String arrayToString(Object[] arr) {
		String str = "";
		boolean isNumArr = false;
		if (arr instanceof Integer[] || arr instanceof Long[]) {
			isNumArr = true;
		}
		if (arr != null && arr.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				Object s = arr[i];
				if (!isNumArr) {
					sb.append("'");
				}
				sb.append(s);
				if (!isNumArr) {
					sb.append("'");
				}
				if (i != arr.length - 1) {
					sb.append(",");
				}
			}
			str = sb.toString();
		}
		return str;
	}

	/**
	 * 判斷是否為null
	 * <p>
	 * 
	 * @author Heidy Chen
	 * @param pObject 輸入要判斷的值
	 * @return true or false
	 */
	public static boolean isEmpty(Object pObject) {
		return (pObject == null || pObject.toString().trim().equals(""));
	}

	/**
	 * 判斷是否為null或空白
	 * <p>
	 * 
	 * @author Heidy Chen
	 * @param pObject 輸入要判斷的值
	 * @return true or false
	 */
	public static boolean isEmptyOrSpace(Object pObject) {
		return isEmpty(pObject) || pObject.toString().trim().equals("");
	}

	/**
	 * 判斷是否為數值型態
	 * <p>
	 * 
	 * @author Heidy Chen
	 * @param pValue
	 * @return true of false
	 */
	public static boolean isDigital(String pValue) {
		try {
			new Integer(pValue);
			// new BigDecimal(pValue);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 解析目標字串中的特定字首、字尾
	 * <p>
	 * 可以用在只知道字首字尾而不知中間字串資料為何的時候<br>
	 * 此功能只會回傳第一個符合條件的字串，ex:字首為"images/", 字尾為".gif"<br>
	 * 回傳字串包含字首:回傳 images/xxx<br>
	 * 回傳字串包含字尾:回傳 xxx.gif<br>
	 * 回傳字串包含首尾:回傳 images/xxx.gif
	 * 
	 * @author Heidy Chen
	 * @param target 目標字串
	 * @param prefix 欲解析的字首
	 * @param suffix 欲解析的字尾
	 * @param includePrefix 回傳的資料是否包含字首
	 * @param includeSuffix 回傳的資料是否包含字尾
	 * @return 符合條件的第一筆資料
	 */
	public static String parseString(String target, String prefix, String suffix, boolean includePrefix, boolean includeSuffix) {
		try {
			int prefixPosition = target.indexOf(prefix);
			int suffixPosition = target.indexOf(suffix);
			return target.substring(prefixPosition + (includePrefix ? 0 : prefix.length()), suffixPosition + (includeSuffix ? suffix.length() : 0));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 解析目標字串中是否符合條件
	 * <p>
	 * 在List中的條件需全部符合才會回傳true，否則回傳false
	 * 
	 * @author Heidy Chen
	 * @param target 目標字串
	 * @param condition 字串條件
	 * @return true or false
	 */
	public static boolean parseString(String target, List condition) {
		boolean isAllMatch = false;
		try {
			if (condition == null) {
				return false;
			}

			for (int i = 0; i < condition.size(); i++) {
				int x = target.indexOf(condition.get(i).toString());
				isAllMatch = x < 0 ? false : true;
			}
			return isAllMatch;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 將二維資料轉成Map
	 * <p>
	 * <b>適用時機：Query- JdbcTemplateManager.executeQuery return String[][]</b><br>
	 * 將二維矩陣轉成List<Map>的形式，同時判斷date欄位轉成date格式
	 * 
	 * @author Heidy Chen
	 * @param Object[][]
	 * @return List<Map>
	 */
	public static List<Map<String, Object>> transArrayToMap(Object[][] commonResult) {
		List<Map<String, Object>> resultList = new LinkedList<Map<String, Object>>();
		try {
			Map<String, Object> commonMapObject = null;
			java.util.Date date = null;
			java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat();
			simple.applyPattern("yyyy-MM-dd HH:mm:ss");
			if (null != commonResult && commonResult.length > 0) {
				for (int i = 1; i < commonResult.length; i++) {
					commonMapObject = new LinkedHashMap<String, Object>();
					for (int j = 0; j < commonResult[i].length; j++) {
						if (null != commonResult[0][j] && !("").equals(commonResult[0][j])) {
							if (String.valueOf(commonResult[0][j]).endsWith("DATE")) {
								if (null != commonResult[i][j] && !("").equals(commonResult[i][j])){
									date = simple.parse(String.valueOf(commonResult[i][j]));
								}
							}else if(String.valueOf(commonResult[0][j]).endsWith("TIME")){
								//simple.applyPattern("yyyy-MM-dd HH:mm");
								if (null != commonResult[i][j] && !("").equals(commonResult[i][j])){
									date = simple.parse(String.valueOf(commonResult[i][j]));
								}
							}
							else{
								date = null;
							}
							commonMapObject.put((String) commonResult[0][j], (String.valueOf(commonResult[0][j]).endsWith("DATE") || String.valueOf(commonResult[0][j]).endsWith("TIME")) ? date : commonResult[i][j]);
						}
					}
					resultList.add(commonMapObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return resultList;
	}
}
