package com.cn.api.entity;

/**
 * 车票预订查询
 */
public class TicketBook {

	public String train_no; // 火车编号
	public String station_train_code; // 车次
	public String start_station_telecode; // 出发地代码
	public String start_station_name; // 出发地名称
	public String end_station_telecode; // 目的地代码
	public String end_station_name; // 目的地名称
	public String from_station_telecode; // 始发站代码
	public String from_station_name; // 始发站名称
	public String to_station_telecode; // 终点站代码
	public String to_station_name; // 终点站名称
	public String start_time; // 出发时间
	public String arrive_time; // 到达时间
	public String day_difference; // 当天到达0，次日到达1，第三日到达2，第四日到达3，以此类推
	public String train_class_name;
	public String lishi; // 历时
	public String canWebBuy; // 能否购买（当总票数为0时，该值为N，无法购买；当总票数大于0时，该值为Y，可以购买；未到购买时间的话，也为N。）
	public String lishiValue; // 历时（分钟）
	public String yp_info;
	public String control_train_day;
	public String start_train_date; // 出发日期
	public String seat_feature;
	public String yp_ex;
	public String train_seat_feature;
	public String seat_types;
	public String location_code;
	public String from_station_no;
	public String to_station_no;
	public String control_day;
	public String sale_time;
	public String is_support_card; // 是否支持凭身份证进站（0、否，1、是）
	// public String note; // 备注
	public String controlled_train_flag;
	public String controlled_train_message;
	public String gg_num;
	public String gr_num; // 高级软卧
	public String qt_num; // 其它
	public String rw_num; // 软卧
	public String rz_num; // 软座
	public String tz_num; // 特等座
	public String wz_num; // 无座
	public String yb_num;
	public String yw_num; // 硬卧
	public String yz_num; // 硬座
	public String ze_num; // 二等座
	public String zy_num; // 一等座
	public String swz_num; // 商务座
	public String secretStr;
	public String buttonTextInfo;

