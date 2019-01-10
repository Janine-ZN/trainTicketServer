package com.cn.api.entity;

public class OrderPassenger {

	public int num_id; // 序号
	public String passenger_name; // 姓名
	public String passenger_id_type_name; // 证件类型
	public String passenger_id_no; // 证件号码
	public String ticket_type_name; // 票种
	public String seat_type_name; // 席别
	public String coach_name; // 车厢
	public String seat_name; // 座位号
	public double ticket_price; // 票价

	public int getNum_id() {
		return num_id;
	}

	public void setNum_id(int num_id) {
		this.num_id = num_id;
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

}
