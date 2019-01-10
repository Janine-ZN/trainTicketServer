package com.cn.api.mvc.action;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.service.AppkeyService;
import com.cn.config.Config;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 登录
 */
@Controller
@RequestMapping("/")
public class APILoginController {

	@Autowired
	AppkeyService appkeyService;
	
	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}
	
	/**
	 * 获取验证码
	 */
	@ResponseBody
	@RequestMapping(value="/getPassCodeNew", method=RequestMethod.GET)
	public String getPassCodeNew(HttpServletRequest request) {
		String module = WebUtil.getString(request, "module", ""); //module（登录：login，提交订单：passenger，其他：other）
		String rand = WebUtil.getString(request, "rand", ""); //rand（登录：sjrand，提交订单：randp，其他：sjrand）
		String appkey = WebUtil.getString(request, "appkey", "");
		System.out.println("APILoginController中的getPassCodeNew进来的表示:");
		System.out.println("APILoginController进来的表示得到的appkey:"+appkey);
		String cookie = appkeyService.findCookies(appkey);
		System.out.println("APILoginController进来的表示得到的cookie:"+cookie);
		String url = Config.getPassCodeUrl + "?rand=" + rand + "&module=" + module + "&" + Math.random();
		
		String result = HttpUtil.getPassCodeNew(url, cookie);
			
		JSONObject jsonObject = new JSONObject();
		
		RspData rspData = new RspData();
		try {
			JSONObject jsonData = JSONObject.fromObject(result);
			String fileName = jsonData.getString("fileName");
			jsonObject.put("imgUrl", Config.captchaUrl + fileName); //获得验证码图片成功，imgUrl为图片访问路径
			jsonObject.put("imgName", fileName); 
			
			String setCookie = jsonData.getString("setCookie");
			appkeyService.updateCookies(appkey, setCookie);
			
			rspData.setFlag(true);
			rspData.setMsg("获取验证码成功");
			rspData.setResult(jsonObject.toString());
			
		} catch(Exception e) {
			jsonObject.put("imgUrl", "");  //获得验证码图片失败，imgUrl为空字符串
			jsonObject.put("imgName", ""); 
			
			rspData.setFlag(false);
			rspData.setMsg("获取验证码失败");
			rspData.setResult(jsonObject.toString());
		}
		System.out.println("Janine:getPassCodeNew.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 检测验证码（登录时）
	 */
	@ResponseBody
	@RequestMapping(value = "/checkForLogin", method = RequestMethod.POST)  
	public String checkForLogin(HttpServletRequest request) {  
		String randCode = WebUtil.getString(request, "randCode", "");
		String rand = WebUtil.getString(request, "rand", ""); //rand（传值：sjrand）
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.chkPassCodeUrl;
		String param = "randCode=" + randCode + "&rand=" + rand;
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		RspData rspData = new RspData();
		if(rspHttp.getCode() == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(rspHttp.getResult());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		System.out.println("Janine.checkForLogin.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 检查用户是否登录
	 */
	@ResponseBody
	@RequestMapping(value = "/checkUser", method = RequestMethod.POST)  
	public String checkUser(HttpServletRequest request) {  
		String appkey = WebUtil.getString(request, "appkey", "");
		System.out.println("APILoginController中的checkUser进来的表示:");
		String cookie = appkeyService.findCookies(appkey);
		System.out.println("APILoginController进来的表示得到的checkUser的cookie:"+cookie);
		String url = Config.checkUserUrl;
		String param = "_json_att=";
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		RspData rspData = new RspData();
		if(rspHttp.getCode() == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(rspHttp.getResult());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		System.out.println("Janine:checkUser.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)  
	public String loginUser(HttpServletRequest request) {  
		String userName = WebUtil.getString(request, "userName", "");
		String password = WebUtil.getString(request, "password", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:loginUser.appkey--->" + appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.loginUserUrl;
		String param = "random=" + System.currentTimeMillis() + "&loginUserDTO.user_name=" + userName + 
						"&userDTO.password=" + password + "&randCode=" + randCode;
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		RspData rspData = new RspData();
		if(rspHttp.getCode() == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(rspHttp.getResult());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		/**
		 * {
    			"flag": true,
    			"msg": "",
    			"result": {
        			"validateMessagesShowId": "_validatorMessage",
        			"status": false,
        			"httpstatus": 200,
        			"messages": ["系统繁忙，请稍后重试！"],
        			"validateMessages": {}
    			}
			}
		 * 
		 */
		System.out.println("Janine:loginUser输入用户名和密码.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 退出登录
	 */
	@ResponseBody
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)  
	public String loginOut(HttpServletRequest request) {  
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		JSONObject jsonObject = new JSONObject();
		
		RspData rspData = new RspData();
		try {
			String url = Config.loginOutUrl;
			HttpUtil.doGet(url, "", cookie);
			
			appkeyService.deleteItem(appkey);
			
			jsonObject.put("code", 1); //退出成功
			
			rspData.setFlag(true);
			rspData.setMsg("退出成功");
			rspData.setResult(jsonObject.toString());
			
		} catch(Exception e) {
			jsonObject.put("code", 0); //退出失败
			
			rspData.setFlag(false);
			rspData.setMsg("退出失败");
			rspData.setResult(jsonObject.toString());
		}

		return rspData.toJsonStr();
	}
	
}
