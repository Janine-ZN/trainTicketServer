package com.cn.api.mvc.action;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 往返
 */
@Controller
@RequestMapping("/")
public class APITicketWcController {

	@Autowired
	AppkeyService appkeyService;
	
	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}

	/**
	 * 初始化订单界面
	 * 
	 * @return		json字符串（格式如下，这些属性在后续购票中需要用到）
	 * 
	 * {"globalRepeatSubmitToken":"...","key_check_isChange":"...","leftTicketStr":"...",
	 * 	"train_location":"...","queryLeftNewDetailDTO":"...","queryLeftTicketRequestDTO":"..."}
	 */
	@ResponseBody
	@RequestMapping(value="/initWc", method=RequestMethod.GET)
	public String initWc(HttpServletRequest request) {
		
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.initWc.接收到的数据:"
				+"appkey:"+appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.initWcUrl; 
		String param = "_json_att=";
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		String result = rspHttp.getResult();
		
		String globalRepeatSubmitToken = "";
		String reg = "var globalRepeatSubmitToken = '(.*?)'";
		Pattern pattern = Pattern.compile(reg);		
		Matcher matcher = pattern.matcher(result);		
		while(matcher.find()) {			
			globalRepeatSubmitToken = matcher.group(1);	
		}	
		
		String key_check_isChange = "";
		String leftTicketStr = "";
		String train_location = "";
		String queryLeftNewDetailDTO = "";
		String queryLeftTicketRequestDTO = "";
		String ticketInfoForPassengerForm = "";
		
		reg = "var ticketInfoForPassengerForm=(.*?);";
		pattern = Pattern.compile(reg);		
		matcher = pattern.matcher(result);		
		while(matcher.find()) {			
			ticketInfoForPassengerForm = matcher.group(1);	
			JSONObject jsonObject = JSONObject.fromObject(ticketInfoForPassengerForm.toString());
			key_check_isChange = jsonObject.getString("key_check_isChange");
			leftTicketStr = jsonObject.getString("leftTicketStr");
			train_location = jsonObject.getString("train_location");
			queryLeftNewDetailDTO = jsonObject.getJSONObject("queryLeftNewDetailDTO").toString();
			queryLeftTicketRequestDTO = jsonObject.getJSONObject("queryLeftTicketRequestDTO").toString();
		}		
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("globalRepeatSubmitToken", globalRepeatSubmitToken);
		jsonResult.put("key_check_isChange", key_check_isChange);
		jsonResult.put("leftTicketStr", leftTicketStr);
		jsonResult.put("train_location", train_location);
		jsonResult.put("queryLeftNewDetailDTO", queryLeftNewDetailDTO);
		jsonResult.put("queryLeftTicketRequestDTO", queryLeftTicketRequestDTO);
		
		RspData rspData = new RspData();
		if(rspHttp.getCode() == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(jsonResult.toString());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		System.out.println("Janine:APITicket.initWc.rspData.toJsonStr()" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 确认提交 
	 * 
	 * @param 		key_check_isChange（由initWc获得）
	 * @param 		leftTicketStr（由initWc获得）
	 * @param		train_location（由initWc获得）
	 * @param 		passengerTicketStr（seat_type,0,ticket_type,name,id_type,id_no,mobile_no,N）-->多个乘客用_隔开
	 * 				seat_type：座位类型（-1、无座，1、硬座，2、软座，3、硬卧，4、软卧，6、高级软卧，M、一等座，O、二等座，P、特等座，9、商务座）-->二等座是大写字母O，不是数字0
	 * 				ticket_type：票种（1、成人票，2、儿童票，3、学生票，4、残军票）
	 * 				name：乘客姓名
	 * 				it_type：证件类型（1、二代省份证，B、护照，C、港澳通行证，G、台湾通行证）
	 * 				id_no：证件号码
	 * 				mobile_no：手机号码（没有就传空字符串）
	 * @param 		oldPassengerStr（name,id_type,id_no,passenger_type）-->这几个字段可从乘客信息里获取
	 * @param 		purpose_codes（）
	 * @param 		randCode（验证码）
	 * @param 		roomType（）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmGoForQueue", method = RequestMethod.POST)  
	public String confirmGoForQueue(HttpServletRequest request) {  
		String key_check_isChange = WebUtil.getString(request, "key_check_isChange", "");
		String leftTicketStr = WebUtil.getString(request, "leftTicketStr", "");
		String train_location = WebUtil.getString(request, "train_location", "");
		String passengerTicketStr = WebUtil.getString(request, "passengerTicketStr", "");
		String oldPassengerStr = WebUtil.getString(request, "oldPassengerStr", "");
		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String roomType = WebUtil.getString(request, "roomType", "00");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.confirmGoForQueue.接收到的数据:"
				+"key_check_isChange:"+key_check_isChange
				+",leftTicketStr:"+leftTicketStr
				+",train_location:"+train_location
				+",passengerTicketStr:"+passengerTicketStr
				+",oldPassengerStr:"+oldPassengerStr
				+",purpose_codes:"+purpose_codes
				+",randCode:"+randCode
				+",REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.confirmWcQueueUrl;
		String param = "key_check_isChange=" + key_check_isChange +
				"&leftTicketStr=" + leftTicketStr + "&train_location=" + train_location +
				"&passengerTicketStr=" + EncoderUtil.encode(passengerTicketStr) + 
				"&oldPassengerStr=" + EncoderUtil.encode(oldPassengerStr) +
				"&purpose_codes=" + purpose_codes + "&randCode=" + randCode + "&roomType=" + roomType +
				"&_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		//System.out.println("***confirmGoForQueue ticket param " + param);
		//System.out.println("***confirmGoForQueue ticket result " + rspHttp.getResult());
		
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
		
		System.out.println("Janine:APITicket.confirmGoForQueue.rspData.toJsonStr()--->"
				+ rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 查看订单结果
	 * 
	 * @param 		orderSequence_no（订单id）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/resultOrderForWcQueue", method = RequestMethod.POST)  
	public String resultOrderForWcQueue(HttpServletRequest request) {  
		String orderSequence_no = WebUtil.getString(request, "orderSequence_no", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.resultOrderForWcQueue.接收到的数据:"
				+"orderSequence_no:"+orderSequence_no
				+",REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.resultOrderWcUrl;
		String param = "orderSequence_no=" + orderSequence_no + "&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN + "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		//System.out.println("***resultOrderForWcQueue ticket param " + param);
		//System.out.println("***resultOrderForWcQueue ticket result " + rspHttp.getResult());
		
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
		System.out.println("Janine:APITicket.resultOrderForWcQueue.rspData.toJsonStr()--->"
				+rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 网上支付初始化
	 */
	@ResponseBody
	@RequestMapping(value="/payOrderWcInit", method=RequestMethod.GET)
	public String payOrderWcInit(HttpServletRequest request) {
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "token", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.payOrderWcInit.接收到的数据:"
				+"REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		String cookie = appkeyService.findCookies(appkey);
		
		Calendar calendar = Calendar.getInstance();
		String url = Config.payOrderInitUrl + "?random=" + calendar.getTimeInMillis();
		String param = "_json_att="+"&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		String result = rspHttp.getResult();
		String parOrderDTOJson = "";
		int index_5 = result.indexOf("var parOrderDTOJson");
		if (index_5 > -1) {
			// Janine:此处的解析跟payOrderGcInit.do的解析相似
			String temp = result.substring(index_5);
			temp = temp.substring(temp.indexOf("\'") + 1);
			parOrderDTOJson = temp.substring(0, temp.indexOf("\'"));
			System.out.println("Janine.APITicket.continuePayInit.parOrderDTOJson.完整数据--->" + parOrderDTOJson);

		}
		System.out.println("Janine:APITicket.continuePayInit.parOrderDTOJson--->" + parOrderDTOJson);

		// Janine:添加oldTicketDTOJson的解析
		// Janine:这个值可以是null
		String orderRequestDTOJson = "";
		String reg_3 = "var orderRequestDTOJson = '(.*?)'";
		Pattern pattern_3 = Pattern.compile(reg_3);
		Matcher matcher_3 = pattern_3.matcher(result);
		while (matcher_3.find()) {
			orderRequestDTOJson = matcher_3.group(1);
		}
		String ticketForm = "";
		int indexOfTicket = result.indexOf("ticketTitleForm");
		if(indexOfTicket > -1) {
			String ticketTitleForm = result.substring(indexOfTicket);
			ticketForm = ticketTitleForm.substring(ticketTitleForm.indexOf("{"), ticketTitleForm.indexOf("}") + 1);
		}
		
		String passangerForm = "";
		int indexOfPassanger = result.indexOf("passangerTicketList");
		if(indexOfPassanger > -1) {
			String passangerTicketList = result.substring(indexOfPassanger);
			passangerForm = passangerTicketList.substring(passangerTicketList.indexOf("["), passangerTicketList.indexOf("]") + 1);
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("ticketForm", ticketForm);
		jsonResult.put("passangerForm", passangerForm);
		jsonResult.put("parOrderDTOJson", parOrderDTOJson);
		jsonResult.put("orderRequestDTOJson", orderRequestDTOJson);
		
		RspData rspData = new RspData();
		if(rspHttp.getCode() == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(jsonResult.toString());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		
		System.out.println("什么东西都没有吗--->"
				+rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
}
