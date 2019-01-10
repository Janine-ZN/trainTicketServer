package com.cn.api.module.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.cn.common.database.RowMapper;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.bean.Appkey;
import com.cn.api.module.dao.TicketOrderDao;
import com.cn.api.module.dao.TrainCodeDao;
import com.cn.api.module.dao.AppkeyDao;
import com.cn.core.DataAccessorWrapper;
import com.cn.util.StringUtil;

import net.sf.json.JSONArray;

/** 
 * TrainCode实现类   
 */
public class TicketOrderDaoImpl extends DataAccessorWrapper implements TicketOrderDao {

	/**
	 * 插入语句
	 */
//	private static final String SQL_INSERT = "insert into appkey (appkey, cookies, createTime, updateTime) values (?, ?, ?, ?)";
	
	private static final String SQL_INSERT = "insert into ticketorder (id,createTime, passengerName, idType, idNo,phoneNumber,ticketType,seatType,coachName,seatName,ticketPrice,stationTrain,startStation,endStation,startTime,arriveTime,startTrain,orderState,orderNumber,updateTime) values (?,?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?, ?, ?, ?,?, ?,?)";
	/**
	 * 删除语句
	 */
//	private static final String SQL_DELETE = "delete from appkey where appkey = ? ";
	private static final String SQL_DELETE = "delete from ticketorder where orderNumber = ? ";

	/**
	 * 更新记录
	 */
	private static final String SQL_UPDATE = "update ticketorder set orderState = ?, updateTime = ? where orderNumber = ? ";
	
	
	/**
	 * 查找记录
	 */
	//private static final String SQL_SELECT = "select * from appkey where appkey = ? ";
	private static final String SQL_SELECT = "select * from ticketorder where orderNumber = ? ";
	
	/**
	 * 查找记录
	 * 
	 */
	
	private static final String SQL_SELECT_flag = "select * from ticketorder where orderNumber = ?and orderState=?";
	@Override
	public int addItem(TicketOrder item) {
		Object[] params = new Object[]{
			item.getId(),
			item.getCreateTime(),
			item.getPassenger_name(),
			item.getPassenger_id_type_name(),
			item.getPassenger_id_no(), 	
			item.getPassenger_phone_number(),
			item.getTicket_type_name(),
			item.getSeat_type_name(),
			item.getCoach_name(),
			item.getSeat_name(),
			item.getTicket_price(),
			item.getStation_train_code(),
			item.getStart_station_name(),
			item.getEnd_station_name(),
			item.getStart_time(),
			item.getArrive_time(),
			item.getStart_train_date(),
			item.getOrderstate(),
			item.getOrdernumber(),
			item.getUpdateTime()
		};
		int[] types = new int[] {
				Types.INTEGER,
				Types.TIMESTAMP,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.DOUBLE,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
				Types.VARCHAR,
			   Types.TIMESTAMP
		};
		return this.executeUpdate(SQL_INSERT, params, types);
	}

	@Override
	public int deleteItem(String id) {
		return this.executeDelete(SQL_DELETE, id);
	}

	@Override
	public int updateItem(TicketOrder item) {
		Object[] params = new Object[]{
			item.getOrderstate(),		item.getUpdateTime(),		item.getOrdernumber()
		};
		int[] types = new int[]{
			Types.VARCHAR,			Types.TIMESTAMP,			Types.VARCHAR
		};
		return this.executeUpdate(SQL_UPDATE, params, types);
	}

	@Override
	public TicketOrder findItem(String id) {
		return this.queryForItem(SQL_SELECT, new TicketOrderRowViewMapper(), id);
	}
	
	@Override
	public TicketOrder findItemflag(String id,String num) {
		return this.queryForItem(SQL_SELECT, new TicketOrderRowViewMapper(), id,num);
	}
	
	class TicketOrderRowViewMapper implements RowMapper<TicketOrder>{
		@Override
		public TicketOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
			TicketOrder item = new TicketOrder();
			
			item.setId(rs.getInt("id"));
			item.setCreateTime(rs.getTimestamp("createTime"));
			item.setUpdateTime(rs.getTimestamp("updateTime"));
			item.setPassenger_name(rs.getString("passengerName"));
			item.setPassenger_id_type_name(rs.getString("idType"));
			item.setPassenger_id_no(rs.getString("idType"));
			item.setPassenger_phone_number(rs.getString("phoneNumber"));
			item.setTicket_type_name(rs.getString("ticketType"));
			item.setSeat_type_name(rs.getString("seatType"));
			item.setCoach_name(rs.getString("coachName"));
			item.setSeat_name(rs.getString("seatName"));
			item.setTicket_price(rs.getDouble("ticketPrice"));
			item.setStation_train_code(rs.getString("stationTrain"));
			item.setStart_station_name(rs.getString("startStation"));
			item.setEnd_station_name(rs.getString("endStation"));
			item.setStart_time(rs.getString("startTime"));
			item.setArrive_time(rs.getString("arriveTime"));
			item.setStart_train_date(rs.getString("startTrain"));
			item.setOrderstate(rs.getString("orderState"));
			item.setOrdernumber(rs.getString("orderNumber"));
			
			
			return item;
		}
	}

//	@Override
//	public String findCookies(String appkey) {
//		if(StringUtil.isNullOrEmpty(appkey)) {
//			return "";
//		}
//		Appkey item = findItem(appkey);
//		if(item == null) {
//			return "";
//		}
//		return item.getCookies();
//	}



	@Override
	public String findOrder(String ordernumber) {
		if(StringUtil.isNullOrEmpty(ordernumber)) {
			return "";
		}
		TicketOrder item = findItem(ordernumber);
		if(item == null) {
			return "";
		}
		return item.getOrdernumber();
	}


}
