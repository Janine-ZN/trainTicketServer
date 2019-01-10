package com.cn.core;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.cn.web.RequestControllerSupport;

/**
 * Web请求访问包裹器，在常用Web请求处理方法的基础上新增了个性化的处理方法
 */
public abstract class RequestControllerWrapper 
extends RequestControllerSupport {

	/**
	 * 缺省异常控制器地址
	 */
	public final static String DEFAULT_EXCEPTIONCONTROLLER 
		= "system/error_report";
	
	/**
	 * 缺省结果显示控制器地址
	 */
	public final static String DEFAULT_RESULTCONTROLLER 
		= "system/result_report";
	
	/**
	 * 异常控制器地址
	 */
	protected String exceptionController;
	
	/**
	 * 结果显示控制器地址
	 */
	protected String resultController;
	
	/**
	 * 构造
	 */
	public RequestControllerWrapper() {
		super();
		this.exceptionController = 
			RequestControllerWrapper.DEFAULT_EXCEPTIONCONTROLLER;
		this.resultController =  
			RequestControllerWrapper.DEFAULT_RESULTCONTROLLER;
	}
	
	/**
	 * @return 异常控制器地址
	 */
	@Override
	public String getExceptionController() {
		if (this.exceptionController == null) {
			return RequestControllerWrapper.DEFAULT_EXCEPTIONCONTROLLER;
		}
		return this.exceptionController;
	}
	
	/**
	 * 设置异常控制器地址
	 * 
	 * @param exceptionController 控制器地址
	 * 
	 * @return 异常控制器地址
	 */
	@Override
	public void setExceptionController(String exceptionController) {
		this.exceptionController = exceptionController;
	}
	
	/**
	 * @return 返回结果显示控制器地址
	 */
	@Override
	public String getResultController() {
		if (this.resultController == null) {
			return RequestControllerWrapper.DEFAULT_RESULTCONTROLLER;
		}
		return resultController;
	}

	/**
	 * 设置结果显示控制器地址
	 * 
	 * @param resultController 控制器地址
	 */
	@Override
	public void setResultController(String resultController) {
		this.resultController = resultController;
	}
	
	/**
	 * 预处理Web请求
	 * 
	 * @param request
	 */
	public void preRequest(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
	}
}
