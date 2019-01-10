package com.cn.api.module.bean;

import net.sf.json.JSONObject;

/**
 * 返回给客户端的结果
 */
public class RspData {

	public boolean flag; //成功true，失败false
	public String msg;
	public String result;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String toJsonStr() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", flag);
		jsonObject.put("msg", msg);
		jsonObject.put("result", result);
		
		return jsonObject.toString();
	}
	
}
