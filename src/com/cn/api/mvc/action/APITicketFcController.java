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

import com.cn.api.entity.OrderTikcet;
import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TicketOrderService;
import com.cn.api.module.service.TrainCodeService;
import com.cn.config.Config;
import com.cn.util.DateUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 返程
 */
@Controller
@RequestMapping("/")
public class APITicketFcController {

	@Autowired
	AppkeyService appkeyService;
	
	@Autowired
	TrainCodeService  trainCodeService;
	
	@Autowired
	TicketOrderService ticketOrderService;
	public TrainCodeService getTrainCodeService() {
		return trainCodeService;
	}

	public void setTrainCodeService(TrainCodeService trainCodeService) {
		this.trainCodeService = trainCodeService;
	}
	
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
	 * 购买返程车票，初始化
	 */

	@ResponseBody
	@RequestMapping(value = "/leftTicketInit", method = RequestMethod.POST)  
	public String leftTicketInit(HttpServletRequest request) {  
		String pre_step_flag = WebUtil.getString(request, "pre_step_flag", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.leftTicketInit.接收到的数据:"
				+ "pre_step_flag:" + pre_step_flag
				+ ",appkey:" + appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.leftTicketInitUrl;
		String param = "pre_step_flag=" + pre_step_flag + "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		String result = rspHttp.getResult();
		
		String from_station = "";
		String reg_1 = "var from_station = '(.*?)'";
		Pattern pattern_1 = Pattern.compile(reg_1);		
		Matcher matcher_1 = pattern_1.matcher(result);		
		while(matcher_1.find()) {			
			from_station = matcher_1.group(1);	
		}	
		
//		String from_station_name = "";
//		String reg_2 = "var from_station_name = '(.*?)'";
//		Pattern pattern_2 = Pattern.compile(reg_2);		
//		Matcher matcher_2 = pattern_2.matcher(result);		
//		while(matcher_2.find()) {			
//			from_station_name = matcher_2.group(1);	
//		}	
		
		String to_station = "";
		String reg_3 = "var to_station = '(.*?)'";
		Pattern pattern_3 = Pattern.compile(reg_3);		
		Matcher matcher_3 = pattern_3.matcher(result);		
		while(matcher_3.find()) {			
			to_station = matcher_3.group(1);	
		}	
//		
//		String to_station_name = "";
//		String reg_4 = "var to_station_name = '(.*?)'";
//		Pattern pattern_4 = Pattern.compile(reg_4);		
//		Matcher matcher_4 = pattern_4.matcher(result);		
//		while(matcher_4.find()) {			
//			to_station_name = matcher_4.group(1);	
//		}	
//		
		String trainDate = "";
		String reg_5 = "var trainDate = '(.*?)'";
		Pattern pattern_5 = Pattern.compile(reg_5);		
		Matcher matcher_5 = pattern_5.matcher(result);		
		while(matcher_5.find()) {			
			trainDate = matcher_5.group(1);	
		}	
		
		String backTrainDate = "";
		String reg_6 = "var backTrainDate = '(.*?)'";
		Pattern pattern_6 = Pattern.compile(reg_6);		
		Matcher matcher_6 = pattern_6.matcher(result);		
		while(matcher_6.find()) {			
			backTrainDate = matcher_6.group(1);	
		}	
		String from_station_name =trainCodeService.findTrain_name(from_station);
		String to_station_name =trainCodeService.findTrain_name(to_station);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("from_station", from_station);
		jsonResult.put("from_station_name",from_station_name);
		jsonResult.put("to_station", to_station);
		jsonResult.put("to_station_name", to_station_name);
		jsonResult.put("trainDate", trainDate);
		jsonResult.put("backTrainDate", backTrainDate);
		
		
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
		System.out.println("返程初始化数据--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
//	@ResponseBody
//	@RequestMapping(value="/leftTicketFc", method=RequestMethod.GET)
//	public String leftTicket(HttpServletRequest request) throws IOException {
//		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
//		String train_date = WebUtil.getString(request, "train_date", "");
////		String from_station = new  String(WebUtil.getString(request, "from_station", "").getBytes("ISO8859-1"),"utf-8");
////		String to_station = new  String(WebUtil.getString(request, "to_station", "").getBytes("ISO8859-1"),"utf-8");
//		
//		// Janine:测试此处的编码格式
//		String from_station_code = WebUtil.getString(request, "from_station", "");
//		String to_station_code = WebUtil.getString(request, "to_station", "");
//		// Janine:是车站名字
////		System.out.println("Janine:leftTicket.from_station--->"+from_station);
////		System.out.println("Janine:leftTicket.to_station--->"+to_station);
////	
////		String from_station_code =trainCodeService.findTrain(from_station);
////		String to_station_code =trainCodeService.findTrain(to_station);
//		// Janine:是车站码
//		System.out.println("Janine:leftTicket.from_station_code--->"+from_station_code);
//		System.out.println("Janine:leftTicket.to_station_code--->"+to_station_code);
//		
//		System.out.println("Janine:leftTicket.接收到的数据:"
//				+"to_station_code:" + to_station_code
//				+",from_station_code:" + from_station_code
//				+",purpose_codes:" + purpose_codes
//				+",train_date:" + train_date);
//		
//		String url = Config.queryLeftTicketUrl;
//		String param = "leftTicketDTO.train_date=" + train_date + "&leftTicketDTO.from_station=" + from_station_code + 
//						"&leftTicketDTO.to_station=" + to_station_code + "&purpose_codes=" + purpose_codes;
//		RspHttp rspHttp = HttpUtil.doGet(url, param, "");
//		
//		System.out.println("Janine:leftTicket.param--->" + param);
//		
//		//Janine:查看URL地址
//		System.out.println("Janine:leftTicket.url--->" + url);
//		
//		System.out.println("leftTicket数据:"+rspHttp.getResult());
//		RspData rspData = new RspData();
//		if(rspHttp.getCode() == 200) {
//			rspData.setFlag(true);
//			rspData.setMsg("");
//			rspData.setResult(rspHttp.getResult());
//		} else {
//			rspData.setFlag(false);
//			rspData.setMsg("失败");
//			rspData.setResult("");
//		}
//		
//		System.out.println("返程:leftTicket.rspData.toJsonStr():"+rspData.toJsonStr());
//		   
//		return rspData.toJsonStr();
//	}
	/**
	 * 初始化返程页面
	 */
	@ResponseBody
	@RequestMapping(value="/initFc", method=RequestMethod.GET)
	public String initFc(HttpServletRequest request) {
		
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.initFcUrl; 
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
		String goOrderDTO = ""; //往程票信息
		
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
		
		reg = "goOrderDTO=(.*?);";
		pattern = Pattern.compile(reg);		
		String temp = result.replace("var goOrderDTO=\"\";", "");
		matcher = pattern.matcher(temp);		
		while(matcher.find()) {			
			goOrderDTO = matcher.group(1);	
		}
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("globalRepeatSubmitToken", globalRepeatSubmitToken);
		jsonResult.put("key_check_isChange", key_check_isChange);
		jsonResult.put("leftTicketStr", leftTicketStr);
		jsonResult.put("train_location", train_location);
		jsonResult.put("queryLeftNewDetailDTO", queryLeftNewDetailDTO);
		jsonResult.put("queryLeftTicketRequestDTO", queryLeftTicketRequestDTO);
		jsonResult.put("goOrderDTO", goOrderDTO);
		
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
		
		System.out.println("Janine:APITicket.confirmGoForQueue.rspData.toJsonStr()--->"
			+rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 确认提交 
	 * 
	 * @param 		key_check_isChange（由initFc获得）
	 * @param 		leftTicketStr（由initFc获得）
	 * @param		train_location（由initFc获得）
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
	@RequestMapping(value = "/confirmBackForQueue", method = RequestMethod.POST)  
	public String confirmBackForQueue(HttpServletRequest request) {  
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
		
		System.out.println("Janine:APITicket.confirmBackForQueue.接收到的数据:"
				+"key_check_isChange:"+key_check_isChange
				+",leftTicketStr:"+leftTicketStr
				+",train_location:"+train_location
				+",passengerTicketStr:"+passengerTicketStr
				+",oldPassengerStr:"+oldPassengerStr
				+",purpose_codes:"+purpose_codes
				+",randCode:"+randCode
				+",REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.confirmFcQueueUrl;
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
		
		System.out.println("Janine:APITicket.confirmBackForQueue.rspData.toJsonStr()--->"
				+rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 查看订单结果
	 * 
	 * @param 		orderSequence_no（订单id）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/resultOrderForFcQueue", method = RequestMethod.POST)  
	public String resultOrderForFcQueue(HttpServletRequest request) {  
		String orderSequence_no = WebUtil.getString(request, "orderSequence_no", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.resultOrderForFcQueue.接收到的数据:"
				+"orderSequence_no:"+orderSequence_no
				+",REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.resultOrderFcUrl;
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
		
		System.out.println("Janine:APITicket.resultOrderForFcQueue.rspData.toJsonStr()--->"
				+rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 网上支付初始化
	 */
	@ResponseBody
	@RequestMapping(value="/payOrderFcInit", method=RequestMethod.GET)
	public String payOrderFcInit(HttpServletRequest request) {
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "token", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.payOrderFcInit.接收到的数据:"
				+"REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN);
		
		String cookie = appkeyService.findCookies(appkey);
		
		Calendar calendar = Calendar.getInstance();
		String url = Config.payOrderInitUrl + "?random=" + calendar.getTimeInMillis();
		String param = "_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
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
		
		String fcTicketForm = "";
		int indexOfFcTicket = result.indexOf("fcTicketTitleForm");
		if(indexOfFcTicket > -1) {
			String ticketTitleForm = result.substring(indexOfFcTicket);
			fcTicketForm = ticketTitleForm.substring(ticketTitleForm.indexOf("{"), ticketTitleForm.indexOf("}") + 1);
		}
		
		String fcPassangerForm = "";
		int indexOfFcPassanger = result.indexOf("fcPassangerTicketList");
		if(indexOfFcPassanger > -1) {
			String passangerTicketList = result.substring(indexOfFcPassanger);
			fcPassangerForm = passangerTicketList.substring(passangerTicketList.indexOf("["), passangerTicketList.indexOf("]") + 1);
		}
		
		String sequence_no = "";
		String reg = "var sequence_no = '(.*?)'";
		Pattern pattern = Pattern.compile(reg);		
		Matcher matcher = pattern.matcher(result);		
		while(matcher.find()) {			
			sequence_no = matcher.group(1);	
		}	
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("ticketForm", ticketForm);
		jsonResult.put("passangerForm", passangerForm);
		jsonResult.put("fcTicketForm", fcTicketForm);
		jsonResult.put("fcPassangerForm", fcPassangerForm);
		jsonResult.put("sequence_no", sequence_no);
		jsonResult.put("parOrderDTOJson", parOrderDTOJson);
		jsonResult.put("orderRequestDTOJson", orderRequestDTOJson);
		/*****************添加订单到数据库Start*******************************/
		
		
		String data = jsonResult.toString();
		
		JSONObject jsonObject = JSONObject.fromObject(data);
		
		OrderTikcet ticketDTO = new OrderTikcet();
		
		OrderTikcet fcTicketDTO = new OrderTikcet();
		
		String ticketForm_sql = jsonObject.getString("ticketForm");
		String passangerForm_sql  = jsonObject.getString("passangerForm");
		String fcTicketForm_sql  = jsonObject.getString("fcTicketForm");
		String fcPassangerForm_sql  = jsonObject.getString("fcPassangerForm");
		
		ticketDTO = (OrderTikcet) JSONObject.toBean(JSONObject.fromObject(ticketForm_sql), OrderTikcet.class);
		fcTicketDTO = (OrderTikcet) JSONObject.toBean(JSONObject.fromObject(fcTicketForm_sql), OrderTikcet.class);
		
		Timestamp current = DateUtil.getCurrentTimestamp();
		
		JSONArray jsonArray = JSONArray.fromObject(passangerForm_sql);
		for(int i = 0; i < jsonArray.size(); i++) {
			TicketOrder item = new TicketOrder();
			JSONObject json = jsonArray.getJSONObject(i);
			
			
			item.setCreateTime(current);//订单生成时间
			item.setUpdateTime(null);//订单更新时间
			item.setPassenger_name(json.getJSONObject("passengerDTO").getString("passenger_name"));//乘客名字
			item.setPassenger_id_type_name(json.getJSONObject("passengerDTO").getString("passenger_id_type_name"));//证件类型
			item.setPassenger_id_no(json.getJSONObject("passengerDTO").getString("passenger_id_no"));//证件号码
			item.setPassenger_phone_number(json.getJSONObject("passengerDTO").getString("mobile_no"));//手机号
			item.setTicket_type_name(json.getString("ticket_type_name"));//ticket_type：票种
			item.setSeat_type_name(json.getString("seat_type_name"));//seat_type：座位类型
			item.setCoach_name(json.getString("coach_name"));
			item.setSeat_name(json.getString("seat_name"));
			item.setStation_train_code(ticketDTO.getStation_train_code());
			item.setStart_station_name(ticketDTO.getFrom_station_name());
			item.setEnd_station_name(ticketDTO.getTo_station_name());
			item.setStart_time(ticketDTO.getStart_time());
			item.setArrive_time(ticketDTO.getArrive_time());
			item.setStart_train_date(ticketDTO.getTrain_date());
			item.setOrderstate("0");
			item.setOrdernumber(json.getString("sequence_no"));
			double ticket_price = json.getDouble("ticket_price")/100;
			item.setTicket_price(ticket_price);
			ticketOrderService.addItem(item);
		}
		
		JSONArray fcJsonArray = JSONArray.fromObject(fcPassangerForm_sql);
		for(int i = 0; i < fcJsonArray.size(); i++) {
			TicketOrder itemT = new TicketOrder();
			JSONObject json = fcJsonArray.getJSONObject(i);
			itemT.setPassenger_name(json.getJSONObject("passengerDTO").getString("passenger_name"));
			itemT.setPassenger_id_type_name(json.getJSONObject("passengerDTO").getString("passenger_id_type_name"));
			itemT.setPassenger_id_no(json.getJSONObject("passengerDTO").getString("passenger_id_no"));
			itemT.setTicket_type_name(json.getString("ticket_type_name"));
			itemT.setSeat_type_name(json.getString("seat_type_name"));
			itemT.setCoach_name(json.getString("coach_name"));
			itemT.setSeat_name(json.getString("seat_name"));
			
			itemT.setCreateTime(current);//订单生成时间
			itemT.setUpdateTime(null);//订单更新时间
			itemT.setPassenger_name(json.getJSONObject("passengerDTO").getString("passenger_name"));//乘客名字
			itemT.setPassenger_id_type_name(json.getJSONObject("passengerDTO").getString("passenger_id_type_name"));//证件类型
			itemT.setPassenger_id_no(json.getJSONObject("passengerDTO").getString("passenger_id_no"));//证件号码
			itemT.setPassenger_phone_number(json.getJSONObject("passengerDTO").getString("mobile_no"));//手机号
			itemT.setTicket_type_name(json.getString("ticket_type_name"));//ticket_type：票种
			itemT.setSeat_type_name(json.getString("seat_type_name"));//seat_type：座位类型
			itemT.setCoach_name(json.getString("coach_name"));
			itemT.setSeat_name(json.getString("seat_name"));
			itemT.setStation_train_code(ticketDTO.getStation_train_code());
			itemT.setStart_station_name(ticketDTO.getFrom_station_name());
			itemT.setEnd_station_name(ticketDTO.getTo_station_name());
			itemT.setStart_time(ticketDTO.getStart_time());
			itemT.setArrive_time(ticketDTO.getArrive_time());
			itemT.setStart_train_date(ticketDTO.getTrain_date());
			itemT.setOrderstate("0");
			itemT.setOrdernumber(json.getString("sequence_no"));
			double ticket_price = json.getDouble("ticket_price")/100;
			itemT.setTicket_price(ticket_price);
			ticketOrderService.addItem(itemT);
		}
		
		System.out.println("添加订单到数据库Star************");
		System.out.println("jsonResult.toString()************"+jsonResult.toString());
		System.out.println("添加订单到数据库SEnd************");
		/*****************添加订单到数据库SEnd*******************************/
		
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
		
		System.out.println("Janine:APITicket.payOrderFcInit.rspData.toJsonStr()--->"
				+rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
}
