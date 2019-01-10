package com.cn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具集
 */
public class StringUtil {

	/**
	 * 替换字符串
	 * 
	 * @param source
	 * @param sub
	 * @param with
	 * @return String
	 */
	public static final String replace(String source, String sub, String with) {

		if (source == null || sub == null || with == null) {
			return source;
		}

		int c = 0;
		int i = source.indexOf(sub, c);
		if (i == -1) {
			return source;
		}
		StringBuffer buf = new StringBuffer(source.length() + with.length());
		synchronized (buf) {
			do {
				buf.append(source.substring(c, i));
				buf.append(with);
				c = i + sub.length();
			} while ((i = source.indexOf(sub, c)) != -1);
			if (c < source.length()) {
				buf.append(source.substring(c, source.length()));
			}
		}
		return buf.toString();
	}

	/**
	 * 替换字符串，不区分大小写
	 * 
	 * @param source
	 * @param sub
	 * @param with
	 * @return String
	 */
	public static final String replaceNoCase(String source, String sub,
			String with) {
		if (source == null || sub == null || with == null) {
			return source;
		}

		String noCaseSource = source.toLowerCase();
		String noCaseSub = sub.toLowerCase();

		int c = 0;
		int i = noCaseSource.indexOf(noCaseSub, c);
		if (i == -1) {
			return source;
		}

		StringBuffer buf = new StringBuffer(source.length() + with.length());
		synchronized (buf) {
			do {
				buf.append(source.substring(c, i));
				buf.append(with);
				c = i + sub.length();
			} while ((i = noCaseSource.indexOf(noCaseSub, c)) != -1);

			if (c < noCaseSource.length()) {
				buf.append(source.substring(c, source.length()));
			}
		}
		return buf.toString();
	}

	/**
	 * 数字转换为定长字符串，如果长度小于指定长度，则在字符串前补0字符
	 * 
	 * @param value
	 *            值
	 * @param length
	 *            字符位数
	 * 
	 * @return 字符串
	 */
	public static String fixNumber(long value, int length) {
		return fixNumber(String.valueOf(value), length, '0', false);
	}

	/**
	 * 数字转换为定长字符串，如果长度小于指定长度，则在字符串前补 <code>staff</code>字符
	 * 
	 * @param value
	 * @param length
	 * @param staff
	 * @return 字符串
	 */
	public static String fixNumber(long value, int length, char staff) {
		return fixNumber(String.valueOf(value), length, staff, false);
	}

	/**
	 * 字符串转换为定长字符串，如果长度小于指定长度，则在字符串前补 <code>0</code>字符
	 * 
	 * @param value
	 * @param length
	 * @param staff
	 * @return 字符串
	 */
	public static String fixNumber(String source, int length) {
		return fixNumber(source, length, '0', false);
	}

	/**
	 * 字符串转换为定长字符串，如果长度小于指定长度，则在字符串前补 <code>staff</code>字符
	 * 
	 * @param source
	 *            待处理字符串
	 * @param length
	 *            总长度
	 * @param staff
	 *            填充字符
	 * @param staffRight
	 *            是否在右侧填充字符
	 * @return 字符串
	 */
	public static String fixNumber(String source, int length, char staff,
			boolean staffRight) {
		String result = source;
		if (source == null) {
			result = "";
		}
		if (result.length() < length) {
			String zero = "";
			for (int i = result.length(); i < length; i++) {
				zero += staff;
			}
			if (staffRight) {
				result = result + zero;
			} else {
				result = zero + result;
			}
		}
		return result;
	}

	/**
	 * 字符串转换为定长字符串，如果长度小于指定长度，则在字符串前补 <code>staff</code>字符
	 * 
	 * @param source
	 *            待处理字符串
	 * @param length
	 *            总长度
	 * @param staff
	 *            填充字符
	 * @param staffRight
	 *            是否在右侧填充字符
	 * @return 字符串
	 */
	public static String fixNumber(long value, int length, char staff,
			boolean staffRight) {
		return fixNumber(String.valueOf(value), length, staff, staffRight);
	}

