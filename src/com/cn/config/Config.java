package com.cn.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.PropertyResourceBundle;

public class Config {

	public static String captchaPath; // 验证码存储在本地服务器的路径
	public static String captchaUrl; // 验证码本地服务器访问的url
	public static String  UnreImagePath;//验证码未被识别存储在服务器的路径
	
	public static String python_path; 
	public static String resutl_txt_path; 
	
	public static String python_path_buyticket; 
	public static String getPassCodeUrl; //获取验证码
	public static String chkPassCodeUrl; //检测验证码
	public static String checkUserUrl; // 检查用户是否登录
	public static String loginUserUrl; // 登录
	public static String loginOutUrl; // 退出
	
	public static String queryTicketUrl; // 余票查询
	public static String queryByTrainNoUrl; // 车次查询
	public static String queryTicketPriceUrl; // 票价查询（查询单个车次的）

	public static String queryAgencyUrl; // 代售点查询
	public static String provinceUrl; // 省
	public static String cityUrl; // 市
	public static String countyUrl; // 区/县
	public static String zwdcxUrl; // 正晚点查询
	public static String queryCCUrl; // 根据车站查询车次列表
	public static String zzzcxUrl; // 中转查询
	public static String czxxUrl; // 车站车次查询
	public static String queryPriceUrl; // 票价查询
	public static String queryTrainUrl; // 车次查询
	
	public static String queryMyOrderNoCompleteUrl; // 未完成订单
	public static String queryMyOrderUrl; // 已完成订单
	
	public static String quertUserInfoUrl; // 查看个人信息
	public static String editUserInfoUrl; // 修改个人信息
	
	public static String queryschoolNamesUrl; // 查看联系人	
	public static String queryallCitysUrl;
	
	public static String queryContactsUrl; // 查看联系人	
	
	public static String addContactsUrl; // 添加联系人
	public static String deleteContactsUrl; // 删除联系人
	public static String editContactsUrl; // 修改联系人
	public static String showContactsUrl;
	
	
	public static String queryLeftTicketUrl; //余票查询（车票预订）
	public static String submitOrderRequestUrl; // 点击“预订”检查车票信息
	public static String initDcUrl; // 初始化订单页面（单程）
	public static String initWcUrl; // 初始化订单页面（往返）
	public static String initFcUrl; // 初始化订单页面（返程）
	public static String initGcUrl; // 初始化订单页面（改签）
	public static String getPassengerUrl; // 获取乘客信息
	public static String checkOrderInfoUrl; // 检测是否可以确认提交
	public static String getQueueCountUrl; // 查看订单排队信息
	public static String confirmDcQueueUrl; // 确认提交
	public static String queryOrderTimeUrl; // 查询出票等待时间 
	public static String resultOrderDcUrl; // 查看订单结果
	public static String cancelQueueOrderUrl; // 取消排队订单（未出票，订单排队中）
	public static String cancelOrderUrl; // 取消订单（已出票，但未付款）
	public static String continuePayUrl; // 继续支付
	public static String payOrderInitUrl; // 初始化支付页面
	
	public static String paycheckNewUrl; // 订单支付检查
	public static String payGatewayUrl; 
	public static String webBusinessUrl; 
	
	public static String returnTicketAffirmUrl; // 是否确定退票
	public static String returnTicketUrl; //退票
	
	public static String resginTicketUrl; // 改签
	public static String confirmGcQueueUrl;
	public static String resultOrderGcUrl;
	public static String payConfirmNUrl;
	public static String payConfirmTUrl;
	public static String payfinishUrl;
	public static String cancelResignUrl;
	
	public static String confirmWcQueueUrl;
	public static String resultOrderWcUrl;
	
	public static String leftTicketInitUrl;
	public static String confirmFcQueueUrl;
	public static String resultOrderFcUrl;
	
	public static String JuheTrainQueryUrl;
	
