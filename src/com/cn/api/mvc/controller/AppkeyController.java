package com.cn.api.mvc.controller;

import java.sql.Timestamp;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.Appkey;
import com.cn.api.module.service.AppkeyService;
import com.cn.core.IdentifierGenerator;
import com.cn.util.DateUtil;

@Controller
@RequestMapping("/")
public class AppkeyController {

	@Autowired
	AppkeyService appkeyService;
	
	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}

	/**
	 * 获取key，每次请求其他接口时都要发送这个key，在服务端用来保存session一致
	 */
	@ResponseBody  
	@RequestMapping(value="/getAppkey", method=RequestMethod.GET)
	public String getAppKey() {
		System.out.println("进来getAppKey****************************");
		Appkey item = new Appkey();
		
		String appkey = IdentifierGenerator.generateUUID(); //appkey生成
		Timestamp current = DateUtil.getCurrentTimestamp();
		item.setAppkey(appkey);
		item.setCookies("");
		item.setCreateTime(current);
		item.setUpdateTime(current);
		
		int code = appkeyService.addItem(item);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		
		if(code > 0) {
			jsonObject.put("appkey", appkey); //若获得key成功，返回的code大于0，appkey为长度32的字符串
		} else {
			jsonObject.put("appkey", ""); //若获得key失败，返回的code等于0，appkey为空字符串
		}
		
		return jsonObject.toString();
	}
	
}
