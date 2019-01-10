package com.cn.api.mvc.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.service.TrainCodeService;
import com.cn.config.Config;
import com.cn.util.DoUtil;
import com.cn.util.StringUtil;
import com.cn.util.WebUtil;

/**
 * 余票查询
 */
@Controller
@RequestMapping("/")
public class APITicketLeftController {
	@Autowired
	TrainCodeService  trainCodeService;
	
	public TrainCodeService getTrainCodeService() {
		return trainCodeService;
	}

	public void setTrainCodeService(TrainCodeService trainCodeService) {
		this.trainCodeService = trainCodeService;
	}
	/**
	 * 余票查询
	 * 
	 * @param 		purpose_codes（普通：ADULT，学生：0X00）
	 * @param 		queryDate（出发日期）
	 * @param 		from_station（出发地代码）
	 * @param 		to_station（目的地代码）
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/queryTicket", method=RequestMethod.GET)
	public String queryTicket(HttpServletRequest request) throws UnsupportedEncodingException {
		String purpose_codes = WebUtil.getString(request, "purpose_codes", "");
		String queryDate = WebUtil.getString(request, "queryDate", "");
		
//		String from_station = new  String(WebUtil.getString(request, "from_station", "").getBytes("ISO8859-1"),"utf-8");
//		String to_station = new  String(WebUtil.getString(request, "to_station", "").getBytes("ISO8859-1"),"utf-8");
		
		String from_station = WebUtil.getString(request, "from_station", "");
		String to_station = WebUtil.getString(request, "to_station", "");
		
		String from_station_code =trainCodeService.findTrain(from_station);
		String to_station_code =trainCodeService.findTrain(to_station);
		
		
		
		System.out.println("Janine:APITicket.queryTicket.接收到的数据:"
				+"purpose_codes:"+purpose_codes
				+",queryDate:"+queryDate
				+",from_station:"+from_station
				+",to_station:"+to_station
				+",from_station_code:"+from_station_code
				+",to_station_code:"+to_station_code
				);
		
		
		
		String url = Config.queryTicketUrl;
		String param = "purpose_codes=" + purpose_codes + "&queryDate=" + queryDate + 
						"&from_station=" + from_station_code + "&to_station=" + to_station_code;
		
		String result = DoUtil.makeGet(url, param, "");
		RspData rspData = new RspData();
		if(!StringUtil.isNullOrEmpty(result)) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(result);
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		
		System.out.println("Janine:APITicket.queryTicket.rspData.toJsonStr():"+rspData.toJsonStr());
		return rspData.toJsonStr();
	}
	
	/**
	 * 车次查询
	 * 
	 * @param 		train_no（车次）
	 * @param 		from_station_telecode（出发地代码）
	 * @param 		to_station_telecode（目的地代码）
	 * @param 		depart_date（开车日期）
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/queryByTrainNo",method=RequestMethod.GET)
	public String queryByTrainNo(HttpServletRequest request) {
		String train_no = WebUtil.getString(request, "train_no", "");
		String from_station_telecode = WebUtil.getString(request, "from_station_telecode", "");
		String to_station_telecode = WebUtil.getString(request, "to_station_telecode", "");
		String depart_date = WebUtil.getString(request, "depart_date", "");
		
		String url = Config.queryByTrainNoUrl;
		String param = "train_no=" + train_no + "&from_station_telecode=" + from_station_telecode + 
						"&to_station_telecode=" + to_station_telecode + "&depart_date=" + depart_date;
		System.out.println("停靠的车次:+++"+param);
		String result =  DoUtil.makeGet(url, param, "");
		System.out.println("停靠的车次:+++"+result);
		RspData rspData = new RspData();
		if(!StringUtil.isNullOrEmpty(result)) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(result);
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		return rspData.toJsonStr();
	}
	
	/**
	 * 票价查询（查询单个车次的）
	 * 
	 * @param 		train_no（车次）
	 * @param 		from_station_no（出发地代码）
	 * @param 		to_station_no（目的地代码）
	 * @param 		seat_types（）
	 * @param 		train_date（开车日期）
	 * 
	 * @return		json字符串
	 */
	@ResponseBody
	@RequestMapping(value="/queryTicketPrice",method=RequestMethod.GET)
	public String queryTicketPrice(HttpServletRequest request) {
		String train_no = WebUtil.getString(request, "train_no", "");
		String from_station_no = WebUtil.getString(request, "from_station_no", "");
		String to_station_no = WebUtil.getString(request, "to_station_no", "");
		String seat_types = WebUtil.getString(request, "seat_types", "");
		String train_date = WebUtil.getString(request, "train_date", "");
		
		String url = Config.queryTicketPriceUrl;
		String param = "train_no=" + train_no + "&from_station_no=" + from_station_no + 
						"&to_station_no=" + to_station_no + "&seat_types=" + seat_types + "&train_date=" + train_date;
		String result =  DoUtil.makeGet(url, param, "");
		RspData rspData = new RspData();
		if(!StringUtil.isNullOrEmpty(result)) {
			rspData.setFlag(true);
			rspData.setMsg("");
			rspData.setResult(result);
		} else {
			rspData.setFlag(false);
			rspData.setMsg("失败");
			rspData.setResult("");
		}
		return rspData.toJsonStr();
	}

}
