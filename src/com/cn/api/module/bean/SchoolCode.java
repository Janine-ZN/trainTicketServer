package com.cn.api.module.bean;

public class SchoolCode {

	public Integer id;
	public String chineseName; // 车站名字
	public String allPin; // 车站代码
	public String simplePin; // 车站缩写
	public String stationTelecode;//车站拼音
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getAllPin() {
		return allPin;
	}
	public void setAllPin(String allPin) {
		this.allPin = allPin;
	}
	public String getSimplePin() {
		return simplePin;
	}
	public void setSimplePin(String simplePin) {
		this.simplePin = simplePin;
	}
	public String getStationTelecode() {
		return stationTelecode;
	}
	public void setStationTelecode(String stationTelecode) {
		this.stationTelecode = stationTelecode;
	}
	
	
}
