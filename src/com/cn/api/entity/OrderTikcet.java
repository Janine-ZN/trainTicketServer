package com.cn.api.entity;

public class OrderTikcet {

	public String train_date;
	public String station_train_code;
	public String from_station_name;
	public String start_time;
	public String to_station_name;
	public String arrive_time;
	public String tour_flag;
	public String train_headers;
	public String week;

	public String getTrain_date() {
		return train_date;
	}

	public void setTrain_date(String train_date) {
		this.train_date = train_date;
	}

	public String getStation_train_code() {
		return station_train_code;
	}

	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}

	public String getFrom_station_name() {
		return from_station_name;
	}

	public void setFrom_station_name(String from_station_name) {
		this.from_station_name = from_station_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getTo_station_name() {
		return to_station_name;
	}

	public void setTo_station_name(String to_station_name) {
		this.to_station_name = to_station_name;
	}

	public String getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}

	public String getTour_flag() {
		return tour_flag;
	}

	public void setTour_flag(String tour_flag) {
		this.tour_flag = tour_flag;
	}

	public String getTrain_headers() {
		return train_headers;
	}

	public void setTrain_headers(String train_headers) {
		this.train_headers = train_headers;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

}
