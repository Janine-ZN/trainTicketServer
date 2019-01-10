package com.cn.core;

import com.cn.common.database.DataAccessorSupport;
import com.cn.util.DatabaseUtil;

/**
 * 数据库访问包裹器，在公共数据库访问方法上增加了个性化的数据库
 * 访问方法、工具。
 */
public class DataAccessorWrapper 
extends DataAccessorSupport {

	/**
	 * 按照ID数组批量删除记录
	 * 
	 * @param table
	 * @param keyField
	 * @param ids
	 * 
	 * @return 删除记录个数
	 */
	public int executeBatchDelete(String table, String keyField, String []ids) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table).
		append(" where ").append(keyField).append(" in (").
		append(DatabaseUtil.buildInStatement(ids, true)).append(")");
		return this.executeUpdate(buffer.toString());
	}
	
	/**
	 * 按照ID数组批量删除记录
	 * 
	 * @param table
	 * @param keyField
	 * @param ids
	 * 
	 * @return 删除记录个数
	 */
	public int executeBatchDelete(String table, String keyField, int []ids) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("delete from ").append(table).
		append(" where ").append(keyField).append(" in (").
		append(DatabaseUtil.buildInStatement(ids)).append(")");
		return this.executeUpdate(buffer.toString());
	}
}
