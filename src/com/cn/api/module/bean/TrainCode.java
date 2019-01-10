package com.cn.api.module.bean;

public class TrainCode {

	public Integer id;
	public String train_name; // 车站名字
	public String train_code; // 车站代码
	public String train_sh; // 车站缩写
	public String train_py;//车站拼音
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTrain_name() {
		return train_name;
	}
	public void setTrain_name(String train_name) {
		this.train_name = train_name;
	}
	public String getTrain_code() {
		return train_code;
	}
	public void setTrain_code(String train_code) {
		this.train_code = train_code;
	}
	public String getTrain_sh() {
		return train_sh;
	}
	public void setTrain_sh(String train_sh) {
		this.train_sh = train_sh;
	}
	public String getTrain_py() {
		return train_py;
	}
	public void setTrain_py(String train_py) {
		this.train_py = train_py;
	}

	
}
