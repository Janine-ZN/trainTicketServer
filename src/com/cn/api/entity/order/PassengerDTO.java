package com.cn.api.entity.order;

public class PassengerDTO {

	public String passenger_name; // 姓名
	public String passenger_id_type_code;
	public String passenger_id_type_name; // 证件类型
	public String passenger_id_no; // 证件号码
	public String total_times;

	public String getPassenger_name() {
		return passenger_name;
	}

	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}

	public String getPassenger_id_type_code() {
		return passenger_id_type_code;
	}

	public void setPassenger_id_type_code(String passenger_id_type_code) {
		this.passenger_id_type_code = passenger_id_type_code;
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

	public String getTotal_times() {
		return total_times;
	}

	public void setTotal_times(String total_times) {
		this.total_times = total_times;
	}

}
