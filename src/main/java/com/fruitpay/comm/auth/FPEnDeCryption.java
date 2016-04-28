package com.fruitpay.comm.auth;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class FPEnDeCryption {
	private static final String DEFAULT_ENCODING="UTF-8";	 
	/**
	 * 
	 * @param enCode
	 * @return 
	 *	DeCode[0] - LogonTime
	 *  DeCode[1] - UserName
	 *  DeCode[2] - UserToken
	 * @throws Exception
	 */
	public static String[] validateV6Token(String enCode)throws Exception {
		String deKey=FPEnDeCryption.decryptCipher(enCode);	
		return deKey.split(";");
	}
	
	public static String encryptCipher(String pCipher) throws Exception{
		final byte[] Key = { 0x01, 0x02, 0x03, 0x07, 0x05, 0x04, 0x04, 0x07,
				0x02, 0x12, 0x02, 0x0d, 0x0c, 0x0a, 0x04, 0x1E };
		final byte[] IV = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
				0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16 };
		SecretKeySpec skeySpec = new SecretKeySpec(Key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, spec);
		byte[] original = cipher.doFinal(pCipher.getBytes("UTF-8"));
		String originalString = Base64.encode(original);
		return originalString;
	}
	
	public static String decryptCipher(String pCipher) throws Exception{
		final byte[] Key = { 0x01, 0x02, 0x03, 0x07, 0x05, 0x04, 0x04, 0x07,
				0x02, 0x12, 0x02, 0x0d, 0x0c, 0x0a, 0x04, 0x1E };
		final byte[] IV = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
				0x09, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16 };
		SecretKeySpec skeySpec = new SecretKeySpec(Key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec spec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, spec);
		byte[] original = cipher.doFinal(Base64.decode(pCipher));
		String originalString = new String(original, "UTF-8");
		return originalString;
	}
}
