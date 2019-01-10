package com.cn.common.database;

import java.util.List;
import com.cn.common.exception.DaoException;
import com.cn.common.page.Page;

/**
 * 数据访问接口
 */
public interface DataAccessor {
	/**
	 * 执行SQL语句，不返回任何结果
	 * 
	 * @param sql
	 * @throws DaoException
	 */
	public void executeNoQuery(String sql) 
	throws DaoException;

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
	throws DaoException;
	
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
	throws DaoException;
	
	/**
	 * 更新
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回值大于0，否则失败
	 * 
	 * @throws DaoException
	 */
	public int executeUpdate(String sql) 
	throws DaoException;
	
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
	throws DaoException;
	
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
	throws DaoException;

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
	throws DaoException;
	
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
	public <T> T queryForObject(String sql, Object[] args, 
	int[] argTypes, RowMapper<T> rowMapper) 
	throws DaoException;
	
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
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper,
	Object... params) throws DaoException;
	
	
	/**
	 * 
	 * @param <T>
	 * @param sql
	 * @param requiredType
	 * @return
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql, Class<T> requiredType) 
	throws DaoException;
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param requiredType
	 * @return
	 * @throws DaoException
	 */
	public <T> T queryForObject(String sql,Object[] params,Class<T> requiredType)
	throws DaoException;
	
	/**
	 * 查询单个字段值-字符串
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public String queryForString(String sql, String defaultValue);
	
	/**
	 * 查询单个字段值-字符串
	 * 
	 * @param sql
	 * @return 执行成功返回String，失败返回<code>null</code>.
	 * @throws DaoException
	 */
	public String queryForString(String sql) 
	throws DaoException;
	
	/**
	 * 查询单个字段值-双精度型
	 * 
	 * @param defaultValue
	 * @param sql
	 * @param params
	 * 
	 * @return 执行成功返回int，失败返回<code>0</code>.
	 * @throws DaoException
	 */
	public long queryForDouble(String sql, long defaultValue, Object ...params)
	throws DaoException;
	
	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回long，失败返回<code>defaultValue</code>.
	 */
	public long queryForLong(String sql, long defaultValue);
	
	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @return 执行成功返回long，失败返回<code>0</code>.
	 * @throws DaoException
	 */
	public long queryForLong(String sql) throws DaoException;

	/**
	 * 查询单个字段值-长整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * @param params
	 * 
	 * @return 执行成功返回int，失败返回<code>0</code>.
	 */
	public int queryForInt(String sql, int defaultValue, Object ...params);
	
	/**
	 * 查询单个字段值-整形
	 * 
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public int queryForInt(String sql, int defaultValue);
	
	/**
	 * 查询单个字段值-整形
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public int queryForInt(String sql) throws DaoException;

	/**
	 * 查询单个字段值-双精度型
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public double queryForDouble(String sql, double defaultValue);
	
	/**
	 * 查询单个字段值-双精度型
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public double queryForDouble(String sql) throws DaoException;
	
	/**
	 * 查询单个字段值-双精度型
	 * @param sql
	 * @param defaultValue
	 * 
	 * @return 执行成功返回T，失败返回<code>defaultValue</code>.
	 */
	public double queryForFloat(String sql, float defaultValue);
	
	/**
	 * 查询单个字段值-浮点型
	 * 
	 * @param sql
	 * 
	 * @return 执行成功返回T，失败返回<code>0</code>.
	 * 
	 * @throws DaoException
	 */
	public float queryForFloat(String sql)
	throws DaoException;

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
	int[] argTypes, RowMapper<T> rowMapper) 
	throws DaoException;
	
	/**
	 * 查询单条记录，不存在返回<code>null</code>.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return 执行成功返回T，失败返回<code>null</code>.
	 * @throws DaoException
	 */
	public <T> T queryForItem(String sql, RowMapper<T> rowMapper, Object... args) 
	throws DaoException;
	
	/**
	 * 查询单条记录，不存在返回<code>null</code>.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return <T>
	 * @throws DaoException
	 */
	public <T> T queryForItem(String sql, RowMapper<T> rowMapper) 
	throws DaoException;
	
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
	throws DaoException;
	
	/**
	 * 查询列表信息，不存在返回空列表.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @return List<T>
	 * @throws DaoException
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper) 
	throws DaoException;
	
	/**
	 * 查询列表信息，不存在返回空列表.
	 * 
	 * @param <T>
	 * @param sql
	 * @param rowMapper
	 * @param args
	 * @return List<T>
	 * @throws DaoException
	 */
	public <T> List<T> queryForList(String sql, RowMapper<T> rowMapper, Object... args) 
	throws DaoException;
	
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
	throws DaoException;
	
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
	throws DaoException;
	
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
			int pageSize, RowMapper<T> rowMapper) throws DaoException;
	
	/**
	 * 查询记录集，返回用户指定格式的数据。
	 * 
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param rse
	 * 
	 * @return T
	 * 
	 * @throws DaoException
	 */
	public <T> T executeQuery(String sql, Object[] args, ResultSetExtractor<T> rse) 
	throws DaoException;
	
	/**
	 * 查询记录集，返回用户指定格式的数据。
	 * 
	 * @param <T>
	 * @param sql
	 * @param ree
	 * @param params
	 * @return T
	 * @throws DaoException
	 */
	public <T> T executeQuery(String sql, ResultSetExtractor<T> ree, Object ...params) 
	throws DaoException;
}
