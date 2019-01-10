package com.cn.api.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cn.common.database.RowMapper;
import com.cn.core.DataAccessorWrapper;
import com.cn.util.StringUtil;
import com.cn.api.module.bean.SchoolCode;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.dao.SchoolCodeDao;
import com.cn.api.module.dao.TrainCodeDao;

/** 
 * TrainCode实现类   
 */
public class SchoolCodeDaoImpl extends DataAccessorWrapper implements SchoolCodeDao {

	/**
	 * 插入语句
	 */
//	private static final String SQL_INSERT = "insert into appkey (appkey, cookies, createTime, updateTime) values (?, ?, ?, ?)";
	private static final String SQL_INSERT = "insert into schoolcode (id, chineseName, allPin , simplePin,stationTelecode ) values (?, ?, ?, ?,?)";
	private static final String SQL_INSERT_City = "insert into citycode (id, chineseName, allPin , simplePin,stationTelecode ) values (?, ?, ?, ?,?)";

	//	private static final String SQL_INSERT = "insert into TrainCode (id,createTime, passengerName, idType, idNo,phoneNumber,ticketType,seatType,coachName,seatName,ticketPrice,stationTrain,startStation,endStation,startTime,arriveTime,startTrain,orderState,orderNumber,updateTime) values (?,?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?, ?,?)";
	/**
	 * 删除语句
	 */
//	private static final String SQL_DELETE = "delete from appkey where appkey = ? ";
//	private static final String SQL_DELETE = "delete from TrainCode where orderNumber = ? ";

	/**
	 * 更新记录
	 */
//	private static final String SQL_UPDATE = "update TrainCode set orderState = ?, updateTime = ? where orderNumber = ? ";
	
	
	/**
	 * 查找记录
	 */
	//private static final String SQL_SELECT = "select * from appkey where appkey = ? ";
	private static final String SQL_SELECT = "select * from schoolcode where chineseName = ? ";
	private static final String SQL_SELECT_City = "select * from citycode where chineseName = ? ";
	
	@Override
	public int addItem(SchoolCode item) {
		Object[] params = new Object[]{
		item.getId(),
		item.getChineseName(),
		item.getAllPin(),
		item.getSimplePin(),
		item.getStationTelecode()
	};
	int[] types = new int[] {
			Types.INTEGER,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR
	};
	System.out.println("insertSchoolCode=====");
	return this.executeUpdate(SQL_INSERT, params, types);

	}
	@Override
	public int addItem_City(SchoolCode item) {
		Object[] params = new Object[]{
		item.getId(),
		item.getChineseName(),
		item.getAllPin(),
		item.getSimplePin(),
		item.getStationTelecode()
	};
	int[] types = new int[] {
			Types.INTEGER,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR
	};
	System.out.println("insertSchoolCode=====");
	return this.executeUpdate(SQL_INSERT_City, params, types);

	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateItem(SchoolCode item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SchoolCode findItem(String id) {
		// TODO Auto-generated method stub
		return this.queryForItem(SQL_SELECT, new SchoolCodeRowViewMapper(), id);
	}
	
	public SchoolCode findItem_CityCode(String id) {
		// TODO Auto-generated method stub
		return this.queryForItem(SQL_SELECT_City, new SchoolCodeRowViewMapper(), id);
	}
	
	class SchoolCodeRowViewMapper implements RowMapper<SchoolCode>{
	@Override
	public SchoolCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		SchoolCode item = new SchoolCode();
		
		item.setId(rs.getInt("id"));
		item.setChineseName(rs.getString("chineseName"));
		item.setAllPin(rs.getString("allPin"));
		item.setSimplePin(rs.getString("simplePin"));
		item.setStationTelecode(rs.getString("stationTelecode"));
		return item;
	}
}
	@Override
	public SchoolCode findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findstationTelecode(String name) {
		if(StringUtil.isNullOrEmpty(name)) {
		return "";
	}
		SchoolCode item = findItem(name);
	if(item == null) {
		return "";
	}
	return item.getStationTelecode();
	}
	@Override
	public String finds_City_tationTelecode(String name) {
		if(StringUtil.isNullOrEmpty(name)) {
		return "";
	}
		SchoolCode item = findItem_CityCode(name);
	if(item == null) {
		return "";
	}
	return item.getStationTelecode();
	}

	
	
}