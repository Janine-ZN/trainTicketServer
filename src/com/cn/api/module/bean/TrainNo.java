package com.cn.api.module.bean;

import java.sql.Timestamp;

public class TrainNo {

	public Integer id;
	public String train_no; // 车站名字
	public String updateTime;
	public Timestamp createTime;
	public String bookTime;
	public String train_nu;
	
	public String getTrain_nu() {
		return train_nu;
	}
	public void setTrain_nu(String train_nu) {
		this.train_nu = train_nu;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTrain_no() {
		return train_no;
	}
	public void setTrain_no(String train_no) {
		this.train_no = train_no;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	
}
