package com.cn.common.database;

/**
 * JDBC模板基类
 */
public class JdbcTemplate 
extends org.springframework.jdbc.core.JdbcTemplate {
	/**
	 * 返回对象ID
	 * 
	 * @return String
	 */
	public String getObjectId() {
		return ("JdbcTemplate");
	}
}
