package com.cn.api.mvc.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TrainCodeService;
import com.cn.api.module.service.TrainNoService;
import com.cn.config.Config;
import com.cn.util.DoUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 出行向导
 */
@Controller
@RequestMapping("/")
public class APIGuideController {

	@Autowired
	AppkeyService appkeyService;
	
	@Autowired
	TrainCodeService  trainCodeService;
	
	@Autowired
	TrainNoService trainNoService;
	
	
	
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
	 * 代售点查询
	 * 
	 * @param 		province（省，如：安徽）
	 * @param 		city（市，如：合肥）
	 * @param 		county（区/县，如：蜀山区）
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryAgency", method=RequestMethod.POST)
	public String queryAgency(HttpServletRequest request) throws UnsupportedEncodingException {
		String province = WebUtil.getString(request, "province", "");
		String city = WebUtil.getString(request, "city", "");
		String county = WebUtil.getString(request, "county", "");
		
		String url = Config.queryAgencyUrl;
		String param = "province=" + EncoderUtil.encode(province) + 
				"&city=" + EncoderUtil.encode(city) + 
				"&county=" + EncoderUtil.encode(county);
		
		return DoUtil.makeGet(url, param, "");
	}
	
	/**
	 * 省
	 */
	@ResponseBody
	@RequestMapping(value="/queryProvince", method=RequestMethod.POST)
	public String queryProvince(HttpServletRequest request) {
		String url = Config.provinceUrl;
	//	System.out.println("于磊磊:queryCity.province--->"+DoUtil.makeGet(url, "", ""));
		return DoUtil.makeGet(url, "", "");
	}
	
