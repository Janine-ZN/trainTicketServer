package com.cn.api.mvc.action;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TicketOrderService;
import com.cn.config.Config;
import com.cn.util.DateUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.StringUtil;
import com.cn.util.WebUtil;

@Controller
@RequestMapping("/")
public class APIOrderPayController {

	@Autowired
	AppkeyService appkeyService;

	@Autowired
	TicketOrderService ticketOrderService;

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
	 * 网上支付初始化
	 */
	@ResponseBody
	@RequestMapping(value = "/payOrderInit", method = RequestMethod.GET)
	public String payOrderInit(HttpServletRequest request) {
		String REPEAT_SUBMIT_TOKEN = WebUtil.getString(request, "token", "");
		String appkey = WebUtil.getString(request, "appkey", "");

		System.out.println("Janine:payOrderInit().jsonResult--->" + "REPEAT_SUBMIT_TOKEN:" + REPEAT_SUBMIT_TOKEN
				+ ";appkey:" + appkey);

		String cookie = appkeyService.findCookies(appkey);

		Calendar calendar = Calendar.getInstance();
		String url = Config.payOrderInitUrl + "?random=" + calendar.getTimeInMillis();
		String param = "_json_att=&REPEAT_SUBMIT_TOKEN=" + REPEAT_SUBMIT_TOKEN;

		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);

		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);

		String result = rspHttp.getResult();
		System.out.println("Janine20170110.APITicket.payOrderInit().result--->" + result);
		
		// Janine:添加parOrderDTOJson的解析
		String parOrderDTOJson = "";
		int index_5 = result.indexOf("var parOrderDTOJson");
		if (index_5 > -1) {
			// Janine:此处的解析跟payOrderGcInit.do的解析相似
			String temp = result.substring(index_5);
			temp = temp.substring(temp.indexOf("\'") + 1);
			parOrderDTOJson = temp.substring(0, temp.indexOf("\'"));
			System.out.println("Janine20170110.APITicket.payOrderInit().parOrderDTOJson--->" + parOrderDTOJson);
		}

		// Janine:添加orderRequestDTOJson的解析
		// Janine:这个值可以是null
		String orderRequestDTOJson = "";
		String reg_3 = "var orderRequestDTOJson = '(.*?)'";
		Pattern pattern_3 = Pattern.compile(reg_3);
		Matcher matcher_3 = pattern_3.matcher(result);
		while (matcher_3.find()) {
			orderRequestDTOJson = matcher_3.group(1);
		}
		System.out.println("Janine20170110:APITicket.payOrderInit().orderRequestDTOJson--->" + orderRequestDTOJson);

		String tour_flag = "";
		String reg_1 = "var tour_flag = '(.*?)'";
		Pattern pattern_1 = Pattern.compile(reg_1);
		Matcher matcher_1 = pattern_1.matcher(result);
		while (matcher_1.find()) {
			tour_flag = matcher_1.group(1);
		}
		String ticketForm = "";
		int indexOfTicket = result.indexOf("ticketTitleForm");
		if (indexOfTicket > -1) {
			String ticketTitleForm = result.substring(indexOfTicket);
			ticketForm = ticketTitleForm.substring(ticketTitleForm.indexOf("{"), ticketTitleForm.indexOf("}") + 1);
		}

		String passangerForm = "";
		int indexOfPassanger = result.indexOf("passangerTicketList");
		if (indexOfPassanger > -1) {
			String passangerTicketList = result.substring(indexOfPassanger);
			passangerForm = passangerTicketList.substring(passangerTicketList.indexOf("["),
					passangerTicketList.indexOf("]") + 1);
		}

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("ticketForm", ticketForm);
		jsonResult.put("passangerForm", passangerForm);
		jsonResult.put("tour_flag", tour_flag);
		jsonResult.put("parOrderDTOJson", parOrderDTOJson);
		jsonResult.put("orderRequestDTOJson", orderRequestDTOJson);
		String jsonTicketForm = ticketForm;
		String jsonPassangerForm = passangerForm;

		/***************** 添加订单到数据库Start *******************************/
		OrderTikcet ticketDTO = (OrderTikcet) JSONObject.toBean(JSONObject.fromObject(jsonTicketForm),
				OrderTikcet.class);

		JSONArray jsonArray = JSONArray.fromObject(jsonPassangerForm);
		Timestamp current = DateUtil.getCurrentTimestamp();
		for (int i = 0; i < jsonArray.size(); i++) {
			TicketOrder item = new TicketOrder();
			JSONObject json = jsonArray.getJSONObject(i);

			item.setCreateTime(current);// 订单生成时间
			item.setUpdateTime(null);// 订单更新时间
			item.setPassenger_name(json.getJSONObject("passengerDTO").getString("passenger_name"));// 乘客名字
			item.setPassenger_id_type_name(json.getJSONObject("passengerDTO").getString("passenger_id_type_name"));// 证件类型
			item.setPassenger_id_no(json.getJSONObject("passengerDTO").getString("passenger_id_no"));// 证件号码
			item.setPassenger_phone_number(json.getJSONObject("passengerDTO").getString("mobile_no"));// 手机号
			item.setTicket_type_name(json.getString("ticket_type_name"));// ticket_type：票种
			item.setSeat_type_name(json.getString("seat_type_name"));// seat_type：座位类型
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
			double ticket_price = json.getDouble("ticket_price") / 100;
			item.setTicket_price(ticket_price);
			ticketOrderService.addItem(item);
		}
		/***************** 添加订单到数据库End *******************************/
		System.out.println("Janine:payOrderInit().jsonResult--->" + jsonResult.toString());
		return jsonResult.toString();
	}

	/**
	 * 获取网上支付相关的参数
	 */
	@ResponseBody
	@RequestMapping(value = "/payGateway", method = RequestMethod.POST)
	public String payGateway(HttpServletRequest request) {
		String appkey = WebUtil.getString(request, "appkey", "");

		String cookie = appkeyService.findCookies(appkey);

		String url = Config.paycheckNewUrl;
		String param = "batch_nos=&coach_nos=&seat_nos=&passenger_id_types=&passenger_id_nos="
				+ "&passenger_names=&insure_price_all=&insure_types=&if_buy_insure_only=N&hasBoughtIns=&_json_att=";

		String result = "";
		try {
			RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);

			String data = rspHttp.getResult();

			System.out.println("Janine:payGateway.最初传输的真实数据--->" + data );
			
			JSONObject jsonObject = JSONObject.fromObject(data);
			boolean status = jsonObject.getBoolean("status"); // 状态
			if (status) {
				JSONObject dataObject = jsonObject.getJSONObject("data"); // 数据
				boolean flag = dataObject.getBoolean("flag");
				if (flag) {
					result = dataObject.getJSONObject("payForm").toString();
					System.out.println("Janine:payGateway.传输的真实数据--->" + result );
				}
			}
		} catch (Exception e) {
			result = "";
		}

		String interfaceName = "";
		String interfaceVersion = "";
		String tranData = "";
		String merSignMsg = "";
		String appId = "";
		String transType = "";
		String channelId = "";
		String merCustomIp = "";
		String orderTimeoutDate = "";
		try {
			JSONObject jsonObject = JSONObject.fromObject(result);

			interfaceName = jsonObject.getString("interfaceName");
			interfaceVersion = jsonObject.getString("interfaceVersion");
			tranData = StringUtil.replaceBlank(jsonObject.getString("tranData"));
			merSignMsg = StringUtil.replaceBlank(jsonObject.getString("merSignMsg"));
			appId = jsonObject.getString("appId");
			transType = jsonObject.getString("transType");

			
			System.out.println("Janine:payGateway.传输的真实数据.解析--->" 
					+ "1.interfaceName:" + interfaceName
					+ ";2.interfaceVersion:" + interfaceVersion
					+ ";3.tranData:" + tranData
					+ ";4.merSignMsg:" + merSignMsg
					+ ";6.appId:" + appId
					+ ";7.transType:" + transType);
			
			url = Config.payGatewayUrl;
			String paywayResult = "";
			try {

				String tranStr = URLEncoder.encode(tranData, "UTF-8");
				String merStr = URLEncoder.encode(merSignMsg, "UTF-8");

				param = "interfaceName=" + interfaceName + "&interfaceVersion=" + interfaceVersion + "&tranData="
						+ tranStr + "&merSignMsg=" + merStr + "&appId=" + appId + "&transType=" + transType
						+ "&_json_att=";
				
				RspHttp rspHttp = HttpUtil.doPost(url, param, "");

				result = rspHttp.getResult();
				System.out.println("Janine:payGateway.请求返回来的数据-是网页吗？--->" + result );

				List<String> nameList = match(result, "input", "name");
				List<String> valueList = match(result, "input", "value");

				JSONObject jsonResult = new JSONObject();
				String nameStr = "channelId,merCustomIp,orderTimeoutDate";
				for (int i = 0; i < nameList.size(); i++) {
					if (nameStr.indexOf(nameList.get(i)) > -1) {
						jsonResult.put(nameList.get(i), valueList.get(i));
					}
				}
				paywayResult = jsonResult.toString();
			} catch (Exception e) {
				paywayResult = "";
			}

			JSONObject jsonResult = JSONObject.fromObject(paywayResult);
			channelId = jsonResult.getString("channelId");
			merCustomIp = jsonResult.getString("merCustomIp");
			orderTimeoutDate = jsonResult.getString("orderTimeoutDate");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		JSONObject data = new JSONObject();
		data.put("tranData", tranData);
		data.put("merSignMsg", merSignMsg);
		data.put("appId", appId);
		data.put("transType", transType);
		data.put("channelId", channelId);
		data.put("merCustomIp", merCustomIp);
		data.put("orderTimeoutDate", orderTimeoutDate);

		RspHttp rs = HttpUtil.doPost(Config.checkUserUrl, "_json_att=", cookie);
		System.out.println("***payGateway ticket checkUser " + rs.getResult());

		System.out.println("Janine:payGateway.data.toString() " + data.toString());
		return data.toString();
	}

	/**
	 * 获取指定HTML标签
	 * 
	 * @param source
	 *            要匹配的源文本
	 * @param element
	 *            标签名称
	 * @param attr
	 *            标签的属性名称
	 * @return 标签
	 */
	public static List<String> match(String source, String element, String attr) {
		List<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = m.group(1);
			result.add(r);
		}
		return result;
	}

	/**
	 * 提交支付方式
	 */
	@ResponseBody
	@RequestMapping(value = "/webBusiness", method = RequestMethod.POST)
	public String webBusiness(HttpServletRequest request) {
		String tranData = WebUtil.getString(request, "tranData", "");
		String merSignMsg = WebUtil.getString(request, "merSignMsg", "");
		String appId = WebUtil.getString(request, "appId", "");
		String transType = WebUtil.getString(request, "transType", "");
		String channelId = WebUtil.getString(request, "channelId", "");
		String merCustomIp = WebUtil.getString(request, "merCustomIp", "");
		String orderTimeoutDate = WebUtil.getString(request, "orderTimeoutDate", "");
		String bankId = WebUtil.getString(request, "bankId", "");
		String amount = WebUtil.getString(request, "amount", "");
		String ok = WebUtil.getString(request, "ok", "");
		
		System.out.println("Janine:webBusiness.接收到的数据--->" 
				+ "tranData:" + tranData
				+ ";merSignMsg:" + merSignMsg
				+ ";appId:" + appId
				+ ";transType:" + transType
				+ ";channelId:" + channelId
				+ ";orderTimeoutDate:" + orderTimeoutDate
				);		

		String formData = "";

		// http请求后，将参数中的“+”全部变成了空格，原因是URL中默认的将“+”号转义了。故这儿要将空格替换回来
		tranData = tranData.replaceAll(" ", "+");
		merSignMsg = merSignMsg.replaceAll(" ", "+");

		String url = Config.webBusinessUrl;
		String param = "tranData=" + EncoderUtil.encode(tranData) + "&merSignMsg=" + EncoderUtil.encode(merSignMsg)
				+ "&appId=" + appId + "&transType=" + transType + "&channelId=" + channelId + "&merCustomIp="
				+ merCustomIp + "&orderTimeoutDate=" + orderTimeoutDate + "&bankId=" + bankId
				+ "&amount=" + amount + "&ok=" + ok;
	
		RspHttp rspHttp = HttpUtil.doPost(url, param, "");

		String result = rspHttp.getResult();

		if (!StringUtil.isNullOrEmpty(result)) {
			if (result.indexOf("<form") > -1) {
				formData = result.substring(result.indexOf("<form"), result.indexOf("</form>") + 7);
			}
		}
		System.out.println("Janine:webBusiness.formData--->" + formData);
		return formData;
	}

	@ResponseBody
	@RequestMapping(value = "/continuePay", method = RequestMethod.POST)
	public String continuePay(HttpServletRequest request) {
		String sequence_no = WebUtil.getString(request, "sequence_no", "");
		String pay_flag = WebUtil.getString(request, "pay_flag", "");
		String appkey = WebUtil.getString(request, "appkey", "");

		System.out.println("Janine:continuePay--->" + "sequence_no:" + sequence_no + ";pay_flag:" + pay_flag);

		String cookie = appkeyService.findCookies(appkey);

		String url = Config.continuePayUrl;
		String param = "sequence_no=" + sequence_no + "&pay_flag=" + pay_flag + "&_json_att=";

		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);

		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);

		System.out.println("Janine:continuePay().rspHttp.getResult()--->" + rspHttp.getResult());
		return rspHttp.getResult();
	}

	/**
	 * 继续支付
	 */
	@ResponseBody
	@RequestMapping(value = "/continuePayInit", method = RequestMethod.GET)
	public String continuePayInit(HttpServletRequest request) {

		String appkey = WebUtil.getString(request, "appkey", "");

		String cookie = appkeyService.findCookies(appkey);

		String url = Config.payOrderInitUrl;
		String param = "_json_att";

		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);

		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);

		String result = rspHttp.getResult();
		System.out.println("Janine:continuePayInit().rspHttp.getResult()--->" + rspHttp.getResult());
		String ticketForm = "";
		int indexOfTicket = result.indexOf("ticketTitleForm");
		if (indexOfTicket > -1) {
			String ticketTitleForm = result.substring(indexOfTicket);
			ticketForm = ticketTitleForm.substring(ticketTitleForm.indexOf("{"), ticketTitleForm.indexOf("}") + 1);
		}

		String passangerForm = "";
		int indexOfPassanger = result.indexOf("passangerTicketList");
		if (indexOfPassanger > -1) {
			String passangerTicketList = result.substring(indexOfPassanger);
			passangerForm = passangerTicketList.substring(passangerTicketList.indexOf("["),
					passangerTicketList.indexOf("]") + 1);
		}

		// Janine:添加parOrderDTOJson的解析
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
		String oldTicketDTOJson = "";
		int index_4 = result.indexOf("var oldTicketDTOJson");
		if (index_4 > -1) {
			String temp = result.substring(index_4);
			oldTicketDTOJson = temp.substring(temp.indexOf("["), temp.indexOf("]") + 1);
		}
		System.out.println("本地服务器:APITicket.continuePayInit.oldTicketDTOJson.完整数据--->" + oldTicketDTOJson);

		// Janine:添加batch_no的解析
		String batch_no = "";
		int index_3 = result.indexOf("var batch_no");
		if(index_3 > -1) {
			String temp = result.substring(index_3);
			temp = temp.substring(temp.indexOf("\'") + 1);
			batch_no = temp.substring(0, temp.indexOf("\'"));
		}
		
	
		// Janine:这个值可以是null
		String orderRequestDTOJson = "";
		String reg_3 = "var orderRequestDTOJson = '(.*?)'";
		Pattern pattern_3 = Pattern.compile(reg_3);
		Matcher matcher_3 = pattern_3.matcher(result);
		while (matcher_3.find()) {
			orderRequestDTOJson = matcher_3.group(1);
		}
		System.out.println("Janine:APITicket.continuePayInit.orderRequestDTOJson--->" + orderRequestDTOJson);

		String tour_flag = "";
		String reg_1 = "var tour_flag = '(.*?)'";
		Pattern pattern_1 = Pattern.compile(reg_1);
		Matcher matcher_1 = pattern_1.matcher(result);
		while (matcher_1.find()) {
			tour_flag = matcher_1.group(1);
		}

		String sequence_no = "";
		String reg_2 = "var sequence_no = '(.*?)'";
		Pattern pattern_2 = Pattern.compile(reg_2);
		Matcher matcher_2 = pattern_2.matcher(result);
		while (matcher_2.find()) {
			sequence_no = matcher_2.group(1);
		}
		String fcTicketForm = "";
		String fcPassangerForm = "";

		if ("fc".equals(tour_flag)) {
			int indexOfFcTicket = result.indexOf("fcTicketTitleForm");
			if (indexOfFcTicket > -1) {
				String ticketTitleForm = result.substring(indexOfFcTicket);
				fcTicketForm = ticketTitleForm.substring(ticketTitleForm.indexOf("{"),
						ticketTitleForm.indexOf("}") + 1);
			}

			int indexOfFcPassanger = result.indexOf("fcPassangerTicketList");
			if (indexOfFcPassanger > -1) {
				String passangerTicketList = result.substring(indexOfFcPassanger);
				fcPassangerForm = passangerTicketList.substring(passangerTicketList.indexOf("["),
						passangerTicketList.indexOf("]") + 1);
			}
		}

		JSONObject jsonResult = new JSONObject();
		jsonResult.put("tour_flag", tour_flag);
		jsonResult.put("sequence_no", sequence_no);
		jsonResult.put("ticketForm", ticketForm);
		jsonResult.put("passangerForm", passangerForm);
		jsonResult.put("fcTicketForm", fcTicketForm);
		jsonResult.put("fcPassangerForm", fcPassangerForm);
		jsonResult.put("parOrderDTOJson", parOrderDTOJson);
		jsonResult.put("oldTicketDTOJson", oldTicketDTOJson);
		jsonResult.put("orderRequestDTOJson", orderRequestDTOJson);
		jsonResult.put("batch_no", batch_no);

		System.out.println("Janine:continuePayInit().jsonResult.toString()--->" + jsonResult.toString());
		return jsonResult.toString();
	}

}
