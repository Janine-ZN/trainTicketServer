package com.cn.api.mvc.action;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TicketOrderService;
import com.cn.config.Config;
import com.cn.util.DateUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 改签
 */
@Controller
@RequestMapping("/")
public class APITicketGcController {

	@Autowired
	AppkeyService appkeyService;
	
	@Autowired
	TicketOrderService ticketOrderService;
	
	
	
	public TicketOrderService getTicketOrderService() {
		return ticketOrderService;
	}

	public void setTicketOrderService(TicketOrderService ticketOrderService) {
		this.ticketOrderService = ticketOrderService;
	}

	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}
	
	/**
	 * 检查是否可以改签
	 */
	@ResponseBody
	@RequestMapping(value = "/resginTicket", method = RequestMethod.POST)  
	public String resginTicket(HttpServletRequest request) {  
		String ticketkey = WebUtil.getString(request, "ticketkey", "");
		String sequenceNo = WebUtil.getString(request, "sequenceNo", "");
		String changeTSFlag = WebUtil.getString(request, "changeTSFlag", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("传参ticketkey*********"+ticketkey);
		System.out.println("传参sequenceNo*********"+sequenceNo);
		System.out.println("传参changeTSFlag*********"+changeTSFlag);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.resginTicketUrl;
		String param = "ticketkey=" + EncoderUtil.encode(ticketkey) +
				"&sequenceNo=" + sequenceNo + "&changeTSFlag=" + changeTSFlag + "&_json_att=";
		System.out.println("传参url*********"+url);
		System.out.println("传参param*********"+param);
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		System.out.println("rspHttp.getResult()*********"+rspHttp.getResult());
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
		System.out.println("Janine:rspData.toJsonStr()*********" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 初始化改签页面
	 */
	@ResponseBody
	@RequestMapping(value="/initGc", method=RequestMethod.GET)
	public String initGc(HttpServletRequest request) {
		
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.initGcUrl; 
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
		String oldTicketDTOs = "";
		
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
		
		reg = "oldTicketDTOs =(.*?);";
		pattern = Pattern.compile(reg);		
		String temp = result.replace("var oldTicketDTOs=\"\";", "");
		matcher = pattern.matcher(temp);		
		while(matcher.find()) {			
			oldTicketDTOs = matcher.group(1);	
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("globalRepeatSubmitToken", globalRepeatSubmitToken);
		jsonResult.put("key_check_isChange", key_check_isChange);
		jsonResult.put("leftTicketStr", leftTicketStr);
		jsonResult.put("train_location", train_location);
		jsonResult.put("queryLeftNewDetailDTO", queryLeftNewDetailDTO);
		jsonResult.put("queryLeftTicketRequestDTO", queryLeftTicketRequestDTO);
		jsonResult.put("oldTicketDTOs", oldTicketDTOs);
		
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
		System.out.println("Janine:initGc.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	@ResponseBody
	@RequestMapping(value = "/confirmResignForQueue", method = RequestMethod.POST)  
	public String confirmResignForQueue(HttpServletRequest request) {  
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
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.confirmGcQueueUrl;
		String param = "key_check_isChange=" + key_check_isChange +
					"&leftTicketStr=" + leftTicketStr + "&train_location=" + train_location +
					"&passengerTicketStr=" + EncoderUtil.encode(passengerTicketStr) + 
					"&oldPassengerStr=" + EncoderUtil.encode(oldPassengerStr) +
					"&purpose_codes=" + purpose_codes + "&randCode=" + randCode + "&roomType=" + roomType +
					"&_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
	
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
		System.out.println("Janine:confirmResignForQueue.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	@ResponseBody
	@RequestMapping(value = "/resultOrderForGcQueue", method = RequestMethod.POST)  
	public String resultOrderForGcQueue(HttpServletRequest request) {  
		String orderSequence_no = WebUtil.getString(request, "orderSequence_no", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.resultOrderGcUrl;
		String param = "orderSequence_no=" + orderSequence_no + "&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN + "&_json_att=";
	
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
		System.out.println("Janine:resultOrderForGcQueue.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 网上支付初始化（改签）
	 */
	@ResponseBody
	@RequestMapping(value="/payOrderGcInit", method=RequestMethod.GET)
	public String payOrderGcInit(HttpServletRequest request) {
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "token", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		Calendar calendar = Calendar.getInstance();
		String url = Config.payOrderInitUrl + "?random=" + calendar.getTimeInMillis();
		String param = "_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
		String data = "";
		int Code = 0;
		JSONObject jsonResult = null;
		try {
			RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
			Code = rspHttp.getCode();
			String setCookie = rspHttp.getCookie();
			appkeyService.updateCookies(appkey, setCookie);
			
			String result = rspHttp.getResult();
			
			System.out.println("Janine:APITicket.payOrderGcInit.rspHttp.getResult().是不是网页--->" 
						+ rspHttp.getResult());
			
			String ticketTitleForm = "";
			int index_1 = result.indexOf("var ticketTitleForm");
			if(index_1 > -1) {
				String temp = result.substring(index_1);
				ticketTitleForm = temp.substring(temp.indexOf("{"), temp.indexOf("}") + 1);
			}
			
			String passangerTicketList = "";
			int index_2 = result.indexOf("var passangerTicketList");
			if(index_2 > -1) {
				String temp = result.substring(index_2);
				passangerTicketList = temp.substring(temp.indexOf("["), temp.indexOf("]") + 1);
				
			}
			
			String batch_no = "";
			int index_3 = result.indexOf("var batch_no");
			if(index_3 > -1) {
				String temp = result.substring(index_3);
				temp = temp.substring(temp.indexOf("\'") + 1);
				batch_no = temp.substring(0, temp.indexOf("\'"));
			}
			
			// Janine:查看返回的值
			String oldTicketDTOJson = "";
			int index_4 = result.indexOf("var oldTicketDTOJson");
			if(index_4 > -1) {
				String temp = result.substring(index_4);
				oldTicketDTOJson = temp.substring(temp.indexOf("["), temp.indexOf("]") + 1);
			}
			System.out.println("Janine:APITicket.payOrderGcInit.oldTicketDTOJson--->"
							+ oldTicketDTOJson);
			
			// Janine:查看返回的值
			String parOrderDTOJson = "";
			int index_5 = result.indexOf("var parOrderDTOJson");
			if(index_5 > -1) {
//				String temp = result.substring(index_5);
//				parOrderDTOJson = temp.substring(temp.indexOf("{"), temp.indexOf("}") + 1);
				
				//Janine:修改
				String temp = result.substring(index_5);
				temp = temp.substring(temp.indexOf("\'") + 1);
				parOrderDTOJson = temp.substring(0, temp.indexOf("\'"));
				System.out.println("Janine.parOrderDTOJson.完全的数据--->"+parOrderDTOJson);
				
			}
			System.out.println("Janine:APITicket.payOrderGcInit.parOrderDTOJson--->"
					+ parOrderDTOJson);
			
			// Janine:查看返回的值
			String orderRequestDTOJson = "";
			int index_6 = result.indexOf("var orderRequestDTOJson");
			if(index_6 > -1) {
				String temp = result.substring(index_6);
				orderRequestDTOJson = temp.substring(temp.indexOf("{"), temp.indexOf("}") + 1);
			}
			System.out.println("Janine:APITicket.payOrderGcInit.orderRequestDTOJson--->"
					+ orderRequestDTOJson);
			
			String sequence_no = "";
			int index_7 = result.indexOf("var sequence_no");
			if(index_7 > -1) {
				String temp = result.substring(index_7);
				temp = temp.substring(temp.indexOf("\'") + 1);
				sequence_no = temp.substring(0, temp.indexOf("\'"));
			}
			
			jsonResult = new JSONObject();
			jsonResult.put("ticketTitleForm", ticketTitleForm);
			jsonResult.put("passangerTicketList", passangerTicketList);
			jsonResult.put("batch_no", batch_no);
			jsonResult.put("oldTicketDTOJson", oldTicketDTOJson);
			jsonResult.put("parOrderDTOJson", parOrderDTOJson);
			jsonResult.put("orderRequestDTOJson", orderRequestDTOJson);
			jsonResult.put("sequence_no", sequence_no);
			
			/*
			 * 订单变为改签状态
			 */
			System.out.println("ticketTitleForm*******"+ticketTitleForm);
			System.out.println("passangerTicketList*******"+passangerTicketList);
			System.out.println("sequence_no*******"+sequence_no);
			// Janine:重点关注orderRequestDTOJson
			System.out.println("orderRequestDTOJson*******"+orderRequestDTOJson);
			
			data = jsonResult.toString();
			
			//新生成一个订单
			//更改原来得订单
			// Janine:从数据库中按订单号查找以前的订单
			TicketOrder olditeam = ticketOrderService.findItem(sequence_no);
			
			TicketOrder newitem = new TicketOrder();
			Timestamp current = DateUtil.getCurrentTimestamp();
			JSONObject jsonObject = JSONObject.fromObject(data);
			String ticketForm = jsonObject.getString("ticketTitleForm");
			String passangerForm = jsonObject.getString("passangerTicketList");
			
			JSONObject jsonTicketForm = JSONObject.fromObject(ticketForm);
			JSONArray jsonArray = JSONArray.fromObject(passangerForm);
			for(int i = 0; i < jsonArray.size(); i++) {
				
				JSONObject json = jsonArray.getJSONObject(i);
				newitem.setArrive_time(jsonTicketForm.getString("arrive_time"));
				newitem.setCoach_name(json.getString("coach_name"));
				newitem.setCreateTime(current);
				newitem.setEnd_station_name(olditeam.getEnd_station_name());
				newitem.setOrdernumber(sequence_no);
				newitem.setOrderstate("5");
				newitem.setPassenger_id_no(olditeam.getPassenger_id_no());
				newitem.setPassenger_id_type_name(olditeam.getPassenger_id_type_name());
				newitem.setPassenger_name(olditeam.getPassenger_name());
				newitem.setPassenger_phone_number(olditeam.getPassenger_phone_number());
				newitem.setSeat_name(json.getString("seat_name"));
				newitem.setSeat_type_name(json.getString("seat_type_name"));
				newitem.setStart_station_name(olditeam.getStart_station_name());
				newitem.setStart_time(jsonTicketForm.getString("start_time"));
				newitem.setStart_train_date(jsonTicketForm.getString("train_date"));
				newitem.setStation_train_code(jsonTicketForm.getString("station_train_code"));
				newitem.setUpdateTime(current);
				newitem.setTicket_type_name(json.getString("ticket_type_name"));
				double ticket_price = json.getDouble("ticket_price")/100;
				newitem.setTicket_price(ticket_price);
				//sequence_no = json.getString("sequence_no");
				
				ticketOrderService.addItem(newitem);
			}
			
			
		} catch(Exception e) {
			data = "";
		}
		
		RspData rspData = new RspData();
		if(Code == 200) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(jsonResult.toString());
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		System.out.println("Janine:payOrderGcInit.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 取消改签
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelResign", method = RequestMethod.POST)  
	public String cancelResign(HttpServletRequest request) {  
		
		String orderRequestDTOJsonStr = WebUtil.getString(request, "orderRequestDTOJson", "");
		String parOrderDTOJsonStr = WebUtil.getString(request, "parOrderDTOJson", "");
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.cancelResign.进来--->" 
				+ "orderRequestDTOJson:" + orderRequestDTOJsonStr                             
				+ ",parOrderDTOJson:" + parOrderDTOJsonStr                
				+ ",sequence_no:" + sequence_no
				+ ",appkey:"+appkey);
		
		String orderRequestDTOJson = orderRequestDTOJsonStr.replace("\\\"", "\"");
		String parOrderDTOJson = parOrderDTOJsonStr.replace("\\\"", "\"");
		
		System.out.println("Janine:APITicket.cancelResign.进来--->"
				+ "orderRequestDTOJson:" + orderRequestDTOJson
				+ ",parOrderDTOJson:" + parOrderDTOJson);
		
		String cookie = appkeyService.findCookies(appkey);
		
		// Janine:cancelResignUrl=https://kyfw.12306.cn/otn/payOrder/cancelResign
		String url = Config.cancelResignUrl;
		
		System.out.println("Janine:APITicket.cancelResign.url--->" 
				+ url);
		
//		String param = "orderRequestDTOJson=" + EncoderUtil.encode(orderRequestDTOJson) + 
//				"&parOrderDTOJson=" + EncoderUtil.encode(parOrderDTOJson) + 
//				"&sequence_no=" + sequence_no + "&_json_att=";
		
		// Janine:修改
		String param = "orderRequestDTOJson=" + orderRequestDTOJson+ 
				"&parOrderDTOJson=" + parOrderDTOJson + 
				"&sequence_no=" + sequence_no + "&_json_att=";
		
		System.out.println("Janine:APITicket.cancelResign.param--->" 
				+ param);
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:APITicket.cancelResign.rspHttp--->" 
				+ rspHttp);
		
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
		System.out.println("Janine:cancelResign.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 判断订单当前能否改签：原价大于现价
	 */
	@ResponseBody
	@RequestMapping(value = "/payConfirmT", method = RequestMethod.POST)  
	public String payConfirmT(HttpServletRequest request) {  
		
		String batch_no	= WebUtil.getString(request, "batch_no", "");
		String oldTicketDTOJsonStr = WebUtil.getString(request, "oldTicketDTOJson", "");
		String parOrderDTOJsonStr = WebUtil.getString(request, "parOrderDTOJson", "");
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.payConfirmT.进来--->" 
				+ "batch_no:" + batch_no                             
				+ ",oldTicketDTOJson:" + oldTicketDTOJsonStr              
				+ ",parOrderDTOJson:" + parOrderDTOJsonStr                
				+ ",sequence_no:" + sequence_no);
		
		String oldTicketDTOJson = oldTicketDTOJsonStr.replace("\\\"", "\"");
		String parOrderDTOJson = parOrderDTOJsonStr.replace("\\\"", "\"");
		
		System.out.println("Janine:APITicket.payConfirmT.进来--->"
					+ "oldTicketDTOJson:" + oldTicketDTOJson
					+ ",parOrderDTOJson:" + parOrderDTOJson);
		
		String cookie = appkeyService.findCookies(appkey);
		
		// Janine:payConfirmNUrl=https://kyfw.12306.cn/otn/pay/payConfirmN
		String url = Config.payConfirmTUrl;
		
		System.out.println("Janine:APITicket.payConfirmT.url--->" 
				+ url);
		
		String param = "batch_no=" + batch_no + 
				"&oldTicketDTOJson=" + EncoderUtil.encode(oldTicketDTOJson) + 
				"&parOrderDTOJson=" + EncoderUtil.encode(parOrderDTOJson) + 
				"&sequence_no=" + sequence_no + "&_json_att=";
		
		System.out.println("Janine:APITicket.payConfirmT.param--->" 
				+ param);
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:APITicket.payConfirmT.rspHttp--->" 
				+ rspHttp);
		
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
		System.out.println("Janine:payConfirmT.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 判断订单当前能否改签：原价等于现价
	 */
	@ResponseBody
	@RequestMapping(value = "/payConfirmN", method = RequestMethod.POST)  
	public String payConfirmN(HttpServletRequest request) {  
		
		String batch_no	= WebUtil.getString(request, "batch_no", "");
		String oldTicketDTOJsonStr = WebUtil.getString(request, "oldTicketDTOJson", "");
		String parOrderDTOJsonStr = WebUtil.getString(request, "parOrderDTOJson", "");
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.payConfirmN.进来--->" 
				+ "batch_no:" + batch_no                             
				+ ",oldTicketDTOJson:" + oldTicketDTOJsonStr              
				+ ",parOrderDTOJson:" + parOrderDTOJsonStr                
				+ ",sequence_no:" + sequence_no);
		
		String oldTicketDTOJson = oldTicketDTOJsonStr.replace("\\\"", "\"");
		String parOrderDTOJson = parOrderDTOJsonStr.replace("\\\"", "\"");
		
		System.out.println("Janine:APITicket.payConfirmN.进来--->"
					+ "oldTicketDTOJson:" + oldTicketDTOJson
					+ ",parOrderDTOJson:" + parOrderDTOJson);
		
		String cookie = appkeyService.findCookies(appkey);
		
		// Janine:payConfirmNUrl=https://kyfw.12306.cn/otn/pay/payConfirmN
		String url = Config.payConfirmNUrl;
		
		System.out.println("Janine:APITicket.payConfirmN.url--->" 
				+ url);
		
		String param = "batch_no=" + batch_no + 
				"&oldTicketDTOJson=" + EncoderUtil.encode(oldTicketDTOJson) + 
				"&parOrderDTOJson=" + EncoderUtil.encode(parOrderDTOJson) + 
				"&sequence_no=" + sequence_no + "&_json_att=";
		
		System.out.println("Janine:APITicket.payConfirmN.param--->" 
				+ param);
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:APITicket.payConfirmN.rspHttp--->" 
				+ rspHttp);
		
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
		System.out.println("Janine:payConfirmN.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	} 
	
	/**
	 * 立即改签
	 */
	@ResponseBody
	@RequestMapping(value = "/payfinish", method = RequestMethod.POST)  
	public String payfinish(HttpServletRequest request) {  
		
		String get_ticket_pass = WebUtil.getString(request, "get_ticket_pass", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.payfinish.进来--->" 
				+ "get_ticket_pass:" + get_ticket_pass);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.payfinishUrl;
		
		System.out.println("Janine:APITicket.payfinish.url--->" 
				 + url);
		
		String param = "get_ticket_pass=" + get_ticket_pass + "&_json_att=";
		
		System.out.println("Janine:APITicket.payfinish.param--->" 
				 + param);
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:APITicket.payfinish.rspHttp--->" 
				 + rspHttp);
		
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
		
		// Janine:返回的是HTML页面
		System.out.println("Janine:payfinish.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
		
//		System.out.println("Janine:payfinish.rspHttp.getResult()--->" + rspHttp.getResult());
//		return rspHttp.getResult();
		
	}  
	
}
