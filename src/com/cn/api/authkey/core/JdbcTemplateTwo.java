package com.cn.api.authkey.core;

/**
 * JDBC模板基类
 */
public class JdbcTemplateTwo 
extends org.springframework.jdbc.core.JdbcTemplate {
	/**
	 * 返回对象ID
	 * 
	 * @return String
	 */
	public String getObjectId() {
		return ("JdbcTemplateTwo");
	}
}
