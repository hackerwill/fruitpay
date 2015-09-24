package com.fruitpay.base.comm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具類
 * 
 */
public class Md5Util {

	/**
	 * 根據輸入的字符串生成固定的32位MD5碼
	 * 
	 * @param str
	 *            輸入的字符串
	 * @return MD5碼
	 */
	public final static String getMd5(String str) {
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mdInst.update(str.getBytes());// 使用指定的字節更新摘要
		byte[] md = mdInst.digest();// 獲得密文
		return StrConvertUtil.byteArrToHexStr(md);
	}
	
	/*public static void main(String[] args){
		String a = "123ef#@+++";
		String b = getMd5(a);
		System.out.println(b);
		
		String c = "123ef#@+++";
		String d = getMd5(c);
		
		System.out.println(b.equals(d));
	}*/
}
