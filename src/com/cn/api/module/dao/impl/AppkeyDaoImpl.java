package com.cn.api.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cn.common.database.RowMapper;
import com.cn.api.module.bean.Appkey;
import com.cn.api.module.dao.AppkeyDao;
import com.cn.core.DataAccessorWrapper;
import com.cn.util.StringUtil;

/** 
 * appkey实现类   
 */
public class AppkeyDaoImpl extends DataAccessorWrapper implements AppkeyDao {

	/**
	 * 插入语句
	 */
	private static final String SQL_INSERT = "insert into appkey (appkey, cookies, createTime, updateTime) values (?, ?, ?, ?)";
	
	/**
	 * 删除语句
	 */
	private static final String SQL_DELETE = "delete from appkey where appkey = ? ";

	/**
	 * 更新记录
	 */
	private static final String SQL_UPDATE = "update appkey set cookies = ?, updateTime = ? where appkey = ? ";
	
	/**
	 * 查找记录
	 */
	private static final String SQL_SELECT = "select * from appkey where appkey = ? ";
	
	@Override
	public int addItem(Appkey item) {
		Object[] params = new Object[]{
			item.getAppkey(), 		item.getCookies(),  	item.getCreateTime(),
			item.getUpdateTime()
		};
		int[] types = new int[] {
			Types.VARCHAR,			Types.VARCHAR,			Types.TIMESTAMP,
			Types.TIMESTAMP
		};
		return this.executeUpdate(SQL_INSERT, params, types);
	}

	@Override
	public int deleteItem(String id) {
		return this.executeDelete(SQL_DELETE, id);
	}

	@Override
	public int updateItem(Appkey item) {
		Object[] params = new Object[]{
			item.getCookies(),		item.getUpdateTime(),		item.getAppkey()
		};
		int[] types = new int[]{
			Types.VARCHAR,			Types.TIMESTAMP,			Types.VARCHAR
		};
		return this.executeUpdate(SQL_UPDATE, params, types);
	}

	@Override
	public Appkey findItem(String id) {
		return this.queryForItem(SQL_SELECT, new AppkeyRowViewMapper(), id);
	}

	class AppkeyRowViewMapper implements RowMapper<Appkey>{
		@Override
		public Appkey mapRow(ResultSet rs, int rowNum) throws SQLException {
			Appkey item = new Appkey();
			item.setAppkey(rs.getString("appkey"));
			item.setCookies(rs.getString("cookies"));
			item.setCreateTime(rs.getTimestamp("createTime"));
			item.setUpdateTime(rs.getTimestamp("updateTime"));
			return item;
		}
	}

	@Override
	public String findCookies(String appkey) {
		if(StringUtil.isNullOrEmpty(appkey)) {
			return "";
		}
		Appkey item = findItem(appkey);
		if(item == null) {
			return "";
		}
		return item.getCookies();
	}

	@Override
	public Appkey findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		 return this.queryForItem(SQL_SELECT, new AppkeyRowViewMapper(), id,num);
	}

}
