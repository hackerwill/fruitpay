package com.fruitpay.base.comm;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5�[�K�u����
 * 
 */
public class Md5Util {

	/**
	 * �ھڿ�J���r�Ŧ�ͦ��T�w��32��MD5�X
	 * 
	 * @param str
	 *            ��J���r�Ŧ�
	 * @return MD5�X
	 */
	public final static String getMd5(String str) {
		MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mdInst.update(str.getBytes());// �ϥΫ��w���r�`��s�K�n
		byte[] md = mdInst.digest();// ��o�K��
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