	public String getTrain_no() {
		return train_no;
	}

	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}

	public String getStation_train_code() {
		return station_train_code;
	}

	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}

	public String getStart_station_telecode() {
		return start_station_telecode;
	}

	public void setStart_station_telecode(String start_station_telecode) {
		this.start_station_telecode = start_station_telecode;
	}

	public String getStart_station_name() {
		return start_station_name;
	}

	public void setStart_station_name(String start_station_name) {
		this.start_station_name = start_station_name;
	}

	public String getEnd_station_telecode() {
		return end_station_telecode;
	}

	public void setEnd_station_telecode(String end_station_telecode) {
		this.end_station_telecode = end_station_telecode;
	}

	public String getEnd_station_name() {
		return end_station_name;
	}

	public void setEnd_station_name(String end_station_name) {
		this.end_station_name = end_station_name;
	}

	public String getFrom_station_telecode() {
		return from_station_telecode;
	}

	public void setFrom_station_telecode(String from_station_telecode) {
		this.from_station_telecode = from_station_telecode;
	}

	public String getFrom_station_name() {
		return from_station_name;
	}

	public void setFrom_station_name(String from_station_name) {
		this.from_station_name = from_station_name;
	}

	public String getTo_station_telecode() {
		return to_station_telecode;
	}

	public void setTo_station_telecode(String to_station_telecode) {
		this.to_station_telecode = to_station_telecode;
	}

	public String getTo_station_name() {
		return to_station_name;
	}

	public void setTo_station_name(String to_station_name) {
		this.to_station_name = to_station_name;
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

	public String getDay_difference() {
		return day_difference;
	}

	public void setDay_difference(String day_difference) {
		this.day_difference = day_difference;
	}

	public String getTrain_class_name() {
		return train_class_name;
	}

	public void setTrain_class_name(String train_class_name) {
		this.train_class_name = train_class_name;
	}

	public String getLishi() {
		return lishi;
	}

	public void setLishi(String lishi) {
		this.lishi = lishi;
	}

	public String getCanWebBuy() {
		return canWebBuy;
	}

	public void setCanWebBuy(String canWebBuy) {
		this.canWebBuy = canWebBuy;
	}

	public String getLishiValue() {
		return lishiValue;
	}

	public void setLishiValue(String lishiValue) {
		this.lishiValue = lishiValue;
	}

	public String getYp_info() {
		return yp_info;
	}

	public void setYp_info(String yp_info) {
		this.yp_info = yp_info;
	}

	public String getControl_train_day() {
		return control_train_day;
	}

	public void setControl_train_day(String control_train_day) {
		this.control_train_day = control_train_day;
	}

	public String getStart_train_date() {
		return start_train_date;
	}

	public void setStart_train_date(String start_train_date) {
		this.start_train_date = start_train_date;
	}

	public String getSeat_feature() {
		return seat_feature;
	}

	public void setSeat_feature(String seat_feature) {
		this.seat_feature = seat_feature;
	}

	public String getYp_ex() {
		return yp_ex;
	}

	public void setYp_ex(String yp_ex) {
		this.yp_ex = yp_ex;
	}

	public String getTrain_seat_feature() {
		return train_seat_feature;
	}

	public void setTrain_seat_feature(String train_seat_feature) {
		this.train_seat_feature = train_seat_feature;
	}

	public String getSeat_types() {
		return seat_types;
	}

	public void setSeat_types(String seat_types) {
		this.seat_types = seat_types;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getFrom_station_no() {
		return from_station_no;
	}

	public void setFrom_station_no(String from_station_no) {
		this.from_station_no = from_station_no;
	}

	public String getTo_station_no() {
		return to_station_no;
	}

	public void setTo_station_no(String to_station_no) {
		this.to_station_no = to_station_no;
	}

	public String getControl_day() {
		return control_day;
	}

	public void setControl_day(String control_day) {
		this.control_day = control_day;
	}

	public String getSale_time() {
		return sale_time;
	}

	public void setSale_time(String sale_time) {
		this.sale_time = sale_time;
	}

	public String getIs_support_card() {
		return is_support_card;
	}

	public void setIs_support_card(String is_support_card) {
		this.is_support_card = is_support_card;
	}

	public String getControlled_train_flag() {
		return controlled_train_flag;
	}

	public void setControlled_train_flag(String controlled_train_flag) {
		this.controlled_train_flag = controlled_train_flag;
	}

	public String getControlled_train_message() {
		return controlled_train_message;
	}

	public void setControlled_train_message(String controlled_train_message) {
		this.controlled_train_message = controlled_train_message;
	}

	public String getGg_num() {
		return gg_num;
	}

	public void setGg_num(String gg_num) {
		this.gg_num = gg_num;
	}

	public String getGr_num() {
		return gr_num;
	}

	public void setGr_num(String gr_num) {
		this.gr_num = gr_num;
	}

	public String getQt_num() {
		return qt_num;
	}

	public void setQt_num(String qt_num) {
		this.qt_num = qt_num;
	}

	public String getRw_num() {
		return rw_num;
	}

	public void setRw_num(String rw_num) {
		this.rw_num = rw_num;
	}

	public String getRz_num() {
		return rz_num;
	}

	public void setRz_num(String rz_num) {
		this.rz_num = rz_num;
	}

	public String getTz_num() {
		return tz_num;
	}

	public void setTz_num(String tz_num) {
		this.tz_num = tz_num;
	}

	public String getWz_num() {
		return wz_num;
	}

	public void setWz_num(String wz_num) {
		this.wz_num = wz_num;
	}

	public String getYb_num() {
		return yb_num;
	}

	public void setYb_num(String yb_num) {
		this.yb_num = yb_num;
	}

	public String getYw_num() {
		return yw_num;
	}

	public void setYw_num(String yw_num) {
		this.yw_num = yw_num;
	}

	public String getYz_num() {
		return yz_num;
	}

	public void setYz_num(String yz_num) {
		this.yz_num = yz_num;
	}

	public String getZe_num() {
		return ze_num;
	}

	public void setZe_num(String ze_num) {
		this.ze_num = ze_num;
	}

	public String getZy_num() {
		return zy_num;
	}

	public void setZy_num(String zy_num) {
		this.zy_num = zy_num;
	}

	public String getSwz_num() {
		return swz_num;
	}

	public void setSwz_num(String swz_num) {
		this.swz_num = swz_num;
	}

	public String getSecretStr() {
		return secretStr;
	}

	public void setSecretStr(String secretStr) {
		this.secretStr = secretStr;
	}

	public String getButtonTextInfo() {
		return buttonTextInfo;
	}

	public void setButtonTextInfo(String buttonTextInfo) {
		this.buttonTextInfo = buttonTextInfo;
	}

}
