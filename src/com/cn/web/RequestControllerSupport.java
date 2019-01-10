package com.cn.web;

import org.springframework.ui.Model;

/**
 * Controller基类
 */
public abstract class RequestControllerSupport 
implements RequestController {

	/**
	 * 构造
	 */
	public RequestControllerSupport() {
	}
	
	/**
	 * 映射文件完整路径
	 * 
	 * @param url
	 * 
	 * @return String
	 */
	@Override
	public String mapPath(String url) {
		String path = getModulePath();
		if (url == null) {
			return path;
		}
		if (path == null) {
			return url;
		}
		String newUrl = url;
		if (path.endsWith("/")) {
			path = path.substring(0, path.length()-2);
		}
		if (newUrl.startsWith("/")) {
			newUrl = newUrl.substring(1);
		}
		return path+"/"+newUrl;
	}
	
	/**
	 * 获取当前模块路径
	 * 
	 * @return String
	 */
	@Override
	public abstract String getModulePath();
	
	
	/**
	 * 执行结果处理，返回控制器地址。
	 * 
	 * @param model  	模型
	 * @param result 	执行结果
	 * @param message 	信息
	 * @param furl   	起始调用页面
	 * @param purl   	上个调用页面
	 */
	@Override
	public String processResult(Model model, 
			String result, String message, String furl, String purl) {
		this.registerUrlParameters(model, furl, purl);
		return this.getResultController();
	}
	
	/**
	 * 注册记录-单条记录
	 * 
	 * @param model
	 * @param item
	 */
	@Override
	public void registerItemParameter(Model model, Object item) {
		model.addAttribute("_item", item);
	}
	
	/**
	 * 注册记录-列表
	 * 
	 * @param model
	 * @param list
	 */
	@Override
	public void registerListParameter(Model model, Object list) {
		model.addAttribute("_list", list);
	}
	
	/**
	 * 注册URL参数
	 * 
	 * @param model
	 * @param furl
	 * @param purl
	 */
	@Override
	public void registerUrlParameters(Model model, String furl, String purl) {
		model.addAttribute("furl", furl);
		model.addAttribute("purl", purl);
	}
	
	/**
	 * 返回转向页面路径
	 * 
	 * @param url 地址
	 * 
	 * @return String 控制器地址
	 */
	@Override
	public String getForward(final String url) {
		String path = getModulePath();
		if (url == null) {
			return path;
		}
		if (path == null) {
			return url;
		}
		String newUrl = url;
		if (path.endsWith("/")) {
			path = path.substring(0, path.length()-2);
		}
		if (newUrl.startsWith("/")) {
			newUrl = newUrl.substring(1);
		}
		return path+"/"+newUrl;
	}
	
	/**
	 * 执行异常处理，返回处理控制器地址
	 * 
	 * @param model  	模型
	 * @param result 	执行结果
	 * @param message 	信息
	 * @param furl   	起始调用页面
	 * @param purl   	上个调用页面
	 * @return 控制器地址
	 */
	public String processException(Model model, String result, 
		String message, String furl, String purl) {
		this.registerUrlParameters(model, furl, purl);
		return this.getExceptionController();
	}
	/**
	 * @return 异常控制器地址
	 */
	@Override
	public abstract String getExceptionController();
	
	/**
	 * 设置异常控制器地址
	 * 
	 * @param exceptionController 控制器地址
	 * 
	 * @return 异常控制器地址
	 */
	@Override
	public abstract void setExceptionController(String exceptionController);
	
	
	/**
	 * @return 返回结果显示控制器地址
	 */
	@Override
	public abstract String getResultController();

	/**
	 * 设置结果显示控制器地址
	 * 
	 * @param resultController 控制器地址
	 */
	@Override
	public abstract void setResultController(String resultController);

	/**
	 * 返回对象ID
	 * 
	 * @return String
	 */
	public String getObjectId() {
		return ("RequestControllerSupport");
	}
}
