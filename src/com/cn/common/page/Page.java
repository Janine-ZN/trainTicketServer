package com.cn.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页管理器。
 */
public class Page<E> {
	
	/**
	 * 默认页面大小
	 */
	public static int DEFAULT_PAGESIZE = 10;
	
	/**
	 * 页面大小
	 */
	private int pageSize;
	
	/**
	 * 当前页码
	 */
	private int pageNumber;
	
	/**
	 * 总记录数
	 */
	private int recordCount;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 记录集
	 */
	private List<E> items = new ArrayList<E>();
	
	public Page() {
		this.pageNumber = 0;
		this.recordCount = 0;
		this.pageSize = DEFAULT_PAGESIZE;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public List<E> getItems() {
		return items;
	}
	
	public void setItems(List<E> items) {
		this.items = items;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
