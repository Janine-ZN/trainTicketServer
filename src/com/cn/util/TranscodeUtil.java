package com.cn.util;

import java.io.UnsupportedEncodingException;

public class TranscodeUtil {

	/**
	 * 获得字符集编码类型
	 * 
	 * @param str
	 * @return 返回字符编码类型
	 */
	public static String getCharEncode(String str) {

		String charEncode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(charEncode), charEncode))) {
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		charEncode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(charEncode), charEncode))) {
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		charEncode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(charEncode), charEncode))) {
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		charEncode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(charEncode), charEncode))) {
				return charEncode;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 转成GBK编码
	 * 
	 * @param str
	 * @return
	 */
	public static String transcodeToGBK(String str) {
		return transcode(str, "GBK");
	}

	/**
	 * 转成UTF-8编码
	 * 
	 * @param str
	 * @return
	 */
	public static String transcodeToUTF_8(String str) {
		return transcode(str, "UTF-8");
	}

	/**
	 * 自动转码
	 * 
	 * @param str				要转码的字符串
	 * @param charEncode 		默认UTF-8
	 * @return
	 */
	public static String transcode(String str, String charEncode) {

		if (null == charEncode || "".equals(charEncode)) {
			charEncode = "UTF-8";
		}

		String temp = "";
		try {
			String code = getCharEncode(str);
			//System.out.println("编码：" + code);
			temp = new String(str.getBytes(code), charEncode);
		} catch (UnsupportedEncodingException e) {
			//System.out.println("转码异常：" + e.getMessage());
		}
		return temp;
	}

}