	/**
	 * 判断字符串是否为null或着空串
	 * 
	 * @param source
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String source) {
		if (source == null) {
			return true;
		}
		if ("".equals(source)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符数组是否为null或着空串
	 * 
	 * @param source
	 * 
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String[] source) {
		if (source == null) {
			return true;
		}
		if (source.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 分割字符串，返回字符串数组，如果原串为<code>null</code>，则返回 <code>null</code>。
	 * 
	 * @param source
	 *            字符
	 * @param delimeter
	 *            分隔字符
	 * @return String[] 字符数组
	 */
	public static synchronized String[] split(String source, String delimeter) {
		if (source == null) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(source, delimeter);
		List<String> items = new ArrayList<String>();
		while (tokens.hasMoreTokens()) {
			items.add(tokens.nextToken());
		}
		String[] array = new String[items.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = items.get(i);
		}
		return array;
	}

	/**
	 * oracle in 查询
	 * 
	 * @Title: oracleIn
	 * @param ids
	 * @return
	 */
	public static String oracleIn(String[] ids) {
		if (ids == null)
			return null;
		StringBuffer buffer = new StringBuffer("'");
		for (int i = 0; i < ids.length - 1; i++) {
			buffer.append(ids[i] + ",");
		}
		buffer.append(ids[ids.length - 1]).append("'");
		return buffer.toString();
	}

	/**
	 * int转换为string
	 * 
	 * @Title: intToString
	 * @param value
	 * @return
	 */
	public static String intToString(int value) {
		return String.valueOf(value);
	}

	/**
	 * 去掉null值
	 * 
	 * @Title: removeNull
	 * @param str
	 * @return
	 */
	public static String removeNull(String str) {
		if (null == str)
			return "";
		return str;
	}

	/**
	 * 去掉null值
	 * 
	 * @Title: removeNull
	 * @param str
	 * @return
	 */
	public static String removeNull(String str, String replace) {
		if (null == str || "".equals(str)) {
			return replace;
		}
		return str;
	}

	/**
	 * 根据特定的字符将字符串分割，同时返回第一个值
	 * 
	 * @Title: getFirstBySplit
	 * @param src
	 * @param split
	 * @return
	 */
	public static String getFirstBySplit(String src, String split) {
		String result = "";
		if (null != src) {
			int index = src.indexOf(split);
			if (index != -1) {
				result = src.substring(0, index);
			}
		}
		return result;
	}

	/**
	 * 字符串转换为double类型
	 * 
	 * @Title: strToDouble
	 * @param str
	 * @return
	 */
	public static double strToDouble(String str) {
		double res = 0.0;
		if (null != str && !"".equals(str)) {
			res = Double.valueOf(str);
		}
		return res;
	}

	/**
	 * 字符串转换为int类型
	 * 
	 * @Title: strToDouble
	 * @param str
	 * @return
	 */
	public static int strToInt(String str) {
		int res = 0;
		if (null != str && !"".equals(str)) {
			res = Integer.valueOf(str);
		}
		return res;
	}

	/**
	 * 返回加1后的字符串(主要用户Level处理)
	 * 
	 * @param str
	 * @return
	 */
	public static String strNext(String str) {
		int res = 0;
		if (null != str && !"".equals(str)) {
			res = Integer.valueOf(str) + 1;
		}
		return String.valueOf(res);
	}

	/**
	 * 将列表返回为字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] listToStrs(List<String> strs) {
		if (strs.size() == 0 || strs.size() < 0)
			return null;
		String[] temp = new String[strs.size()];
		for (int i = 0; i < strs.size(); i++) {
			temp[i] = strs.get(i);
		}
		return temp;
	}

	/**
	 * 将int转换为固定长度的字符串(不足前面补零)
	 * 
	 * @param i
	 * @param length
	 * @return
	 */
	public static String intToString(int i, int length) {
		String temp = String.valueOf(i);
		StringBuffer buffer = new StringBuffer();
		int len = temp.length();
		while (len < length) {
			buffer.append("0");
			len++;
		}
		return buffer.append(temp).toString();
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * 
	 * \n 回车( ) \t 水平制表符( ) \s 空格(\u0008) \r 换行( )
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (!isNullOrEmpty(str)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

}
