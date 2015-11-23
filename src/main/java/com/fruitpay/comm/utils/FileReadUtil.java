package com.fruitpay.comm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileReadUtil {
	
	private static String lineSeparator = System.getProperty("line.separator");
	
	public static String getResourceFile(String fileName) {
		
		//Get file from resources folder
		ClassLoader classLoader = FileReadUtil.class.getClassLoader();
		
		StringBuilder result = new StringBuilder();
		String line;
		try (
		    InputStream fis = new FileInputStream(classLoader.getResource(fileName).getPath());
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
			
		) {
		    while ((line = br.readLine()) != null) {
		    	result.append(line + lineSeparator);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return result.toString();

	  }

}
