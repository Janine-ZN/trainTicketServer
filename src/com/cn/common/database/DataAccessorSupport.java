package com.cn.common.database;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cn.common.page.Page;
import com.cn.common.page.PageBuilder;
import com.cn.common.exception.DaoException;

/**
 * 数据访问基类
 */
public class DataAccessorSupport 
implements DataAccessor {

	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	/**
	 * 返回JdbcTemplate
	 * 
	 * @return JdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * 设置JdbcTemplate
	 * 
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//-----数据更新-----------------------------------------------------------
	
	/**
	 * 执行SQL语句，不返回任何结果
	 * 
	 * @param sql
	 * @throws DaoException
	 */
	public void executeNoQuery(String sql) throws DaoException {
		try {
			jdbcTemplate.execute(preExecute(sql));
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
	}

	/**
	 * 更新记录，返回更新影响的记录数
	 * 
	 * @param sql
	 * @param args
	 * @param argTypes
	 * 
	 * @return int
	 * 
	 * @throws DaoException
	 */
	public int executeUpdate(String sql, Object[] args, int[] argTypes) 
	throws DaoException {
		int affect = 0;
		try {
			affect = jdbcTemplate.update(preExecute(sql), args, argTypes);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return affect;
	}
	
	/**
	 * 更新记录，返回更新影响的记录数
	 * 
	 * @param sql
	 * @param params
	 * 
	 * @return int
	 * 
	 * @throws DaoException
	 */
	public int executeUpdate(String sql, Object ...params) 
	throws DaoException {
		int affect = 0;
		try {
			affect = jdbcTemplate.update(preExecute(sql), params);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return affect;
	} 
	
	/**
	 * 更新
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回值大于0，否则失败
	 * 
	 * @throws DaoException
	 */
	public int executeUpdate(String sql) throws DaoException {
		int affect = 0;
		try {
			affect = jdbcTemplate.update(preExecute(sql));
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return affect;
	}
	
	/**
	 * 批量更新
	 * 
	 * @param sqls
	 * 
	 * @return 语句执行情况状态集，执行成功返回值大于0，否则失败
	 * 
	 * @throws DaoException
	 */
	public int[] executeBatchUpdate(final String[] sqls) 
	throws DaoException {
		int[] affects;
		try {
			affects = jdbcTemplate.batchUpdate(preExecute(sqls));
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return affects;
	}
	/**
	 * 批量更新操作
	 * @param sql
	 * @param setter
	 * @return 语句执行情况状态集，执行成功返回值大于0，否则失败
	 * @throws DaoException
	 */
	public  int[] executeBatchUpdate(final String sql,BatchPreparedStatementSetter setter)
	throws DaoException {
		int[] affects;
		try {
			affects = jdbcTemplate.batchUpdate(preExecute(sql), setter);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return affects;
	}
	//-----数据删除-----------------------------------------------------------
	
	/**
	 * 执行删除操作
	 * 
	 * @param sql
	 * @param args
	 * @param args
	 * 
	 * @return int 执行成功返回值大于0，否则失败
	 * 
	 * @throws DaoException
	 */
	public int executeDelete(String sql, Object[] args, int[] argTypes) 
	throws DaoException {
		return executeUpdate(sql, args, argTypes);
	}

	/**
	 * 执行删除操作
	 * 
	 * @param sql
	 * @param id
	 * 
	 * @return int 执行成功返回值大于0，否则失败
	 * 
	 * @throws DaoException
	 */
	public int executeDelete(String sql, Object ...params) 
	throws DaoException {
		return executeUpdate(sql, params);
	}
	
	//-----单值查询-----------------------------------------------------------
	/**
	 * 查询单个属性-Object
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param rowMapper
	 * 
	 * @return 执行成功返回T，失败返回<code>null</code>.
	 * 
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper) 
	throws DaoException {
		List<T> results = null;
		try {
			results = queryForList(sql, args, argTypes, rowMapper);	
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
	
	/**
	 * 查询单个属性-Object
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param rowMapper
	 * 
	 * @return 执行成功返回T，失败返回<code>null</code>.
	 * 
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params) 
	throws DaoException {
		T result = null;
		try {
			result = jdbcTemplate.queryForObject(preExecute(sql), rowMapper, params);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return result;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param sql
	 * @param requiredType
	 * @return
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType) 
	throws DaoException {
		T result = null;
		try {
			result = jdbcTemplate.queryForObject(preExecute(sql), requiredType);
		} catch (EmptyResultDataAccessException erdae) {
			result = null;
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return result;
	}
	/**
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param requiredType
	 * @return
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) 
	throws DaoException {
		T result = null;
		try {
			result = jdbcTemplate.queryForObject(preExecute(sql), args, requiredType);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return result;
	}
	
	/**
	 * 查询单个字段值-字符串
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public String queryForString(String sql, String defaultValue) {
		String value = defaultValue;
		try {
			value = (String)queryForObject(sql, String.class);	
		} catch (DaoException de) {
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 查询单个字段值-字符串
	 * 
	 * @param sql
	 * @return 执行成功返回String，失败返回<code>null</code>.
	 * 
	 * @throws DaoException
	 */
	public String queryForString(String sql) throws DaoException {
		return (String)queryForObject(sql, String.class);
	}
	
	/**
	 * 查询单个字段值-字符串类型
	 * 
	 * @param defaultValue
	 * @param sql
	 * @param params
	 * 
	 * @return 执行成功返回int，失败返回<code>0</code>.
	 */
	public String queryForString(String sql, String defaultValue, Object ...params) {
		String value = defaultValue;
		try {
			value = jdbcTemplate.queryForObject(preExecute(sql), params, String.class);
		} catch (DataAccessException de) {
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 查询单个字段值-双精度型
	 * 
	 * @param defaultValue
	 * @param sql
	 * @param params
	 * 
	 * @return 执行成功返回int，失败返回<code>0</code>.
	 */
	public long queryForDouble(String sql, long defaultValue, Object ...params) {
		long value = defaultValue;
		try {
			Number number = jdbcTemplate.queryForObject(preExecute(sql), params, Long.class);
			if (number != null) {
				value = number.longValue();
			}
		} catch (EmptyResultDataAccessException erdae) {
			value = defaultValue;
		} catch (DataAccessException de) {
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回long，失败返回<code>defaultValue</code>.
	 */
	public long queryForLong(String sql, long defaultValue) {
		long value = defaultValue;
		try {
			Number number = queryForObject(sql, Long.class);
			if (number != null) {
				value = number.longValue();
			} 		
		} catch (DaoException de) {
			value = defaultValue;
		}

		return value;
	}
	
	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @return 执行成功返回long，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public long queryForLong(String sql) 
	throws DaoException {
		Number number = queryForObject(sql, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * @param params
	 * 
	 * @return 执行成功返回int，失败返回<code>0</code>.
	 */
	public int queryForInt(String sql, int defaultValue, Object ...params) {
		int value = defaultValue;
		try {
			Number number = jdbcTemplate.queryForObject(preExecute(sql), params, Integer.class);
			if (number != null) {
				value = number.intValue();
			}
		} catch (EmptyResultDataAccessException erdae) {
			value = defaultValue;
		} catch (DataAccessException de) {
			value = defaultValue;
		}
		return value;
	}
	
	/**
	 * 查询单个字段值-整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public int queryForInt(String sql, int defaultValue) {
		int value = defaultValue;
		try {
			Number number = queryForObject(sql, Integer.class);
			if (number != null) {
				value = number.intValue();
			} 	
		} catch (DaoException de) {
			value = defaultValue;
		}

		return value;
	}
	
	/**
	 * 查询单个字段值-整形
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public int queryForInt(String sql) 
	throws DaoException {
		Number number = queryForObject(sql, Integer.class);
		return (number != null ? number.intValue() : 0);
	}
	
	/**
	 * 查询单个字段值-双精度型
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public double queryForDouble(String sql, double defaultValue) {
		double value = defaultValue;
		try {
			Number number = queryForObject(sql, Double.class);
			if (number != null) {
				value = number.doubleValue();
			} 		
		} catch (DaoException de) {
			value = defaultValue;
		}

		return value;
	}
	
	/**
	 * 查询单个字段值-双精度型
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public double queryForDouble(String sql) throws DaoException {
		Number number = queryForObject(sql, Double.class);
		return (number != null ? number.doubleValue() : 0);
	}
	
	/**
	 * 查询单个字段值-双精度型
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public double queryForFloat(String sql, float defaultValue) {
		float value = defaultValue;
		try {
			Number number = queryForObject(sql, Float.class);
			if (number != null) {
				value = number.intValue();
			} 		
		} catch (DaoException de) {
			value = defaultValue;
		}

		return value;
	}
	
	/**
	 * 查询单个字段值-浮点型
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public float queryForFloat(String sql) throws DaoException {
		Number number = queryForObject(sql, Float.class);
		return (number != null ? number.floatValue() : 0);
	}

	/**
	 * 查询单条记录，不存在返回<code>null</code>.
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param argTypes
	 * @param rowMapper
	 * @return 执行成功返回T，失败返回<code>null</code>.
	 * @throws DaoException
	 */
	public <T> T queryForItem(String sql, Object[] args, 
	int[] argTypes, RowMapper<T> rowMapper) throws DaoException {
		List<T> results = queryForList(sql, args, argTypes, rowMapper);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
	
	/**
	 * 查询单条记录，不存在返回<code>null</code>.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param params
	 * @return 执行成功返回T，失败返回<code>null</code>.
	 * @throws DaoException
	 */
	public <T> T queryForItem(String sql, RowMapper<T> rowMapper, Object... params) 
	throws DaoException {
		List<T> results = jdbcTemplate.query(preExecute(sql), rowMapper, params);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
	
	/**
	 * 查询单条记录，不存在返回<code>null</code>.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return <T>
	 * @throws DaoException
	 */
	public <T> T queryForItem(String sql, RowMapper<T> rowMapper) throws DaoException {
		List<T> results = queryForList(sql, null, null, rowMapper);
		if (results != null && results.size() > 0) {
			return results.get(0);
		}
		return null;
	}
	
	//-----列表查询-----------------------------------------------------------
	/**
	 * 查询列表信息，不存在返回空列表.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * 
	 * @return List<T>
	 */
	public <T> List<T> queryForList(String sql, Object[] args, 
			int[] argTypes, RowMapper<T> rowMapper) 
	throws DaoException {
		List<T> rows = null;
		try {
			rows = jdbcTemplate.query(preExecute(sql), args, argTypes, rowMapper);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return rows == null ? new ArrayList<T>() : rows;
	}
	
	/**
	 * 查询列表信息，不存在返回空列表.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * 
	 * @return List<T>
	 * 
	 * @throws DaoException
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) 
	throws DaoException {
		List<T> rows = null;
		try {
			rows = jdbcTemplate.query(preExecute(sql), rowMapper);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return rows == null ? new ArrayList<T>() : rows;
	}
	
	/**
	 * 查询列表信息，不存在返回空列表.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * 
	 * @return List<T>
	 * 
	 * @throws DaoException
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args) 
	throws DaoException {
		List<T> rows = null;
		try {
			rows = jdbcTemplate.query(preExecute(sql), rowMapper, args);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return rows == null ? new ArrayList<T>() : rows;
	}
	
	//-----分页查询-----------------------------------------------------------
	/**
	 * 查询列表信息，不存在返回空页.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * 
	 * @return List<T>
	 * 
	 * @throws DaoException
	 */
	public <T> Page<T> queryForPage(String sql, Object[] args,
			int pageNumber, int pageSize, RowMapper<T> rowMapper) 
	throws DaoException {
		PageBuilder<T> pageBuilder = new PageBuilder<T>(this);	
		Page<T> page = pageBuilder.build(
			preExecute(sql), args, pageNumber, pageSize, rowMapper
		);	
		if (page == null) {
			page = new Page<T>();
		}
		return page;
	}
	
	/**
	 * 查询列表信息，不存在返回空页.
	 * 
	 * @param <T>
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param rowMapper
	 * @param params
	 * 
	 * @return Page<T>
	 * 
	 * @throws DaoException
	 */
	public <T> Page<T> queryForPage(String sql, int pageNumber, 
			int pageSize, RowMapper<T> rowMapper, Object ...params) 
	throws DaoException {
		PageBuilder<T> pageBuilder = new PageBuilder<T>(this);
		Page<T> page = pageBuilder.build(
			preExecute(sql), pageNumber, pageSize, rowMapper, params
		);	
		if (page == null) {
			page = new Page<T>();
		}
		return page;
	}
	
	/**
	 * 按页查询
	 * 
	 * @param <T>
	 * @param sql
	 * @param pageNumber
	 * @param pageSize
	 * @param rowMapper
	 * 
	 * @return Page<T>
	 * 
	 * @throws DaoException
	 */
	public <T> Page<T> queryForPage(String sql,int pageNumber, 
			int pageSize, RowMapper<T> rowMapper)
	throws DaoException {
		PageBuilder<T> pageBuilder = new PageBuilder<T>(this);
		Page<T> page = pageBuilder.build(
			preExecute(sql), null, pageNumber, pageSize, rowMapper
		);
		if (page == null) {
			page = new Page<T>();
		}
		return page;
	}
	
	/**
	 * 查询记录集，返回用户指定格式的数据。
	 * 
	 * @param <T>           
	 * @param sql           
	 * @param args          
	 * @param rse           
	 * @return T         
	 * @throws DaoException  
	 */
	public <T> T executeQuery(String sql, Object[] args, ResultSetExtractor<T> rse) 
	throws DaoException {
		T result = null;
		try {
			result = jdbcTemplate.query(preExecute(sql), args, rse);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return result;
	}
	
	/**
	 * 查询记录集，返回用户指定格式的数据。
	 * 
	 * @param <T>
	 * @param sql
	 * @param ree
	 * @param params
	 * 
	 * @return T
	 * 
	 * @throws DaoException
	 */
	public <T> T executeQuery(String sql, ResultSetExtractor<T> ree, Object ...params) 
	throws DaoException {
		T result = null;
		try {
			result = jdbcTemplate.query(preExecute(sql), ree, params);
		} catch (DataAccessException dae) {
			throw new DaoException(dae);
		}
		return result;
	}
	
	/**
	 * 执行SQL命令前预处理
	 * 
	 * @param sql SQL语句
	 */
	protected String preExecute(String sql) 
	throws DaoException {
		//检查/*! command */的注入
		if (sql.indexOf("/*!") > -1) {
			throw new DaoException("invalid SQL statement");
		}
		return sql;
	}
	
	/**
	 * 执行SQL命令前预处理
	 * 
	 * @param sqls SQL语句
	 */
	protected String[] preExecute(String[] sqls) 
	throws DaoException {
		for (int i = 0; i < sqls.length; i++) {
			preExecute(sqls[i]);
		}
		return sqls;
	}
	
	/**
	 * 返回对象ID
	 * 
	 * @return String
	 */
	public String getObjectId() {
		return ("DataAccessorSupport");
	}
}
