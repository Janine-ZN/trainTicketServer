package com.cn.api.entity.gc;

/**
 * 改签（原票信息）
 */
public class OldTicketDTO {

	/**
	 * 车次信息
	 */
	public String start_train_date_page;// 开车日期和时间
	public StationTrainDTO stationTrainDTO;
	/*
	 * public String station_train_code;// 车次 public String from_station_name;//
	 * 始发站名称 public String to_station_name;// 目的地名称
	 *//**
	 * 席别信息
	 */
	public String seat_type_name; // 席别
	public String coach_name; // 车厢
	public String seat_name; // 座位号
	/**
	 * 旅客信息
	 */
	public PassengerDTO passengerDTO;

	/*
	 * public String passenger_name; // 姓名 public String passenger_id_type_name;
	 * // 证件类型 public String passenger_id_no; // 证件号码 public String mobile_no;//
	 * 手机号码
	 *//**
	 * 票款信息
	 */
	public String ticket_type_name; // 票种
	public double ticket_price; // 票价

	public String getStart_train_date_page() {
		return start_train_date_page;
	}

	public void setStart_train_date_page(String start_train_date_page) {
		this.start_train_date_page = start_train_date_page;
	}

	public StationTrainDTO getStationTrainDTO() {
		return stationTrainDTO;
	}

	public void setStationTrainDTO(StationTrainDTO stationTrainDTO) {
		this.stationTrainDTO = stationTrainDTO;
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

	public PassengerDTO getPassengerDTO() {
		return passengerDTO;
	}

	public void setPassengerDTO(PassengerDTO passengerDTO) {
		this.passengerDTO = passengerDTO;
	}

	public String getTicket_type_name() {
		return ticket_type_name;
	}

	public void setTicket_type_name(String ticket_type_name) {
		this.ticket_type_name = ticket_type_name;
	}

	public double getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(double ticket_price) {
		this.ticket_price = ticket_price;
	}

}