	/**
	 * 市
	 * 
	 * @param 		province（省，如：安徽）
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/queryCity", method=RequestMethod.POST)
	public String queryCity(HttpServletRequest request) {
		String province = WebUtil.getString(request, "province", "");
		//System.out.println("于磊磊:queryCity.province--->"+province);
		String url = Config.cityUrl;
		String param = "province=" + EncoderUtil.encode(province);
		//System.out.println("于磊磊:queryCity--->"+DoUtil.makeGet(url, param, ""));
		return DoUtil.makeGet(url, param, "");
	}
	
	/**
	 * 区/县
	 * 
	 * @param 		province（省，如：安徽）
	 * @param 		city（市，如：合肥）
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="/queryCounty", method=RequestMethod.POST)
	public String queryCounty(HttpServletRequest request) {
		String province = WebUtil.getString(request, "province", "");
		String city = WebUtil.getString(request, "city", "");
		
		//System.out.println("于磊磊:queryCounty.province.city--->"+province+","+city);
		
		String url = Config.countyUrl;
		String param = "province=" + EncoderUtil.encode(province) + "&city=" + EncoderUtil.encode(city);
		
		//System.out.println("于磊磊:queryCounty--->"+DoUtil.makeGet(url, param, ""));
		return DoUtil.makeGet(url, param, "");
	}
	
	/**
	 * 检测验证码（出行向导里）
	 * 
	 * @param 		randCode（验证码）
	 * @param		rand（传值：sjrand）
	 * 
	 * @return		12306返回的json
	 */
	@ResponseBody
	@RequestMapping(value = "/checkForGuide", method = RequestMethod.POST)  
	public String checkForGuide(HttpServletRequest request) {  
		String randCode = WebUtil.getString(request, "randCode", "");
		String rand = WebUtil.getString(request, "rand", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.chkPassCodeUrl;
		String param = "randCode=" + randCode + "&rand=" + rand;
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		return rspHttp.getResult();
	}  
	
	/**
	 * 正晚点查询
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/zwdcx", method=RequestMethod.POST)
	public String zwdcx(HttpServletRequest request) throws UnsupportedEncodingException {
		String cc = WebUtil.getString(request, "cc", "");
		String cxlx = WebUtil.getString(request, "cxlx", "");
		String cz = WebUtil.getString(request, "cz", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String appkey = WebUtil.getString(request, "appkey", "");
//		String cc = new  String(WebUtil.getString(request, "cc", "").getBytes("ISO8859-1"),"utf-8");
//		String cz = new  String(WebUtil.getString(request, "cz", "").getBytes("ISO8859-1"),"utf-8");
		String cookie = appkeyService.findCookies(appkey);
		
		String station = EncoderUtil.encode(cz);
		String czEn = station.replaceAll("%", "-");
		System.out.println("cc"+cc);
		System.out.println("cxlx"+cxlx);
		System.out.println("cz"+cz);
		System.out.println("station"+station);
		System.out.println("czEn"+czEn);
		System.out.println("randCode"+randCode);
		
		String url = Config.zwdcxUrl;
		String param = "cxlx=" + cxlx + "&cz=" + station + "&cc=" + cc + 
				"&czEn=" + czEn + "&randCode=" + randCode;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		return rspHttp.getResult();
		
	}
	
	/**
	 * 根据车站查询车次列表
	 */
	@ResponseBody
	@RequestMapping(value="/queryCC", method=RequestMethod.GET)
	public String queryCC(HttpServletRequest request) {
		String train_station_code = WebUtil.getString(request, "train_station_code", "");
		
		String url = Config.queryCCUrl;
		String param = "train_station_code=" + train_station_code;
		
		return DoUtil.makePost(url, param, "");
	}
	
	/**
	 * 中转查询
	 * 
	 * @param  		queryDate(乘车日期)
	 * @param  		from_station(出发地代码)
	 * @param  		to_station(目的地代码)
	 * @param  		from_station_name(出发地名称)
	 * @param  		to_station_name(目的地名称)
	 * @param  		randCode(验证码)
	 * @param  		changeStationText (中转站代码)
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/transferQuery", method=RequestMethod.POST)
	public String transferQuery(HttpServletRequest request) throws UnsupportedEncodingException {
System.out.println("进入服务器--->");
		
		String queryDate = WebUtil.getString(request, "queryDate", "");
		
//		String from_station = WebUtil.getString(request, "from_station", "");
//		String to_station = WebUtil.getString(request, "to_station", "");
		
		String from_station_name = WebUtil.getString(request, "from_station_name", "");
		String to_station_name = WebUtil.getString(request, "to_station_name", "");
//		
//		String from_station_name = new  String(WebUtil.getString(request, "from_station_name", "").getBytes("ISO8859-1"),"utf-8");
//		String to_station_name = new  String(WebUtil.getString(request, "to_station_name", "").getBytes("ISO8859-1"),"utf-8");
		
		String randCode = WebUtil.getString(request, "randCode", "");
		String changeStationText = WebUtil.getString(request, "changeStationText", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String from_station_code =trainCodeService.findTrain(from_station_name);
		String to_station_code =trainCodeService.findTrain(to_station_name);
		String changeStationText_code =trainCodeService.findTrain(changeStationText);
		
		System.out.println("于磊磊##:APITicket.transferQuery.接收到的数据----->" 
				+"queryDate:"+queryDate
				+",from_station_name:"+from_station_name+from_station_code
				+",to_station_name:"+to_station_name+to_station_code
				+",changeStationText:"+changeStationText+changeStationText_code
				+",randCode:"+randCode);
		
		String cookie = appkeyService.findCookies(appkey);
		
		
		String url = Config.zzzcxUrl;
		String param = "queryDate=" + queryDate
						+ "&from_station=" + from_station_code 
						+ "&to_station=" + to_station_code
						+"&randCode=" + randCode
						+"&changeStationText=" + changeStationText_code;
		
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		System.out.println("于磊磊:APITicket.transferQuery.rspHttp.getResult()--->"+rspHttp.getResult());
		return rspHttp.getResult();
	}
	
	/** 
	 * 车站车次查询
	 * 
	 * @param  		train_start_date(日期)
	 * @param  		train_station_name(车站名称)
	 * @param  		train_station_code(车站代码)
	 * @param  		randCode(验证码)
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/trainQuery", method=RequestMethod.POST)
	public String trainQuery(HttpServletRequest request) throws UnsupportedEncodingException {
		
		System.out.println("于磊磊:APITicket.trainQuery.进入--->");
		
		String train_start_date = WebUtil.getString(request, "train_start_date", "");
		String train_station_name = WebUtil.getString(request, "train_station_name", "");
//		String train_station_code = WebUtil.getString(request, "train_station_code", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		
		System.out.println("于磊磊:trainQuery.接收到的数据:"
				+"train_start_date:"+train_start_date
				+",train_station_name:"+train_station_name
				+",randCode:"+randCode
				);
		
		
		String cookie = appkeyService.findCookies(appkey);
		
//		String from_station_code =trainCodeService.findTrain(train_station_code);
		String from_station_code =trainCodeService.findTrain(train_station_name);
		
		String url = Config.czxxUrl;
		String param = "train_start_date=" + train_start_date + "&train_station_name=" + EncoderUtil.encode(train_station_name) + 
				"&train_station_code="+from_station_code+"&randCode=" + randCode;
		
		System.out.println("于磊磊:param--->"+param);
		
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		System.out.println("于磊磊:trainQuery.rspHttp.getResult()--->"+rspHttp.getResult());
		return rspHttp.getResult();
	}
	
	/**
	 * 票价查询
	 * 
	 * @param  		leftTicketDTO.train_date(乘车日期)
	 * @param  		leftTicketDTO.from_station(出发地代码)
	 * @param  		leftTicketDTO.to_station(目的地代码)
	 * @param 		ticket_type(1：成人票，2：儿童票，3：学生票，4：残军票）
	 * @param  		randCode(验证码)
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/ticketPriceQuery", method=RequestMethod.GET)
	public String ticketPriceQuery(HttpServletRequest request) throws UnsupportedEncodingException {
		String train_date = WebUtil.getString(request, "train_date", "");
		
//		String from_station = new  String(WebUtil.getString(request, "from_station", "").getBytes("ISO8859-1"),"utf-8");
//		String to_station = new  String(WebUtil.getString(request, "to_station", "").getBytes("ISO8859-1"),"utf-8");
		
		String ticket_type = WebUtil.getString(request, "ticket_type", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String from_station = WebUtil.getString(request, "from_station", "");
		String to_station = WebUtil.getString(request, "to_station", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		System.out.println("于磊磊:APITicket.ticketPriceQuery.接收出来的信息--->"
				+",train_date:"+train_date
				+",from_station:"+from_station
				+",to_station:"+to_station
				+",ticket_type:"+ticket_type
				+",randCode:"+randCode);
		
		String from_station_code =trainCodeService.findTrain(from_station);
		String to_station_code =trainCodeService.findTrain(to_station);
		
		
		String url = Config.queryPriceUrl;
		String param = "leftTicketDTO.train_date=" + train_date + "&leftTicketDTO.from_station=" + from_station_code + 
						"&leftTicketDTO.to_station=" + to_station_code + "&ticket_type" + ticket_type + "&randCode=" + randCode;
	
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		System.out.println("于磊磊:APITicket.ticketPriceQuery.rspHttp.getResult()--->"+rspHttp.getResult());
		return rspHttp.getResult();
	}
	
	/**
	 * 车次查询
	 * 
	 * @param  		leftTicketDTO.train_date(乘车日期)
	 * @param  		leftTicketDTO.train_no(车次)
	 * @param  		rand_code(验证码)
	 */
	@ResponseBody
	@RequestMapping(value="/queryTrainInfo", method=RequestMethod.GET)
	public String queryTrainInfo(HttpServletRequest request) {
		String train_no = WebUtil.getString(request, "train_no", "");
		String train_date = WebUtil.getString(request, "train_date", "");
		String randCode = WebUtil.getString(request, "randCode", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		System.out.println("于磊磊:queryTrainInfo.接收到的数据--->"
				+"train_no"+train_no
				+"train_date"+train_date
				+"randCode"+randCode);
		System.out.println("train_no------>"+"1111111111");
		
		System.out.println("train_no"+train_no);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String train_nu = trainNoService.findTrain(train_no);
		System.out.println("train_nu****"+train_nu);     //于磊磊
		
		String url = Config.queryTrainUrl;
		
		//于磊磊
		String param = "leftTicketDTO.train_no=" + train_nu + "&leftTicketDTO.train_date=" + train_date + "&rand_code=" + randCode;
		System.out.println("于磊磊:queryTrainInfo.param--->"+param);
		RspHttp rspHttp = HttpUtil.doGet(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		System.out.println("于磊磊:queryTrainInfo.rspHttp.getResult()--->"+rspHttp.getResult());
		return rspHttp.getResult();
	}
	
}
