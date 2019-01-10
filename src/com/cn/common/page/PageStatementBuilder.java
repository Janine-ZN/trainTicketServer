package com.cn.common.page;

/**
 * 数据库分页查询语句构造器接口。
 */
public interface PageStatementBuilder {

	/**
	 * 创建分页语句
	 * 
	 * @param sql
	 * @param startIndex
	 * @param endIndex
	 * @param pageSize
	 * 
	 * @return String
	 */
	public abstract String buildPageStatement(
		String sql, int startIndex, int endIndex, int pageSize
	);
	
	/**
	 * 创建记录集统计语句
	 * 
	 * @param sql
	 * 
	 * @return String
	 */
	public abstract String buildCountStatement(
		String sql
	);
}
