package com.cn.common.page;

/**
 * MySQL数据库分页查询语句构造器。
 */
public class MySQLPageStatementBuilder 
implements PageStatementBuilder {

	/**
	 * @see com.akson.common.page.PageStatementBuilder
	 */
	@Override
	public String buildPageStatement(String sql, 
	int startIndex, int endIndex, int pageSize) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (").append(sql);
		buffer.append(") as __t limit ").
		append(startIndex).append(",").
		append(pageSize);
		
		return buffer.toString();
	}

	/**
	 * 构造查询总数SQL
	 * @see com.akson.common.page.buildCountStatement
	 */
	@Override
	public String buildCountStatement(String sql) {
		String sqlString = sql.toLowerCase();
		int startIndex = sqlString.indexOf("from");
		int endIndex = sqlString.indexOf("order by") == -1 ? 
			sqlString.length() : sqlString.indexOf("order by");
		return "select count(*) " + sqlString.substring(startIndex, endIndex);
	}	
}
