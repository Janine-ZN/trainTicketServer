package com.cn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 常用数据库操作工具集。
 */
public class DatabaseUtil {

	/**
	 * 生成IN语句
	 * 
	 * @param ids
	 * @param bNeedComma
	 * @return
	 */
	public static synchronized String buildInStatement(int[] ids){
		StringBuffer buffer = new StringBuffer();
		if(ids == null) {
			return "-1";
		}
		for(int i = 0; i < ids.length; i++) {
			if (i == 0) {
				buffer.append(ids[i]);
			} else {
				buffer.append(",").append(ids[i]);
			}
		}
		return buffer.toString();
	}

	/**
	 * 生成IN语句
	 * 
	 * @param ids
	 * @return
	 */
	public static synchronized int[] convertToInt(String ids[]) {
		if (ids == null) {
			return null;
		}
		int []values = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			values[i] = Integer.parseInt(ids[i]);
		}
		return values;
	}
	
	/**
	 * 以逗号字符串转换为int数组
	 * 
	 * @param array
	 * 
	 * @return 
	 */
	public static synchronized String[] convertToString(String array) {
		return convertToString(array, ",");
	}
	
	/**
	 * 字符串转换为int数组
	 * 
	 * @param array				字符串
	 * @param dilimeter			分隔符
	 * 
	 * @return 
	 */
	public static synchronized String[] convertToString(String array, String dilimeter) {
		if (array == null) {
			return null;
		}
		String separator = ",";
		if (!StringUtil.isNullOrEmpty(dilimeter)) {
			separator = dilimeter;
		}
		List<String> values = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(array, separator);
		while (tokens.hasMoreTokens()) {
			String result = tokens.nextToken();
			values.add(result);
		}
		String results[] = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			results[i] = values.get(i);
		}
		return results;
	}
	
	/**
	 * 以逗号字符串转换为int数组
	 * 
	 * @param array
	 * 
	 * @return 
	 */
	public static synchronized int[] convertToInt(String array) {
		return convertToInt(array, ",");
	}
	
	/**
	 * 字符串转换为int数组
	 * 
	 * @param array				字符串
	 * @param dilimeter			分隔符
	 * 
	 * @return 
	 */
	public static synchronized int[] convertToInt(String array, String dilimeter) {
		if (array == null) {
			return null;
		}
		String separator = ",";
		if (!StringUtil.isNullOrEmpty(dilimeter)) {
			separator = dilimeter;
		}
		List<Integer> values = new ArrayList<Integer>();
		StringTokenizer tokens = new StringTokenizer(array, separator);
		while (tokens.hasMoreTokens()) {
			String result = tokens.nextToken();
			int value = 0;
			try {
				value = Integer.parseInt(result);
				values.add(new Integer(value));
			} catch (Exception exception) {
				//Do Nothing
			} 
		}
		int results[] = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			results[i] = values.get(i).intValue();
		}
		return results;
	}
	
	/**
	 * 生成IN语句
	 * 
	 * @param ids
	 * @param bNeedComma
	 * @return
	 */
	public static synchronized String buildInStatement(String[] ids){
		return buildInStatement(ids, true);
	}

	/**
	 * 生成IN语句
	 * 
	 * @param ids
	 * @param bNeedComma
	 * @return
	 */
	public static String buildInStatement(String[] ids, boolean bNeedComma){
		StringBuffer buffer = new StringBuffer();
		if(ids == null || ids.length < 1) {
			return "''";
		}
		for(int i = 0; i < ids.length; i++) {
			if (i == 0) {
				if (bNeedComma) {
					buffer.append("'").append(ids[i]).append("'");
				} else {
					buffer.append(ids[i]);
				}
			} else {
				if (bNeedComma) {
					buffer.append(",'").append(ids[i]).append("'");
				} else {
					buffer.append(",").append(ids[i]);
				}
			}
		}
		return buffer.toString();
	}
}
