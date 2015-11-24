package com.fruitpay.comm.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RadomValueUtil {
	
	private final static int MAX_PASSWORD_ALPHABET_LENGTH = 4;
	private final static int MAX_PASSWORD_NUMBER_LENGTH = 4;
	
	public static String getRandomPassword(){
		return RandomStringUtils.randomAlphabetic(MAX_PASSWORD_ALPHABET_LENGTH) + 
				RandomStringUtils.randomNumeric(MAX_PASSWORD_NUMBER_LENGTH);
	};

}
