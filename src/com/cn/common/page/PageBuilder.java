package com.cn.common.page;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.cn.common.database.DataAccessor;
import com.cn.common.database.ResultSetExtractor;
import com.cn.common.database.RowMapper;

/**
 * <p>分页查询构造器。</p>
 * <p>
 * 	<b>版本历史：<br/></b>
 *  <li>v1.0.0.120214/XML/2012-02-14：创建</li>
 * </p>
 * @version v1.0.0.120214
 * @author XML
 * @since 2012-02-14
 */
public class PageBuilder<T> {
	
	/**
	 * 日志
	 */
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(PageBuilder.class);
	
	/**
	 * 记录总数
	 */
	protected int rowCount;
	
	/**
	 * 记录起始位置
	 */
	protected int startIndex;
	
	/**
	 * 记录结束为止
	 */
	protected int endIndex;
	
	/**
	 * 当前页面
	 */
	protected int pageNumber;
	
	/**
	 * 页面大小
	 */
	protected int pageSize;
	
	/**
	 * 页面总数
	 */
	protected int pageCount;
	
	/**
	 * 查询语句
	 */
	protected String sqlString;
	
	/**
	 * 分页查询语句构造器
	 */
	protected PageStatementBuilder statementBuilder;
	
	/**
	 * 数据操纵对象
	 */
	protected DataAccessor dataAccessor;
	
	/**
	 * 构造
	 */
	public PageBuilder() {
		this(null, null);
	}
	
	/**
	 * 构造
	 * 
	 * @param dataAccessor
	 */
	public PageBuilder(DataAccessor dataAccessor) {
		this(dataAccessor, null);
	}
	
	/**
	 * 构造
	 * 
	 * @param dataAccessor
	 * @param statementBuilder
	 */
	public PageBuilder(DataAccessor dataAccessor, PageStatementBuilder statementBuilder) {
		this.dataAccessor = dataAccessor;
		this.statementBuilder = statementBuilder;
		this.rowCount = 0;
		this.startIndex = 0;
		this.endIndex = 0;
		this.pageNumber = 0;
		this.pageSize = 0;
		this.pageCount = 0;
		this.sqlString = null;
	}
	
	/**
	 * 对List记录集分页显示
	 * 
	 * @param keyword
	 * @param pageNumber
	 * @param pageSize
	 * @param list
	 * @param pageFilter
	 * 
	 * @return Page
	 */
	public static <T> Page<T> findPage(String keyword, 
	int pageNumber, int pageSize, List<T> list, PageFilter<T> pageFilter) {
		
		Page<T> page = new Page<T>();
		List<T> items = new ArrayList<T>();
		
        for (int i = 0; i < list.size(); i ++) {
        	T item = list.get(i);
        	if (pageFilter == null) {
        		items.add(item);
        	} else {
	        	if (pageFilter.filter(item, keyword)) {
	        		items.add(item);   
	        	} 
        	}
        };
        
		int pageCount = 0;
		if (items.size() > 0) {
            if (items.size() % pageSize == 0 ) {
	            pageCount = items.size() / pageSize;
            } else {
	            pageCount = items.size() / pageSize + 1;
            }
	    }
        if (pageNumber < 1) {
        	pageNumber = 1;
        }
        if (pageNumber > pageCount) {
        	pageNumber = pageCount;
        }  
        int start = 0;
        int stop = 0;
        
        if (pageNumber > 0) {
	        start = (pageNumber - 1) * pageSize;
	        stop = start + pageSize;
	        if (stop > items.size()) { 
	            stop = items.size();  
	        }
        } else {
            stop = items.size();
        }
        page.setPageCount(pageCount);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        page.setRecordCount(items.size());
        
        for (int i = start; i < stop; i ++) {
        	page.getItems().add(items.get(i));
        };
        
        return page;
	}
	
	/**
	 * 返回分页语句创建器
	 * 
	 * @return the statementBuilder
	 */
	public PageStatementBuilder getPageStatementBuilder() {
		if (statementBuilder == null)
			statementBuilder = new MySQLPageStatementBuilder();
		return statementBuilder;
	}

	/**
	 * 设置分页语句创建器
	 * 
	 * @param statementBuilder the statementBuilder to set
	 */
	public void setPageStatementBuilder(PageStatementBuilder statementBuilder) {
		this.statementBuilder = statementBuilder;
	}

	/**
	 * 返回数据库操作器
	 * 
	 * @return the dataAccessor
	 */
	public DataAccessor getDataAccessor() {
		return this.dataAccessor;
	}

	/**
	 * 设置数据库操作器
	 * 
	 * @param dataAccessor the dataAccessor to set
	 */
	public void setDataAccessor(DataAccessor dataAccessor) {
		this.dataAccessor = dataAccessor;
	}
	
