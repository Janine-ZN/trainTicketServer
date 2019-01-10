package com.cn.util;

import org.apache.commons.httpclient.HttpStatus;

import com.cn.api.module.bean.RspHttp;

public class DoUtil {

	/**
	 * GET请求
	 * 
	 * @param url			请求地址
	 * @param param			请求参数
	 * @return
	 */
	public static String makeGet(String url, String param, String cookie) {
		String result = "";
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		if(rspHttp.getCode() == HttpStatus.SC_OK) {
			result = rspHttp.getResult(); //从12306接口返回的结果
		}
		return result;
	}
	
	/**
	 * POST请求
	 * 
	 * @param url			请求地址
	 * @param param			请求参数
	 * @return
	 */
	public static String makePost(String url, String param, String cookie) {
		String result = "";
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		if(rspHttp.getCode() == HttpStatus.SC_OK) {
			result = rspHttp.getResult(); //从12306接口返回的结果
		}
		return result;
	}
	
}
