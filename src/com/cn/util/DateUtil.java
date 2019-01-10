package com.cn.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date getCurrentTime() {
		// 获取当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dd = format.format(date);
		Date currentTime = null;
		try {
			currentTime = format.parse(dd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}

	public static Timestamp getCurrentTimestamp(){
		Date date= new Date();
		String nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return Timestamp.valueOf(nowTime);
	}
	
	
	public static String getCurrentTime_m() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return formatter.format(now);
	}
	
	public static String getCurrentTime_s() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(now);
	}
	
	public static String formatDateTime_m(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String value = "";
		if(date != null) {
			value = formatter.format(date);
		}
		return value;
	}

	public static String formatDateTime_s(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String value = "";
		if(date != null) {
			value = formatter.format(date);
		}
		return value;
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date());
	}
	
}
