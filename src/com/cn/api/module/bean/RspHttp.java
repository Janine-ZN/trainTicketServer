package com.cn.api.module.bean;

/**
 * 本地调用12306返回的结果
 */
public class RspHttp {

	public int code;// 返回状态码
	public String result;// 返回结果
	public String cookie;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

}
