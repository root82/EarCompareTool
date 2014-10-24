package com.murali.ear.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Properties {

	public static SimpleDateFormat sdfExcelFileDate;
	public static SimpleDateFormat sdfExcelFileTime;
	public static SimpleDateFormat sdfDateTime;
	
	public static String[] strToken;
	public static List<String> listTokens;
	
	static {
		sdfExcelFileDate = new SimpleDateFormat("yyyyMMdd");
		sdfExcelFileTime = new SimpleDateFormat("HHmmss");
		sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		strToken = new String("par,sar").split(",");
		listTokens = new ArrayList<String>();
		listTokens.add("par");
		listTokens.add("sar");
		listTokens.add("jar");
	}
	
	
}
