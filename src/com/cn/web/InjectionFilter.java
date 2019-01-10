package com.cn.web;

import com.cn.util.StringUtil;

/**
 * 防注入过滤类
 */
public class InjectionFilter {

	/**
	 * MySQL数据库过滤关键词
	 */
	public static final String KW_MYSQL = 
		"'|,|exec |insert |delete |update |truncate ";
	
	/**
	 * 识别模式（以"|"分隔的字符串）
	 */
	private String pattern;
	
	/**
	 * 构造
	 */
	public InjectionFilter() {
		this(InjectionFilter.KW_MYSQL);
	}
	
	/**
	 * 构造
	 * 
	 * @param pattern 识别模式（以"|"分隔的字符串）
	 */
	public InjectionFilter(String pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * 检查SQL语句
	 * 
	 * @param source
	 * @return
	 */
	public String checkSQL(String source) {
		if (source == null || "".equals(source)) {
			return source;
		}
		String filterString = this.getPattern();
		if (filterString == null || "".equals(filterString)) {
			return source;
		}
		String[] strings = filterString.split("\\|");
		String newString = source;
		for (int i = 0 ; i < strings.length; i++) {
			//替换敏感字符串为空字符串，不区分大小写
			newString = StringUtil.replaceNoCase(newString, strings[i], "");
		}
		return newString;
	}
}
