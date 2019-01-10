package com.cn.common.page;

/**
 * 记录过滤器。
 */
public interface PageFilter<T> {


	/**
	 * 过滤记录
	 * 
	 * @param item
	 * @param argument
	 * 
	 * @return boolean
	 */
	public boolean filter(T item, Object argument);
}
