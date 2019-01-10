package com.cn.api.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cn.common.database.RowMapper;
import com.cn.core.DataAccessorWrapper;
import com.cn.util.StringUtil;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.bean.TrainNo;
import com.cn.api.module.bean.Appkey;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.dao.TrainCodeDao;
import com.cn.api.module.dao.TrainNoDao;
import com.cn.api.module.dao.impl.TrainCodeDaoImpl.TrainCodeRowViewMapper;

/** 
 * TrainCode实现类   
 */
public class TrainNoDaoImpl extends DataAccessorWrapper implements TrainNoDao {

	/**
	 * 插入语句
	 */
	private static final String SQL_INSERT = "insert into train_no (id, train_no, updatetime,createtime,booktime ,train_nu) values (?,?, ?, ?,?,?)";
	/**
	 * 删除语句
	 */
//	private static final String SQL_DELETE = "delete from appkey where appkey = ? ";
//	private static final String SQL_DELETE = "delete from TrainCode where orderNumber = ? ";

	/**
	 * 更新记录
	 */
	private static final String SQL_UPDATE = "update train_no set updatetime = ?,booktime=?,train_nu=? where train_no = ? ";
	
	
	/**
	 * 查找记录
	 */
	//private static final String SQL_SELECT = "select * from appkey where appkey = ? ";
	private static final String SQL_SELECT = "select * from train_no where train_no = ? ";

	@Override
	public int addItem(TrainNo item) {
		Object[] params = new Object[]{
		item.getId(),
		item.getTrain_no(),
		item.getUpdateTime(),
		item.getCreateTime(),
		item.getBookTime(),
		item.getTrain_nu()
	};
	int[] types = new int[] {
			Types.INTEGER,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.TIMESTAMP,
			Types.VARCHAR,
			Types.VARCHAR
			};
	return this.executeUpdate(SQL_INSERT, params, types);
	}

//	@Override
//	public int deleteItem(String id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public int updateItem(TrainNo item) {
		Object[] params = new Object[]{
				item.getId(),		item.getTrain_no(),		item.getUpdateTime() ,item.getCreateTime(),item.getBookTime(),item.getTrain_nu()
			};
			int[] types = new int[]{
				Types.INTEGER,		Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP,Types.VARCHAR,Types.VARCHAR
			};
			return this.executeUpdate(SQL_UPDATE, params, types);
	}


	class TrainNoRowViewMapper implements RowMapper<TrainNo>{
	@Override
	public TrainNo mapRow(ResultSet rs, int rowNum) throws SQLException {
		TrainNo item = new TrainNo();
		
		item.setId(rs.getInt("id"));
		item.setTrain_no(rs.getString("train_no"));
		item.setUpdateTime(rs.getString("updatetime"));
		item.setCreateTime(rs.getTimestamp("createtime"));
		item.setBookTime(rs.getString("booktime"));
		item.setTrain_nu(rs.getString("train_nu"));
		return item;
	}
}

	@Override
	public String findTrain(String name) {
		if(StringUtil.isNullOrEmpty(name)) {
		return "";
	}
	TrainNo item = findItem(name);
	if(item == null) {
		return "";
	}
	return item.getTrain_nu();
	}



	@Override
	public TrainNo findItem(String id) {
		// TODO Auto-generated method stub
		 return this.queryForItem(SQL_SELECT, new TrainNoRowViewMapper(), id);
	}

	@Override
	public TrainNo findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}


	
}