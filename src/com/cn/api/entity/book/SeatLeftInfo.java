package com.cn.api.entity.book;

public class SeatLeftInfo {

	public String seat_type;// 座位类型（-1、无座，1、硬座，2、软座，3、硬卧，4、软卧，6、高级软卧，M、一等座，O、二等座，P、特等座，9、商务座）-->二等座是大写字母O，不是数字0
	public String seat_type_name;// 座位类型名称
	public double ticket_price;// 票价
	public int ticket_num;// 余票数量

	public String getSeat_type() {
		return seat_type;
	}

	public void setSeat_type(String seat_type) {
		this.seat_type = seat_type;
	}

	public String getSeat_type_name() {
		return seat_type_name;
	}

	public void setSeat_type_name(String seat_type_name) {
		this.seat_type_name = seat_type_name;
	}

	public double getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(double ticket_price) {
		this.ticket_price = ticket_price;
	}

	public int getTicket_num() {
		return ticket_num;
	}

	public void setTicket_num(int ticket_num) {
		this.ticket_num = ticket_num;
	}
	
}
