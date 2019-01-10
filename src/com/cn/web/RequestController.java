package com.cn.web;

import org.springframework.ui.Model;

/**
 * Web页面请求处理接口
 */
public interface RequestController {

	/**
	 * 映射文件完整路径
	 * 
	 * @param url 待映射的地址
	 * 
	 * @return String 完整地址
	 */
	public String mapPath(String url);
	
	/**
	 * 获取当前模块路径
	 * 
	 * @return String 地址
	 */
	public abstract String getModulePath();
	
	/**
	 * 注册记录-单条记录
	 * 
	 * @param model
	 * @param item
	 */
	public void registerItemParameter(Model model, Object item);
	
	/**
	 * 注册记录-列表
	 * 
	 * @param model
	 * @param item
	 */
	public void registerListParameter(Model model, Object item);
	
	/**
	 * 注册URL参数
	 * 
	 * @param model
	 * @param furl
	 * @param purl
	 */
	public void registerUrlParameters(Model model, 
	String furl, String purl);
	/**
	 * 返回转向页面路径
	 * 
	 * @param url 地址
	 * 
	 * @return String 控制器地址
	 */
	public String getForward(String url);
	
	/**
	 * 执行结果处理，返回控制器地址。
	 * 
	 * @param model  	模型
	 * @param result 	执行结果
	 * @param message 	信息
	 * @param furl   	起始调用页面
	 * @param purl   	上个调用页面
	 */
	public String processResult(Model model, 
			String result, String message, String furl, String purl);
	
	/**
	 * @return 异常控制器地址
	 */
	public String getExceptionController();
	
	/**
	 * 设置异常控制器地址
	 * 
	 * @param exceptionController 控制器地址
	 * 
	 * @return 异常控制器地址
	 */
	public void setExceptionController(String exceptionController);
	
	/**
	 * @return 返回结果显示控制器地址
	 */
	public String getResultController();

	/**
	 * 设置结果显示控制器地址
	 * 
	 * @param resultController 控制器地址
	 */
	public void setResultController(String resultController);
}
