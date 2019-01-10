package com.cn.common.database;

/**
 * 数据访问基本接口
 */
public interface DaoOperations<T> {
	/**
	 * 新增记录
	 * 
	 * @param item 记录
	 * 
	 * @return int 大于0更新成功，否则失败
	 */
	public abstract int addItem(T item);
	
	/**
	 * 删除记录
	 * 
	 * @param id 记录ID
	 * 
	 * @return int 大于0更新成功，否则失败
	 */
	public abstract int deleteItem(String id);
	
	/**
	 * 更新记录
	 * 
	 * @param item 记录
	 * 
	 * @return 大于0更新成功，否则失败
	 */
	public abstract int updateItem(T item);
	
	/**
	 * 按照ID查询记录
	 * 
	 * @param id 记录ID
	 * 
	 * @return 不存在返回<code>null</code>
	 */
	public abstract T findItem(String id);
/*
 * 
 * 按照id,num查询
 */
	public abstract T findItemflag(String id, String num);
	
}
