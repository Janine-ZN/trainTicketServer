package com.cn.api.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cn.common.database.RowMapper;
import com.cn.core.DataAccessorWrapper;
import com.cn.util.StringUtil;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.dao.TrainCodeDao;

/** 
 * TrainCode实现类   
 */
public class TrainCodeDaoImpl extends DataAccessorWrapper implements TrainCodeDao {

	/**
	 * 插入语句
	 */
//	private static final String SQL_INSERT = "insert into appkey (appkey, cookies, createTime, updateTime) values (?, ?, ?, ?)";
	private static final String SQL_INSERT = "insert into traincode (id, train_name, train_code , train_sh,train_py ) values (?, ?, ?, ?,?)";
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
	private static final String SQL_SELECT = "select * from traincode where train_name = ? ";
	private static final String SQL_SELECT_code = "select * from traincode where train_code = ? ";

	@Override
	public int addItem(TrainCode item) {
		Object[] params = new Object[]{
		item.getId(),
		item.getTrain_name(),
		item.getTrain_code(),
		item.getTrain_sh(),
		item.getTrain_py()
	};
	int[] types = new int[] {
			Types.INTEGER,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR,
			Types.VARCHAR
	};
	return this.executeUpdate(SQL_INSERT, params, types);
	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateItem(TrainCode item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TrainCode findItem(String id) {
		// TODO Auto-generated method stub
		return this.queryForItem(SQL_SELECT, new TrainCodeRowViewMapper(), id);
	}
	public TrainCode findItem1(String id) {
		// TODO Auto-generated method stub
		return this.queryForItem(SQL_SELECT_code, new TrainCodeRowViewMapper(), id);
	}

	class TrainCodeRowViewMapper implements RowMapper<TrainCode>{
	@Override
	public TrainCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		TrainCode item = new TrainCode();
		
		item.setId(rs.getInt("id"));
		item.setTrain_name(rs.getString("train_name"));
		item.setTrain_code(rs.getString("train_code"));
		item.setTrain_sh(rs.getString("train_sh"));
		item.setTrain_py(rs.getString("train_py"));
		return item;
	}
}
	@Override
	public TrainCode findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTrain(String name) {
		if(StringUtil.isNullOrEmpty(name)) {
		return "";
	}
	TrainCode item = findItem(name);
	if(item == null) {
		return "";
	}
	return item.getTrain_code();
	}

	@Override
	public String findTrain_name(String code) {
		if(StringUtil.isNullOrEmpty(code)) {
		return "";
	}
	TrainCode item = findItem1(code);
	if(item == null) {
		return "";
	}
	return item.getTrain_name();
	}
	
	/**
	 * 查找记录
	 * 
	 */
	
//	private static final String SQL_SELECT_flag = "select * from TrainCode where orderNumber = ?and orderState=?";
//	@Override
//	public int addItem(TrainCode item) {
//		Object[] params = new Object[]{
//			item.getId(),
//			item.getTrain_name(),
//			item.getTrain_code(),
//			item.getTrain_sh(),
//			item.getTrain_py()
//		};
//		int[] types = new int[] {
//				Types.INTEGER,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR,
//				Types.VARCHAR
//		};
//		return this.executeUpdate(SQL_INSERT, params, types);
//	}
//
//
//
//	@Override
//	public TrainCode findItem(String name) {
//		return this.queryForItem(SQL_SELECT, new TrainCodeRowViewMapper(), name);
//	}
//	
//	@Override
//	public TrainCode findItemflag(String id,String num) {
//		return this.queryForItem(SQL_SELECT, new TrainCodeRowViewMapper(), id,num);
//	}
//	
//	class TrainCodeRowViewMapper implements RowMapper<TrainCode>{
//		@Override
//		public TrainCode mapRow(ResultSet rs, int rowNum) throws SQLException {
//			TrainCode item = new TrainCode();
//			
//			item.setId(rs.getInt("id"));
//			item.setCreateTime(rs.getTimestamp("createTime"));
//			item.setUpdateTime(rs.getTimestamp("updateTime"));
//			item.setPassenger_name(rs.getString("passengerName"));
//			item.setPassenger_id_type_name(rs.getString("idType"));
//			item.setPassenger_id_no(rs.getString("idType"));
//			item.setPassenger_phone_number(rs.getString("phoneNumber"));
//			item.setTicket_type_name(rs.getString("ticketType"));
//			item.setSeat_type_name(rs.getString("seatType"));
//			item.setCoach_name(rs.getString("coachName"));
//			item.setSeat_name(rs.getString("seatName"));
//			item.setTicket_price(rs.getDouble("ticketPrice"));
//			item.setStation_train_code(rs.getString("stationTrain"));
//			item.setStart_station_name(rs.getString("startStation"));
//			item.setEnd_station_name(rs.getString("endStation"));
//			item.setStart_time(rs.getString("startTime"));
//			item.setArrive_time(rs.getString("arriveTime"));
//			item.setStart_train_date(rs.getString("startTrain"));
//			item.setOrderstate(rs.getString("orderState"));
//			item.setOrdernumber(rs.getString("orderNumber"));
//			
//			
//			return item;
//		}
//	}
//
////	@Override
////	public String findCookies(String appkey) {
////		if(StringUtil.isNullOrEmpty(appkey)) {
////			return "";
////		}
////		Appkey item = findItem(appkey);
////		if(item == null) {
////			return "";
////		}
////		return item.getCookies();
////	}
//
//
//
////	@Override
////	public String findOrder(String ordernumber) {
////		if(StringUtil.isNullOrEmpty(ordernumber)) {
////			return "";
////		}
////		TrainCode item = findItem(ordernumber);
////		if(item == null) {
////			return "";
////		}
////		return item.getOrdernumber();
////	}
//
//	@Override
//	public String findTrain(String name) {
//		if(StringUtil.isNullOrEmpty(name)) {
//			return "";
//		}
//		TrainCode item = findItem(name);
//		if(item == null) {
//			return "";
//		}
//		return item.getTrain_code();
//
//}
//
//
//
//	@Override
//	public int deleteItem(String id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//
//	@Override
//	public int updateItem(TrainCode item) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//
//	@Override
//	public TrainCode findItem(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//	@Override
//	public int addItem(TrainCode item) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//
//
//	@Override
//	public TrainCode findItem(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}