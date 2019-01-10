package com.cn.common.page;

/**
 * Oracle数据库分页查询语句构造器。
 */
public class OraclePageStatementBuilder 
implements PageStatementBuilder {
	
	/**
	 * @see com.akson.common.PageStatementBuilder.buildPageStatement
	 */
	@Override
	public String buildPageStatement(String sql, 
	int startIndex, int endIndex, int pageSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (select __t.*,ROWNUM ROWINDEX from (").
		append(sql);buffer.append(") __t where ROWNUM <= ").
		append(endIndex).append(" ) where ROWINDEX > ").append(startIndex);
		return buffer.toString();
	}

	/**
	 * @see com.akson.common.PageStatementBuilder.buildCountStatement
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