	public static HashMap<String, String> constants = new HashMap<String, String>(); // 常量文件
	public static String cancelUrl;
	static {
		constants = loadDataFromFile("/config.properties");
		captchaPath = (String) constants.get("captchaPath");
		captchaUrl = (String) constants.get("captchaUrl");
		UnreImagePath = (String) constants.get("UnreImagePath");
		cancelUrl = (String) constants.get("cancelUrl");
		python_path_buyticket = (String) constants.get("python_path_buyticket");
		python_path = (String) constants.get("python_path");
		resutl_txt_path = (String) constants.get("resutl_txt_path");
		
		getPassCodeUrl = (String) constants.get("getPassCodeUrl");
		chkPassCodeUrl = (String) constants.get("chkPassCodeUrl");
		checkUserUrl = (String) constants.get("checkUserUrl");
		loginUserUrl = (String) constants.get("loginUserUrl");
		loginOutUrl = (String) constants.get("loginOutUrl");
		
		queryTicketUrl = (String) constants.get("queryTicketUrl");
		queryByTrainNoUrl = (String) constants.get("queryByTrainNoUrl");
		queryTicketPriceUrl = (String) constants.get("queryTicketPriceUrl");
		
		queryAgencyUrl = (String) constants.get("queryAgencyUrl");
		provinceUrl = (String) constants.get("provinceUrl");
		cityUrl = (String) constants.get("cityUrl");
		countyUrl = (String) constants.get("countyUrl");
		zwdcxUrl = (String) constants.get("zwdcxUrl");
		queryCCUrl = (String) constants.get("queryCCUrl");
		zzzcxUrl = (String) constants.get("zzzcxUrl");
		czxxUrl = (String) constants.get("czxxUrl");
		queryPriceUrl = (String) constants.get("queryPriceUrl");
		queryTrainUrl = (String) constants.get("queryTrainUrl");
		
		queryMyOrderNoCompleteUrl = (String) constants.get("queryMyOrderNoCompleteUrl");
		queryMyOrderUrl = (String) constants.get("queryMyOrderUrl");
		
		quertUserInfoUrl = (String) constants.get("quertUserInfoUrl");
		editUserInfoUrl = (String) constants.get("editUserInfoUrl");
		queryschoolNamesUrl = (String) constants.get("queryschoolNamesUrl");
		queryallCitysUrl = (String) constants.get("queryallCitysUrl");
		queryContactsUrl = (String) constants.get("queryContactsUrl");
		addContactsUrl = (String) constants.get("addContactsUrl");
		deleteContactsUrl = (String) constants.get("deleteContactsUrl"); 
		showContactsUrl = (String) constants.get("showContactsUrl");
		editContactsUrl = (String) constants.get("editContactsUrl");
		
		queryLeftTicketUrl = (String) constants.get("queryLeftTicketUrl");
		submitOrderRequestUrl = (String) constants.get("submitOrderRequestUrl");
		initDcUrl = (String) constants.get("initDcUrl");
		initWcUrl = (String) constants.get("initWcUrl");
		initFcUrl = (String) constants.get("initFcUrl");
		initGcUrl = (String) constants.get("initGcUrl");
		getPassengerUrl = (String) constants.get("getPassengerUrl");
		checkOrderInfoUrl = (String) constants.get("checkOrderInfoUrl"); 
		getQueueCountUrl = (String) constants.get("getQueueCountUrl"); 
		confirmDcQueueUrl = (String) constants.get("confirmDcQueueUrl"); 
		queryOrderTimeUrl = (String) constants.get("queryOrderTimeUrl"); 
		resultOrderDcUrl = (String) constants.get("resultOrderDcUrl"); 
		cancelQueueOrderUrl = (String) constants.get("cancelQueueOrderUrl"); 
		cancelOrderUrl = (String) constants.get("cancelOrderUrl"); 
		
		continuePayUrl = (String) constants.get("continuePayUrl"); 
		payOrderInitUrl = (String) constants.get("payOrderInitUrl"); 
		paycheckNewUrl = (String) constants.get("paycheckNewUrl"); 
		payGatewayUrl = (String) constants.get("payGatewayUrl"); 
		webBusinessUrl = (String) constants.get("webBusinessUrl"); 
		
		returnTicketAffirmUrl = (String) constants.get("returnTicketAffirmUrl"); 
		returnTicketUrl = (String) constants.get("returnTicketUrl"); 
		
		resginTicketUrl = (String) constants.get("resginTicketUrl"); 
		confirmGcQueueUrl = (String) constants.get("confirmGcQueueUrl"); 
		resultOrderGcUrl = (String) constants.get("resultOrderGcUrl"); 
		payConfirmNUrl = (String) constants.get("payConfirmNUrl"); 
		payConfirmTUrl = (String) constants.get("payConfirmTUrl"); 
		payfinishUrl = (String) constants.get("payfinishUrl"); 
		cancelResignUrl = (String) constants.get("cancelResignUrl"); 
		
		confirmWcQueueUrl = (String) constants.get("confirmWcQueueUrl"); 
		resultOrderWcUrl = (String) constants.get("resultOrderWcUrl"); 
		
		leftTicketInitUrl = (String) constants.get("leftTicketInitUrl"); 
		confirmFcQueueUrl = (String) constants.get("confirmFcQueueUrl"); 
		resultOrderFcUrl = (String) constants.get("resultOrderFcUrl"); 
		JuheTrainQueryUrl = (String) constants.get("JuheTrainQueryUrl"); 
	}

	/**
	 * 从属性文件装载数据
	 * 
	 * @param path 		属性文件路径
	 * 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public static HashMap<String, String> loadDataFromFile(String path) {
		HashMap<String, String> hm = new HashMap<String, String>();
		try {
			InputStream is = new Config().getClass().getResourceAsStream(path);
			PropertyResourceBundle prb = new PropertyResourceBundle(is);
			if (prb == null)
				System.out.println("No Source");
			Enumeration e = prb.getKeys();
			while (e.hasMoreElements()) {
				String key = ((String) e.nextElement()).trim();
				String value = ((String) prb.getObject(key)).trim();
				value = new String(value.getBytes("iso-8859-1"), "UTF-8");
				hm.put(key, value);
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return hm;
	}

}
