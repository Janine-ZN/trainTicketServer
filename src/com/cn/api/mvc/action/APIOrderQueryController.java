package com.cn.api.mvc.action;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.entity.OrderTikcet;
import com.cn.api.entity.order.OrderDTODataList;
import com.cn.api.entity.order.Tickets;
import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TicketOrderService;
import com.cn.api.module.service.TrainCodeService;
import com.cn.config.Config;
import com.cn.util.DateUtil;
import com.cn.util.DoUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.StringUtil;
import com.cn.util.WebUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单
 */
@Controller
@RequestMapping("/")
public class APIOrderQueryController {

	@Autowired
	AppkeyService appkeyService;
	
	@Autowired
	TicketOrderService ticketOrderService;
	
	@Autowired
	TrainCodeService  trainCodeService;
	
	
	
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
	
	public TicketOrderService getTicketOrderService() {
		return ticketOrderService;
	}

	public void setTicketOrderService(TicketOrderService ticketOrderService) {
		this.ticketOrderService = ticketOrderService;
	}

	/**
	 * 未完成订单
	 */
	@ResponseBody
	@RequestMapping(value="/unfinishedOrder", method=RequestMethod.GET)
	public String unfinishedOrder(HttpServletRequest request) {
		
		String queryType = WebUtil.getString(request, "queryType", "");
		String queryStartDate = WebUtil.getString(request, "queryStartDate", DateUtil.getCurrentDate());
		String queryEndDate = WebUtil.getString(request, "queryEndDate", DateUtil.getCurrentDate());
		String sequeue_train_name = WebUtil.getString(request, "sequeue_train_name", "");
		
		int pageIndex = WebUtil.getInt(request, "pageIndex", 0);
		int pageSize = WebUtil.getInt(request, "pageSize", 10);
		
		
		String come_from_flag = "my_order";
		String query_where = "G";
		
		String appkey = WebUtil.getString(request, "appkey", "");
		String sequence_no = WebUtil.getString(request, "sequence_no", ""); 
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.queryMyOrderNoCompleteUrl;
		String param = "_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		/**查询为出行订单
		 * 
		 * 退单  0表示未付订单   1 付款订单  2取消订单  3退票订单  4改签订单
		 * **/
		if (!StringUtil.isNullOrEmpty(sequence_no)) {
			//如果订单号不为空
			//查看是否有订单,如果有订单就属于未支付状态,如果没有订单,再去查为出行订单
			JSONObject jsonObject = JSONObject.fromObject(rspHttp.getResult());
			if ( jsonObject.getJSONObject("data").isNullObject()) {
				//没找到订单,到未出行订单
				
				String ticket_url = Config.queryMyOrderUrl;
				String ticket_param = "come_from_flag=" + come_from_flag + "&pageIndex=" + pageIndex + "&pageSize=" + pageSize + 
						"&queryStartDate=" + queryStartDate + "&queryEndDate=" + queryEndDate + 
						"&queryType=" + queryType + "&query_where=" + query_where + 
						"&sequeue_train_name=" + EncoderUtil.encode(sequeue_train_name);
				
				RspHttp ticket_rspHttp = HttpUtil.doPost(ticket_url, ticket_param, cookie);
				String result =ticket_rspHttp.getResult();
				Timestamp current = DateUtil.getCurrentTimestamp();
				JSONObject ticket_jsonObject = JSONObject.fromObject(result);
				boolean status = ticket_jsonObject.getBoolean("status"); //状态码
				if(status) {
						JSONObject ticket_dataObject = ticket_jsonObject.getJSONObject("data"); //数据
						JSONArray jsonArray = ticket_dataObject.getJSONArray("OrderDTODataList");

						for (int i = 0; i < jsonArray.size(); i++) {
							String ticket_sequence_no=jsonArray.getJSONObject(0).getString("sequence_no");
							if (ticket_sequence_no.equals(sequence_no)) {
								TicketOrder iteam = ticketOrderService.findItem(sequence_no);
								iteam.setOrderstate("1");
								iteam.setUpdateTime(current);
								ticketOrderService.updateItem(iteam);
							}else {
								TicketOrder iteam = ticketOrderService.findItem(sequence_no);
								iteam.setOrderstate("2");
								iteam.setUpdateTime(current);
								ticketOrderService.updateItem(iteam);
							}
							
						}
					}
				
			}			
		}
		
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
		System.out.println("于磊磊:unfinishedOrder.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
		
	}
	
	/**
	 * 取消排队订单（未出票，订单排队中）
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelQueueOrder", method = RequestMethod.POST)  
	public String cancelQueueOrder(HttpServletRequest request) {  
		String tourFlag = WebUtil.getString(request, "tourFlag", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.cancelQueueOrderUrl;
		String param = "_json_att=&tourFlag=" + tourFlag;
		
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
		System.out.println("Janine:cancelQueueOrder.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 取消订单（已出票，但未付款）
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)  
	public String cancelOrder(HttpServletRequest request) {  
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String cancel_flag = WebUtil.getString(request, "cancel_flag", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:cancelOrder.取消订单传过来的参数--->" 
						+ "sequence_no:" + sequence_no
						+ ",cancel_flag:" + cancel_flag);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.cancelOrderUrl;
		String param = "sequence_no=" + sequence_no + "&_json_att=&cancel_flag=" + cancel_flag;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		/**
		 * 退单  0表示未付订单   1 付款订单  2取消订单  3退票订单  4改签订单
		 */
		try{
		TicketOrder ticketOrder = ticketOrderService.findItem(sequence_no);
		
		ticketOrder.setUpdateTime(DateUtil.getCurrentTimestamp());
		ticketOrder.setOrderstate("2");
		ticketOrderService.updateItem(ticketOrder);
		}catch(Exception e){}
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
		System.out.println("Janine:cancelOrder.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	/**
	 * 取消订单（已出票，但未付款）
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel", method = RequestMethod.POST)  
	public String cancel(HttpServletRequest request) {  
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String parOrderDTOJsonStr = WebUtil.getString(request, "parOrderDTOJson", "");
		String orderRequestDTOJsonStr = WebUtil.getString(request, "orderRequestDTOJson", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String orderRequestDTOJson = orderRequestDTOJsonStr.replace("\\\"", "\"");
		String parOrderDTOJson = parOrderDTOJsonStr.replace("\\\"", "\"");
		
		System.out.println("于磊磊:cancel.取消订单传过来的参数--->" 
						+ "sequence_no:" + sequence_no
						+ ",appkey:" + appkey
						+ ",parOrderDTOJson:" + parOrderDTOJson+ ",orderRequestDTOJson:" + orderRequestDTOJson);
		
		String cookie = appkeyService.findCookies(appkey);
		System.out.println("Janine20170110.APITicket.cancel.cookie====>" + cookie);
		
		String url = Config.cancelUrl;
		String param = "sequence_no=" + sequence_no + "&parOrderDTOJson=" 
					+  EncoderUtil.encode(parOrderDTOJson) +"&orderRequestDTOJson=" 
					+ EncoderUtil.encode(orderRequestDTOJson)+ "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		/**
		 * 退单  0表示未付订单   1 付款订单  2取消订单  3退票订单  4改签订单
		 */
		try{
		TicketOrder ticketOrder = ticketOrderService.findItem(sequence_no);
		System.out.println("Janine:ticketOrder=====>" + ticketOrder);
		ticketOrder.setUpdateTime(DateUtil.getCurrentTimestamp());
		ticketOrder.setOrderstate("2");
		ticketOrderService.updateItem(ticketOrder);
		}catch(Exception e){}
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
		System.out.println("你问我爱你有多少：" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	/**
	 * 历史订单
	 * 
	 * @param 		come_from_flag（默认为：my_order）
	 * @param 		pageIndex（页码，从0开始，第一页为0，第二页为1）
	 * @param 		pageSize（一页显示的条数）
	 * @param 		queryStartDate（查询日期-开始）
	 * @param 		queryEndDate（查询日期-结束）
	 * @param 		queryType（1、按订票日期查询，2、按乘车日期查询）
	 * @param 		query_where（G、未出行订单，H、历史订单）
	 * @param 		sequeue_train_name（订单号/车次/乘客姓名  若为空就传空字符串，若不为空，中文utf-8编码）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value="/finishedOrder_H", method=RequestMethod.GET)
	public String queryMyOrderH(HttpServletRequest request) {
		
		//Janine:首先,12306的接口https://kyfw.12306.cn/otn/queryOrder/queryMyOrder是POST请求方式
		
		String queryStartDate = WebUtil.getString(request, "queryStartDate", "");
		String queryEndDate = WebUtil.getString(request, "queryEndDate", "");
		String sequeue_train_name = WebUtil.getString(request, "sequeue_train_name", "");
		int pageIndex = WebUtil.getInt(request, "pageIndex", 0);
		int pageSize = WebUtil.getInt(request, "pageSize", 8); // Janine:10-->8
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:APITicket.finishedOrder_H.接收到的数据----->" 
				+"queryStartDate:"+queryStartDate
				+",queryEndDate:"+queryEndDate
				+",sequeue_train_name:"+sequeue_train_name);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String come_from_flag = "my_order";
		String queryType = "1";
		String query_where = "H";
		
		String url = Config.queryMyOrderUrl;
		String param = "come_from_flag=" + come_from_flag + "&pageIndex=" + pageIndex + "&pageSize=" + pageSize + 
				"&queryStartDate=" + queryStartDate + "&queryEndDate=" + queryEndDate + 
				"&queryType=" + queryType + "&query_where=" + query_where + 
				"&sequeue_train_name=" + EncoderUtil.encode(sequeue_train_name);
		
		System.out.println("Janine:APITicket.finishedOrder_H.param----->" + param );
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		System.out.println("Janine:APITicket.finishedOrder_H.rspHttp----->" + rspHttp );
		
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
		System.out.println("Janine:finishedOrder_H.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}	
	
	/**
	 * 未出行订单
	 * 
	 * @param 		come_from_flag（默认为：my_order）
	 * @param 		pageIndex（页码，从0开始，第一页为0，第二页为1）
	 * @param 		pageSize（一页显示的条数）
	 * @param 		queryStartDate（查询日期-开始）
	 * @param 		queryEndDate（查询日期-结束）
	 * @param 		queryType（1、按订票日期查询，2、按乘车日期查询）
	 * @param 		query_where（G、未出行订单，H、历史订单）
	 * @param 		sequeue_train_name（订单号/车次/乘客姓名  若为空就传空字符串，若不为空，中文utf-8编码）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value="/finishedOrder_G", method=RequestMethod.GET)
	public String queryMyOrderG(HttpServletRequest request) {
		String queryType = WebUtil.getString(request, "queryType", "");
		String queryStartDate = WebUtil.getString(request, "queryStartDate", "");
		String queryEndDate = WebUtil.getString(request, "queryEndDate", "");
		String sequeue_train_name = WebUtil.getString(request, "sequeue_train_name", "");
//		String query_where = WebUtil.getString(request, "query_where", "");  // Janine:未完成订单和历史订单，只有query_where不一样，未完成是G，历史是H
		
		int pageIndex = WebUtil.getInt(request, "pageIndex", 0);
		int pageSize = WebUtil.getInt(request, "pageSize", 10);
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:finishedOrder_G.传过来的值:" 
				+ "queryType:" + queryType 
				+ ",queryStartDate:" + queryStartDate
				+ ",queryEndDate:" + queryEndDate
				+ ",sequeue_train_name:" + sequeue_train_name 
				+ ",pageIndex:" + pageIndex
				+ ",pageSize:" + pageSize
				+ ",appkey:" + appkey);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String come_from_flag = "my_order";
		String query_where = "G";
		
		String url = Config.queryMyOrderUrl;
		String param = "come_from_flag=" + come_from_flag + "&pageIndex=" + pageIndex + "&pageSize=" + pageSize + 
				"&queryStartDate=" + queryStartDate + "&queryEndDate=" + queryEndDate + 
				"&queryType=" + queryType + "&query_where=" + query_where + 
				"&sequeue_train_name=" + EncoderUtil.encode(sequeue_train_name);
		
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
		System.out.println("Janine:finishedOrder_G.rspData.toJsonStr()--->" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}	
	
	/**
	 * 检查是否可以退票
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/returnTicketAffirm", method = RequestMethod.POST)  
	public String returnTicketAffirm(HttpServletRequest request) throws UnsupportedEncodingException {  
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String batch_no = WebUtil.getString(request, "batch_no", "");
		String coach_no = WebUtil.getString(request, "coach_no", "");
		String seat_no = WebUtil.getString(request, "seat_no", "");
		String start_train_date_page = WebUtil.getString(request, "start_train_date_page", "");
		String start_train_date_page1 = WebUtil.getString(request, "start_train_date_page", DateUtil.getCurrentDate());
		String train_code = WebUtil.getString(request, "train_code", "");
		String coach_name = WebUtil.getString(request, "coach_name", "");
		String seat_name = WebUtil.getString(request, "seat_name", "");
		String seat_type_name = WebUtil.getString(request, "seat_type_name", "");
		String train_date = WebUtil.getString(request, "train_date", "");
		String from_station_name = WebUtil.getString(request, "from_station_name", "");
		String to_station_name = WebUtil.getString(request, "to_station_name", "");
		String start_time = WebUtil.getString(request, "start_time", "");
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String appkey = WebUtil.getString(request, "appkey", "");

		String ticket_type_name = WebUtil.getString(request, "ticket_type_name", "");
		String passenger_id_type_name = WebUtil.getString(request, "passenger_id_type_name", "");
		String start_time_page = WebUtil.getString(request, "start_time_page", "");
		String arrive_time_page = WebUtil.getString(request, "arrive_time_page", "");
		String ticket_price = WebUtil.getString(request, "ticket_price", "");
		String passenger_id_no = WebUtil.getString(request, "passenger_id_no", "");
		String from_station_code =trainCodeService.findTrain(from_station_name);
		String to_station_code =trainCodeService.findTrain(to_station_name);
		String cookie = appkeyService.findCookies(appkey);
		System.out.println("Janine:returnTicketAffirm.接收到的数据:"
				+ "sequence_no:" + sequence_no 
   				+ ",batch_no:" + batch_no
   				+ ",coach_no:" + coach_no
   				+ ",seat_no:" + seat_no
   				+ ",start_train_date_page:" + start_train_date_page
   				+ ",train_code:" + train_code
   				+ ",coach_name:" + coach_name
   				+ ",seat_name:" + seat_name
   				+ ",seat_type_name:" + seat_type_name
   				+ ",train_date:" + train_date
   				+ ",from_station_name:" + from_station_name
   				+ ",to_station_name:" + to_station_name
   				+ ",start_time:" + start_time
   				+ ",passenger_name:" + passenger_name);
		
		String url = Config.returnTicketAffirmUrl;
		String param = "sequence_no=" + sequence_no + "&batch_no=" + batch_no + "&coach_no=" + coach_no +
				"&seat_no=" + seat_no + 
				"&start_train_date_page=" + EncoderUtil.encode(start_train_date_page1) +
				"&train_code=" + train_code + "&coach_name=" + coach_name + 
				"&seat_name=" + EncoderUtil.encode(seat_name) +
				"&seat_type_name=" + EncoderUtil.encode(seat_type_name) + 
				"&train_date=" + EncoderUtil.encode(train_date) + 
				"&from_station_name=" + from_station_code + 
				"&to_station_name=" + to_station_code +
				"&start_time=" + EncoderUtil.encode(start_time) + 
				"&passenger_name=" + EncoderUtil.encode(passenger_name) + "&_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		 
		 Timestamp current = DateUtil.getCurrentTimestamp();
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		try{
			TicketOrder ticketOrder = ticketOrderService.findItem(sequence_no);
			System.out.println("订单数据备份数据库保存成功&&&&&&&&&&&"+ticket_price+arrive_time_page+passenger_id_type_name);
			
			ticketOrder.setUpdateTime(current);
		
			ticketOrderService.updateItem(ticketOrder);
	} catch (Exception e) {
		System.out.println("订单数据备份数据库保存成功***********************************"+ticket_price+arrive_time_page+passenger_id_type_name);
		TicketOrder item = new TicketOrder();
		item.setCreateTime(current);// 订单生成时间
		item.setUpdateTime(current);// 订单更新时间
		item.setPassenger_name(passenger_name);// 乘客名字
		item.setPassenger_id_type_name(passenger_id_type_name);// 证件类型
		item.setPassenger_id_no(passenger_id_no);// 证件号码
		item.setPassenger_phone_number(null);// 手机号
		item.setTicket_type_name(ticket_type_name);// ticket_type：票种
		item.setSeat_type_name(seat_type_name);// seat_type：座位类型
		item.setCoach_name(coach_name);
		item.setSeat_name(seat_name);
		item.setStation_train_code(train_code);
		item.setStart_station_name(from_station_name);
		item.setEnd_station_name(to_station_name);
		item.setStart_time(start_time_page);
		item.setArrive_time(arrive_time_page);
		item.setStart_train_date(start_train_date_page.substring(0, start_train_date_page.length()-6));
		item.setOrderstate("1");
		item.setOrdernumber(sequence_no);
		//double ticket_price = json.getDouble("ticket_price") / 100;
	   item.setTicket_price(Double.parseDouble(ticket_price));
		ticketOrderService.addItem(item);
		System.out.println("订单数据备份数据库保存成功"+ticket_price+arrive_time_page+passenger_id_type_name+start_train_date_page.substring(0, start_train_date_page.length()-4));
		
		//System.out.println("订单数据备份数据库保存成功"+date);
	}
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
		System.out.println("Janine:returnTicketAffirm.rspData.toJsonStr()--->"+rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
	/**
	 * 退票
	 */
	@ResponseBody
	@RequestMapping(value = "/returnTicket", method = RequestMethod.POST)  
	public String returnTicket(HttpServletRequest request) {  
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("Janine:returnTicket.接收到的数据:"
				+ "sequence_no:" + sequence_no );
		
		String cookie = appkeyService.findCookies(appkey);
		System.out.println("于磊磊：cookie。。。"+cookie+cookie+cookie);
		String url = Config.returnTicketUrl;
		String param = "_json_att=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		/**
		 * 退单  0表示未付订单 1 付款订单  2取消订单  3退票订单  4改签订单
		 */
		//try {
		TicketOrder ticketOrder = ticketOrderService.findItem(sequence_no);
		System.out.println("于磊磊：ticketOrder。。。"+ticketOrder+ticketOrder+ticketOrder.start_station_name);
		System.out.println("TicketOrder退票**************" + ticketOrder);
		Timestamp current = DateUtil.getCurrentTimestamp();
		ticketOrder.setUpdateTime(current);
		ticketOrder.setOrderstate("3");
		ticketOrderService.updateItem(ticketOrder);
//		} catch (Exception e) {
//			System.out.println("订单数据备份数据库保存成功");
//		}
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
		System.out.println("Janine:returnTicket.rspData.toJsonStr():" + rspData.toJsonStr());
		return rspData.toJsonStr();
	}  
	
}
