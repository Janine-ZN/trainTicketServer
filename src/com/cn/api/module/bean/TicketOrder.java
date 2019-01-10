package com.cn.api.module.bean;

import java.sql.Timestamp;

public class TicketOrder {

//	public String appkey;
//	public String cookies;
//	public Timestamp createTime;
//	public Timestamp updateTime;
	public Integer id;
	public Timestamp createTime;//创建订单时间
	public Timestamp updateTime;
	public String passenger_name; // 姓名
	public String passenger_id_type_name; // 证件类型
	public String passenger_id_no; // 证件号码
	public String passenger_phone_number;//手机号
	public String ticket_type_name; // 票种
	public String seat_type_name; // 席别
	public String coach_name; // 车厢
	public String seat_name; // 座位号
	public double ticket_price; // 票价
	public String station_train_code; // 车次
	public String start_station_name; // 出发地名称
	public String end_station_name; // 目的地名称
	public String start_time; // 出发时间
	public String arrive_time; // 到达时间
	public String start_train_date; // 乘车日期
	public String orderstate;//订单状态
	public String ordernumber;//订单号
	
	
	
	
	public String getOrderstate() {
		return orderstate;
	}
	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}
	public String getOrdernumber() {
		return ordernumber;
	}
	public void setOrdernumber(String ordernumber) {
		this.ordernumber = ordernumber;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getPassenger_name() {
		return passenger_name;
	}
	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}
	public String getPassenger_id_type_name() {
		return passenger_id_type_name;
	}
	public void setPassenger_id_type_name(String passenger_id_type_name) {
		this.passenger_id_type_name = passenger_id_type_name;
	}
	public String getPassenger_id_no() {
		return passenger_id_no;
	}
	public void setPassenger_id_no(String passenger_id_no) {
		this.passenger_id_no = passenger_id_no;
	}
	public String getPassenger_phone_number() {
		return passenger_phone_number;
	}
	public void setPassenger_phone_number(String passenger_phone_number) {
		this.passenger_phone_number = passenger_phone_number;
	}
	public String getTicket_type_name() {
		return ticket_type_name;
	}
	public void setTicket_type_name(String ticket_type_name) {
		this.ticket_type_name = ticket_type_name;
	}
	public String getSeat_type_name() {
		return seat_type_name;
	}
	public void setSeat_type_name(String seat_type_name) {
		this.seat_type_name = seat_type_name;
	}
	public String getCoach_name() {
		return coach_name;
	}
	public void setCoach_name(String coach_name) {
		this.coach_name = coach_name;
	}
	public String getSeat_name() {
		return seat_name;
	}
	public void setSeat_name(String seat_name) {
		this.seat_name = seat_name;
	}
	public double getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(double ticket_price) {
		this.ticket_price = ticket_price;
	}
	public String getStation_train_code() {
		return station_train_code;
	}
	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}
	public String getStart_station_name() {
		return start_station_name;
	}
	public void setStart_station_name(String start_station_name) {
		this.start_station_name = start_station_name;
	}
	public String getEnd_station_name() {
		return end_station_name;
	}
	public void setEnd_station_name(String end_station_name) {
		this.end_station_name = end_station_name;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getStart_train_date() {
		return start_train_date;
	}
	public void setStart_train_date(String start_train_date) {
		this.start_train_date = start_train_date;
	}
	
	

}
