package com.cn.api.authkey.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cn.api.authkey.bean.Authkey;
import com.cn.api.authkey.core.DataAccessorWrapperTwo;
import com.cn.api.authkey.dao.AuthkeyDao;
import com.cn.common.database.RowMapper;

public class AuthkeyDaoImpl extends DataAccessorWrapperTwo implements AuthkeyDao {

	/**
	 * 判断key的状态
	 * 
	 * @param 	authkey
	 * @return	0、key可用，1、key不存在，2、key存在，但已过期
	 */
	@Override
	public int findAuthkeyStatus(String authkey) {
		int status = 0;
		/***
		 * 此处写sql语句，从数据库查询authkey的状态，加以判断，并返回结果
		 */
		String sql = "select * from authkey where authkey = '" + authkey + "'";
		
		Authkey item = this.queryForItem(sql, new AuthkeyRowViewMapper());
		
		return status;
	}

	class AuthkeyRowViewMapper implements RowMapper<Authkey>{
		@Override
		public Authkey mapRow(ResultSet rs, int rowNum) throws SQLException {
			Authkey item = new Authkey();
			item.setAuthkey(rs.getString("authkey"));
			return item;
		}
	}
	
}
