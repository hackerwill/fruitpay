package com.fruitpay.base.comm;
/**
 * 字符串轉換工具類
 * 
 */
public class StrConvertUtil {

	/**
	 * 將byte數組轉換为表示16進制值的字符串，如：byte[]{8,18}轉換为：0813， 和public static byte[]
	 * hexStrToByteArr(String strIn) 互为可逆的轉換過程
	 * 
	 * @param arrB
	 *            需要轉換的byte數組
	 * @return 轉換後的字符串
	 */
	public static String byteArrToHexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每個byte(8位)用兩個(16進制)字符才能表示，所以字符串的長度是數組長度的兩倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {// 把負數轉換为正數
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {// 小於0F的數需要在前面補0
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 將表示16進制值的字符串轉換为byte數組，和public static String byteArrToHexStr(byte[] arrB)
	 * 互为可逆的轉換過程
	 * 
	 * @param strIn
	 *            需要轉換的字符串
	 * @return 轉換後的byte數組
	 */
	public static byte[] hexStrToByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// 兩個(16進制)字符表示一個字節(8位)，所以字節數組長度是字符串長度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

}