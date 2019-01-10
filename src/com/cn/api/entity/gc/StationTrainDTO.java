package com.cn.api.entity.gc;

public class StationTrainDTO {

	public String station_train_code;// 车次
	public String from_station_name;// 始发站名称
	public String to_station_name;// 目的地名称

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

	public String getTo_station_name() {
		return to_station_name;
	}

	public void setTo_station_name(String to_station_name) {
		this.to_station_name = to_station_name;
	}

}
