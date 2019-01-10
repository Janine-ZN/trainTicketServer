package com.cn.api.mvc.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.bean.TrainNo;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TrainCodeService;
import com.cn.api.module.service.TrainNoService;
import com.cn.config.Config;
import com.cn.util.DateUtil;
import com.cn.util.DoUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.StringUtil;
import com.cn.util.WebUtil;
import com.googlecode.jsonplugin.annotations.JSON;

/**
 * 车票预订
 */
@Controller
@RequestMapping("/")
public class APITicketDcController {

	@Autowired
	AppkeyService appkeyService;
	
	
	@Autowired
	TrainCodeService  trainCodeService;
	
	@Autowired
	TrainNoService  trainNoService;
	

	public TrainNoService getTrainNoService() {
		return trainNoService;
	}

	public void setTrainNoService(TrainNoService trainNoService) {
		this.trainNoService = trainNoService;
	}

	public TrainCodeService getTrainCodeService() {
		return trainCodeService;
	}

	public void setTrainCodeService(TrainCodeService trainCodeService) {
		this.trainCodeService = trainCodeService;
	}

	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}
	
	/**
	 * 余票查询
	 * 
	 * @param 		purpose_codes（普通：ADULT，学生：0X00）
	 * @param 		train_date（出发日期）
	 * @param 		from_station（出发地代码）
	 * @param 		to_station（目的地代码）
	 * 
	 * @return		12306返回的json
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="/leftTicket", method=RequestMethod.GET)
	public String leftTicket(HttpServletRequest request) throws IOException {
		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
		String train_date = WebUtil.getString(request, "train_date", "");
//		String from_station = new  String(WebUtil.getString(request, "from_station", "").getBytes("ISO8859-1"),"utf-8");
//		String to_station = new  String(WebUtil.getString(request, "to_station", "").getBytes("ISO8859-1"),"utf-8");
		
		// Janine:测试此处的编码格式
		String from_station = WebUtil.getString(request, "from_station", "");
		String to_station = WebUtil.getString(request, "to_station", "");
		// Janine:是车站名字
		System.out.println("Janine:leftTicket.from_station--->"+from_station);
		System.out.println("Janine:leftTicket.to_station--->"+to_station);
	
		String from_station_code =trainCodeService.findTrain(from_station);
		String to_station_code =trainCodeService.findTrain(to_station);
		//if(trainCodeService.findTrain(from_station)==""){from_station_code=from_station;to_station_code=to_station;}
		// Janine:是车站码
		System.out.println("Janine:leftTicket.from_station_code--->" + from_station_code);
		System.out.println("Janine:leftTicket.to_station_code--->" + to_station_code);
		
		System.out.println("Janine:leftTicket.接收到的数据:"
				+"to_station_code:" + to_station_code
				+",from_station_code:" + from_station_code
				+",purpose_codes:" + purpose_codes
				+",train_date:" + train_date);
		
		String url = Config.queryLeftTicketUrl;
		String param = "leftTicketDTO.train_date=" + train_date + "&leftTicketDTO.from_station=" + from_station_code + 
						"&leftTicketDTO.to_station=" + to_station_code + "&purpose_codes=" + purpose_codes;
		RspHttp rspHttp = HttpUtil.doGet(url, param, "");
		
		System.out.println("Janine:leftTicket.param--->" + param);
		
		//Janine:查看URL地址
		System.out.println("Janine:leftTicket.url--->" + url);
		
		System.out.println("leftTicket数据:"+rspHttp.getResult());
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
		
		System.out.println("返程:leftTicket.rspData.toJsonStr():"+rspData.toJsonStr());
		   
		return rspData.toJsonStr();
	}
	
	/**
	 * 点击“预订”检查车票信息
	 * 
	 * @param 		back_train_date（返程日期）
	 * @param 		purpose_codes（普通：ADULT，学生：0X00）
	 * @param 		query_from_station_name（出发站名）
	 * @param 		query_to_station_name（到达站名）
	 * @param 		secretStr（查询时返回的加密字符串）
	 * @param 		tour_flag（单程：dc，往程：wc，返程：fc，改签：gc）
	 * @param 		train_date（出发日期）
	 * 
	 * @return		12306返回的json
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/submitOrderRequest", method = RequestMethod.POST)  
	public String submitOrderRequest(HttpServletRequest request) throws UnsupportedEncodingException {  
		String back_train_date = WebUtil.getString(request, "back_train_date", "");
		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
		
		String query_from_station_name = WebUtil.getString(request, "query_from_station_name", "");
		String query_to_station_name = WebUtil.getString(request, "query_to_station_name", "");
		
		// Janine:在此处转一下编码格式
//		String query_from_station_name =new  String(WebUtil.getString(request, "query_from_station_name", "").getBytes("ISO8859-1"),"utf-8");
//		String query_to_station_name =new  String(WebUtil.getString(request, "query_to_station_name", "").getBytes("ISO8859-1"),"utf-8");
		
		String secretStr = WebUtil.getString(request, "secretStr", "");
		String tour_flag = WebUtil.getString(request, "tour_flag", "");
		String train_date = WebUtil.getString(request, "train_date", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine20170111.APITicket.submitOrderRequest.tour_flag--->"
				+"tour_flag:" + tour_flag);
		
		// Janine20170111修改:测试数据
		if(tour_flag.equals("fc")){
			System.out.println("Janine20170111FC.APITicket.submitOrderRequest.接收数据--->"
					+"query_from_station_name:" + query_from_station_name
					+",query_to_station_name:" + query_to_station_name
					+",back_train_date:" + back_train_date
					+",train_date:" + train_date
					+",purpose_codes:" + purpose_codes
					+",tour_flag:" + tour_flag
					+",secretStr:" + secretStr);
		}else if(tour_flag.equals("wc")){
			System.out.println("Janine20170111WC.APITicket.submitOrderRequest.接收数据--->"+"query_from_station_name:" + query_from_station_name
					+",query_to_station_name:" + query_to_station_name
					+",back_train_date:" + back_train_date
					+",train_date:" + train_date
					+",purpose_codes:" + purpose_codes
					+",tour_flag:" + tour_flag
					+",secretStr:" + secretStr);
		}else if(tour_flag.equals("dc")){
			System.out.println("Janine20170111DC.APITicket.submitOrderRequest.接收数据--->"+"query_from_station_name:" + query_from_station_name
					+",query_to_station_name:" + query_to_station_name
					+",back_train_date:" + back_train_date
					+",train_date:" + train_date
					+",purpose_codes:" + purpose_codes
					+",tour_flag:" + tour_flag
					+",secretStr:" + secretStr);
		}
		
		
		String cookie = appkeyService.findCookies(appkey);
		
		System.out.println(cookie);
		
		secretStr = secretStr.replaceAll(" ", "+");
		
		String url = Config.submitOrderRequestUrl;
		System.out.println("Janine.APITicket.submitOrderRequest.url--->"+url);
		
		String param = "secretStr=" + EncoderUtil.encode(secretStr) + "&train_date=" + train_date + 
				"&back_train_date=" + back_train_date + "&tour_flag=" + tour_flag +
				"&purpose_codes=" + purpose_codes + 
				"&query_from_station_name=" + EncoderUtil.encode(query_from_station_name) + 
				"&query_to_station_name=" + EncoderUtil.encode(query_to_station_name) + "&undefined";
		
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
		System.out.println("Janine:submitOrderRequest.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
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
	@RequestMapping(value="/initDc", method=RequestMethod.GET)
	public String initDc(HttpServletRequest request) {
		
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.initDcUrl; 
		String param = "_json_att=";
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		String result = rspHttp.getResult();
		
		//System.out.println("***initDc ticket param " + param);
		//System.out.println("***initDc ticket result " + rspHttp.getResult());
		
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
		return rspData.toJsonStr();
	}
	
	/**
	 * 获取乘客信息
	 * 
	 * @param 		globalRepeatSubmitToken（初始化订单时生成的加密字符串）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value="/getPassenger", method=RequestMethod.GET)
	public String getPassenger(HttpServletRequest request) {
		
		String globalRepeatSubmitToken = WebUtil.getString(request, "globalRepeatSubmitToken", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.getPassengerUrl;
		String param = "globalRepeatSubmitToken=" + globalRepeatSubmitToken + "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		//System.out.println("***getPassenger ticket param " + param);
		//System.out.println("***getPassenger ticket result " + rspHttp.getResult());
		
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
		return rspData.toJsonStr();
	}
	
	/**
	 * 检测验证码（提交订单）
	 * 
	 * @param 		randCode（验证码的坐标）
	 * @param		rand（传值：randp）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value = "/checkForOrder", method = RequestMethod.POST)  
	public String checkForOrder(HttpServletRequest request) {  
		System.out.println("checkForOrder.进入--->");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String rand = WebUtil.getString(request, "rand", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.chkPassCodeUrl;
		String param = "REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN + "&randCode=" + randCode + "&rand=" + rand + "&_json_att=";
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:checkForOrder ticket param " + param);
		System.out.println("Janine:checkForOrder.传入的结果--->" + "REPEAT_SUBMIT_TOKEN:"
					+ REPEAT_SUBMIT_TOKEN+",randCode:" + randCode+",rand:" + rand +",appkey:"+appkey);
		
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
		System.out.println("Janine:checkForOrder.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 检查确认订单
	 * 
	 * @param 		cancel_flag（传值：2）
	 * @param 		bed_level_order_num（传值：000000000000000000000000000000）
	 * @param 		passengerTicketStr（seat_type,0,ticket_type,name,id_type,id_no,mobile_no,N）-->多个乘客用_隔开
	 * 				seat_type：座位类型（-1、无座，1、硬座，2、软座，3、硬卧，4、软卧，6、高级软卧，M、一等座，O、二等座，P、特等座，9、商务座）-->二等座是大写字母O，不是数字0
	 * 				ticket_type：票种（1、成人票，2、儿童票，3、学生票，4、残军票）
	 * 				name：乘客姓名
	 * 				it_type：证件类型（1、二代省份证，B、护照，C、港澳通行证，G、台湾通行证）
	 * 				id_no：证件号码
	 * 				mobile_no：手机号码（没有就传空字符串）
	 * @param 		oldPassengerStr（name,id_type,id_no,passenger_type）-->这几个字段可从乘客信息里获取
	 * @param 		tour_flag
	 * @param 		randCode（验证码）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value = "/checkOrderInfo", method = RequestMethod.POST)  
	public String checkOrderInfo(HttpServletRequest request) {  
		
		String cancel_flag = WebUtil.getString(request, "cancel_flag", "");
		String bed_level_order_num = WebUtil.getString(request, "bed_level_order_num", "");
		String passengerTicketStr = WebUtil.getString(request, "passengerTicketStr", "");
		String oldPassengerStr = WebUtil.getString(request, "oldPassengerStr", "");
		String tour_flag = WebUtil.getString(request, "tour_flag", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.checkOrderInfo.接收到的数据----->" 
				+"cancel_flag:"+cancel_flag
				+";bed_level_order_num:"+bed_level_order_num
				+";passengerTicketStr:"+passengerTicketStr
				+";oldPassengerStr:"+oldPassengerStr
				+";tour_flag:"+tour_flag
				+";randCode:"+randCode
				+";REPEAT_SUBMIT_TOKEN:"+REPEAT_SUBMIT_TOKEN
				);
		
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.checkOrderInfoUrl;
		String param = "cancel_flag=" + cancel_flag + "&bed_level_order_num=" + bed_level_order_num +
					"&passengerTicketStr=" + EncoderUtil.encode(passengerTicketStr) + 
					"&oldPassengerStr=" + EncoderUtil.encode(oldPassengerStr) +
					"&tour_flag=" + tour_flag + "&randCode=" + randCode +
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
		System.out.println("Janine:checkOrderInfo.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 查看排队人数
	 * 
	 * @param 		fromStationTelecode（出发地代码）
	 * @param 		toStationTelecode（目的地代码）
	 * @param 		leftTicket（）
	 * @param 		purpose_codes（）
	 * @param		seatType
	 * @param 		stationTrainCode（车次）
	 * @param 		train_date（出发日期）
	 * @param		train_no（火车编号）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/getQueueCount", method = RequestMethod.POST)  
	public String getQueueCount(HttpServletRequest request) {  
		String fromStationTelecode = WebUtil.getString(request, "fromStationTelecode", "");
		String leftTicket = WebUtil.getString(request, "leftTicket", "");
		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
		String seatType = WebUtil.getString(request, "seatType", "");
		String stationTrainCode = WebUtil.getString(request, "stationTrainCode", "");
		String toStationTelecode = WebUtil.getString(request, "toStationTelecode", "");
		String train_date = WebUtil.getString(request, "train_date", "");
		String train_no = WebUtil.getString(request, "train_no", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
		String train_date_str = "";
		try {
			Date date = sdf.parse(train_date.substring(0, 4) + "-" + train_date.substring(4, 6) + "-" + train_date.substring(6, 8));
			train_date_str = date.toString();
			train_date_str = train_date_str.substring(0, 10) + " " + train_date.substring(0, 4) + " 00:00:00 GMT+0800";
			train_date_str = EncoderUtil.encode(train_date_str);
		} catch(Exception e) {
			train_date_str = "";
		}
		
		String url = Config.getQueueCountUrl;
		String param = "fromStationTelecode=" + fromStationTelecode + "&toStationTelecode=" + toStationTelecode +
					"&leftTicket=" + leftTicket + "&purpose_codes=" + purpose_codes + 
					"&seatType=" + seatType + "&stationTrainCode=" + stationTrainCode + 
					"&train_date=" + train_date_str + "&train_no=" + train_no + 
					"&_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		//System.out.println("***getQueueCount ticket param " + param);
		//System.out.println("***getQueueCount ticket result " + rspHttp.getResult());
		
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
		return rspData.toJsonStr();
	}
	
	/**
	 * 确认提交 
	 * 
	 * @param 		dwAll（）
	 * @param 		key_check_isChange（由initDc获得）
	 * @param 		leftTicketStr（由initDc获得）
	 * @param		train_location（由initDc获得）
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
	@RequestMapping(value = "/confirmSingleForQueue", method = RequestMethod.POST)  
	public String confirmSingleForQueue(HttpServletRequest request) {  
		String dwAll = WebUtil.getString(request, "dwAll", "N");
		String choose_seats = WebUtil.getString(request, "choose_seats", "");       //Janine:
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
		
		
		System.out.println("Janine:ajax_confirmSingleForQueue.传过来的值:"
				+ "dwAll:" + dwAll
				+ ";key_check_isChange:"+key_check_isChange
				+ ";leftTicketStr:" + leftTicketStr 
				+ ";train_location:" + train_location
				+ ";passengerTicketStr:" + passengerTicketStr
				+ ";oldPassengerStr:" + oldPassengerStr
				+ ";purpose_codes:"+ purpose_codes
				+ ";randCode:" + randCode
				+ ";choose_seats:" + choose_seats
				+ ";roomType:" + roomType
				+ ";REPEAT_SUBMIT_TOKEN:" + REPEAT_SUBMIT_TOKEN
				+ ";app_key:"+ appkey);
		
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.confirmDcQueueUrl;
		String param = "dwAll=" + dwAll + "&key_check_isChange=" + key_check_isChange +
				"&leftTicketStr=" + EncoderUtil.encode(leftTicketStr) + "&train_location=" + train_location +
				"&passengerTicketStr=" + EncoderUtil.encode(passengerTicketStr) + 
				"&oldPassengerStr=" + EncoderUtil.encode(oldPassengerStr) +
				"&purpose_codes=" + purpose_codes + "&randCode=" + randCode + "&roomType=" + roomType +
				"&_json_att=&REPEAT_SUBMIT_TOKEN=" + EncoderUtil.encode(REPEAT_SUBMIT_TOKEN) + "&choose_seats=" + choose_seats ;
		
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
		System.out.println("Janine:confirmSingleForQueue.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 查询出票等待时间（返回等待时间，如果waitTime小于0，则获取订单信息orderId，如果大于0，则继续轮询）
	 * 
	 * @param 		tourFlag（）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/queryOrderWaitTime", method = RequestMethod.GET)  
	public String queryOrderWaitTime(HttpServletRequest request) {  
		System.out.println("queryOrderWaitTime.进入--->");
		String tourFlag = WebUtil.getString(request, "tourFlag", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("queryOrderWaitTime.接收的值:"+"tourFlag:" + tourFlag
				+ ";REPEAT_SUBMIT_TOKEN:" + REPEAT_SUBMIT_TOKEN +";appkey:"+appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		Calendar calendar = Calendar.getInstance();
		long random = calendar.getTimeInMillis();
		String url = Config.queryOrderTimeUrl;
		String param = "random=" + random + "&tourFlag=" + tourFlag + "&_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;
		
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		
		//System.out.println("***queryOrderWaitTime ticket param " + param);
		//System.out.println("***queryOrderWaitTime ticket result " + rspHttp.getResult());
		
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
		System.out.println("Janine:queryOrderWaitTime.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 查看订单结果
	 * 
	 * @param 		orderSequence_no（订单id）
	 * @param 		REPEAT_SUBMIT_TOKEN（初始化订单时生成的加密字符串）
	 */
	@ResponseBody
	@RequestMapping(value = "/resultOrderForDcQueue", method = RequestMethod.POST)  
	public String resultOrderForDcQueue(HttpServletRequest request) {  
		String orderSequence_no = WebUtil.getString(request, "orderSequence_no", "");
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "REPEAT_SUBMIT_TOKEN", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("resultOrderForDcQueue.接收的值:"+"orderSequence_no:" + orderSequence_no
				+ ";REPEAT_SUBMIT_TOKEN:" + REPEAT_SUBMIT_TOKEN +";appkey:"+appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.resultOrderDcUrl;
		String param = "orderSequence_no=" + orderSequence_no + "&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN + "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		//System.out.println("***resultOrderForDcQueue ticket param " + param);
		//System.out.println("***resultOrderForDcQueue ticket result " + rspHttp.getResult());
		
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
		System.out.println("Janine:resultOrderForDcQueue.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
}