	/**
	 * 获取指定页数据
	 * 
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param rowMapper
	 * @param params
	 * 
	 * @return Page<T>
	 */
	public Page<T> build(String sql, int pageNumber, 
		int pageSize, RowMapper<T> rowMapper, Object ...params) {
		int count = this.dataAccessor.queryForInt(
			(getPageStatementBuilder().buildCountStatement(sql)), 0, params
		); 
		return build(sql, pageNumber, pageSize, count, rowMapper, params);
	}
	
	/**
	 * 获取指定页数据
	 * 
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param rowCount
	 * @param rowMapper
	 * @param params
	 * 
	 * @return Page<T>
	 */
	public Page<T> build(final String sql, final int pageNumber, final int pageSize, 
	final int rowCount, final RowMapper<T> rowMapper, Object ...params) {
		this.sqlString = sql;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		calculate();
		return extract(rowMapper, params);
	}
	
	/**
	 * 获取指定页数据
	 * 
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param pageNumber
	 * @param pageSize
	 * @param rowMapper
	 * 
	 * @return Page<T>
	 */
	public Page<T> build(final String sql, final Object args[], 
			final int[] argTypes, final int pageNumber, 
			final int pageSize, final RowMapper<T> rowMapper){
		int count = this.dataAccessor.queryForInt(
			(getPageStatementBuilder().buildCountStatement(sql)),
			0, args, argTypes
		); 
		return build(sql, args, argTypes, pageNumber, pageSize, count, rowMapper);
	}
	
	/**
	 * 获取指定页数据
	 * 
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param pageNumber
	 * @param pageSize
	 * @param rowCount
	 * @param rowMapper
	 * 
	 * @return Page<T>
	 */
	private Page<T> build(final String sql, final Object args[], 
		final int[] argTypes, final int pageNumber, 
		final int pageSize, final int rowCount, final RowMapper<T> rowMapper) {
		
		this.sqlString = sql;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.rowCount = rowCount;
		calculate();
		return extract(args, rowMapper);
	}
	
	/**
	 * 获取指定页数据
	 * 
	 * @param sql
	 * @param args
	 * @param pageNumber
	 * @param pageSize
	 * @param rowMapper
	 * 
	 * @return Page<T>
	 */
	public Page<T> build(final String sql, final Object args[], final int pageNumber, 
		final int pageSize, final RowMapper<T> rowMapper) {
		
		this.sqlString = sql;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.rowCount = this.dataAccessor.queryForInt(
			(getPageStatementBuilder().buildCountStatement(sql)), 0, args
		); 
		calculate();
		return extract(rowMapper, args);
	}
	
	/**
	 * 计算分页相关参数
	 */
	protected void calculate() {
		//开始索引位置
		startIndex = (pageNumber - 1)*pageSize;
		//最后一条记录的索引位置
		endIndex = 0;
		pageCount = 0;
		if (rowCount % pageSize == 0) {
			pageCount = rowCount / pageSize;
	    } else {
	    	pageCount = (rowCount / pageSize) + 1;
	    }
		if (rowCount < pageSize){
			endIndex = rowCount;
		} else if ((pageNumber == pageCount && rowCount % pageSize == 0) || pageNumber < pageCount) {
			endIndex = pageNumber*pageSize;
		} else if ((pageNumber == pageCount && rowCount % pageSize != 0)) {
			endIndex = rowCount;
		}
	}
	
	/**
	 * 析取指定页面
	 * 
	 * @param args
	 * @param rowMapper
	 * 
	 * @return Page
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	protected Page<T> extract(final Object args[], final RowMapper<T> rowMapper){
		
		String sql = getPageStatementBuilder().buildPageStatement(
			sqlString, startIndex, endIndex, pageSize
		);
		final Page<T> page = new Page<T>(); 
		page.setPageCount(pageCount);
		page.setRecordCount(rowCount);
		
		this.dataAccessor.executeQuery(sql, args, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException {
				final List<T> items = page.getItems(); 
	    		int currentRow = 0;   
	    		while (rs.next()) {   
	    			items.add(rowMapper.mapRow(rs, currentRow));     
                    currentRow++;   
                }   
	    		return page;
			}
		});
		return page;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	protected Page<T> extract(final RowMapper<T> rowMapper, Object ...params) {
		
		String sql = getPageStatementBuilder().buildPageStatement(
			sqlString, startIndex, endIndex, pageSize
		);
		final Page<T> page = new Page<T>(); 
		page.setPageCount(pageCount);
		page.setRecordCount(rowCount);
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		
		this.dataAccessor.executeQuery(sql, new ResultSetExtractor() {
			public Object extractData(ResultSet rs) throws SQLException {
				List<T> items = page.getItems(); 
	    		int currentRow = 0;   
	    		while (rs.next()) {   
	    			items.add(rowMapper.mapRow(rs, currentRow));     
                    currentRow++;   
                }   
	    		return page;
			}
		}, params);
		
		return page;
	}
}
