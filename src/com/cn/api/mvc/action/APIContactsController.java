package com.cn.api.mvc.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.nodes.Element;
import org.apache.commons.httpclient.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.entity.TicketBook;
import com.cn.api.entity.TicketLeft;
import com.cn.api.module.bean.RspData;
import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.SchoolCode;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.SchoolCodeService;
import com.cn.config.Config;
import com.cn.util.DoUtil;
import com.cn.util.EncoderUtil;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 常用联系人
 */
@Controller
@RequestMapping("/")
public class APIContactsController {

	@Autowired
	AppkeyService appkeyService;
	
	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}

	@Autowired
	SchoolCodeService schoolCodeService;
	
	public SchoolCodeService getSchoolCodeService() {
		return schoolCodeService;
	}

	public void setSchoolCodeService(SchoolCodeService schoolCodeService) {
		this.schoolCodeService = schoolCodeService;
	}
	
	/**
	 * 查看联系人
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryContacts", method=RequestMethod.POST)
	public String queryContacts(HttpServletRequest request) throws UnsupportedEncodingException {
		String pageIndex = WebUtil.getString(request, "pageIndex", "");
		String pageSize = WebUtil.getString(request, "pageSize", "");
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.queryContactsUrl;	
		String param = "pageIndex=" + pageIndex + "&pageSize=" + pageSize + 
				"&passengerDTO.passenger_name=" + EncoderUtil.encode(passenger_name);
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		return rspHttp.getResult();
	}

	/**
	 * 查看学校数据
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value="/queryschoolNames", method=RequestMethod.POST)
	public String queryschoolNames(HttpServletRequest request) throws UnsupportedEncodingException {
		
		String appkey = WebUtil.getString(request, "appkey", "");
		String provinceCode = WebUtil.getString(request, "provinceCode", "11");
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.queryschoolNamesUrl;	
		String param = "provinceCode=" + provinceCode;
		String url1 = Config.queryallCitysUrl;	
		String param1 = "station_name=";
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		RspHttp rspHttp1 = HttpUtil.doPost(url1, param1, cookie);
		List<SchoolCode> list = new ArrayList<SchoolCode>();
		List<SchoolCode> list1 = new ArrayList<SchoolCode>();
		String result = rspHttp.getResult();
		String result1 = rspHttp1.getResult();
		
		System.out.println(result);
		System.out.println(result1);
		int id=0;
		int id1=0;
		try {
			JSONObject jsonObject = JSONObject.fromObject(result);
			boolean status = jsonObject.getBoolean("status");
			JSONObject jsonObject1 = JSONObject.fromObject(result1);
			boolean status1 = jsonObject.getBoolean("status");
			if(status && status1) {
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				list = (List<SchoolCode>)JSONArray.toCollection(jsonArray, SchoolCode.class);
				JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
				list1 = (List<SchoolCode>)JSONArray.toCollection(jsonArray1, SchoolCode.class);
					
					for(SchoolCode itme : list) {
						
						schoolCodeService.addItem(itme);
						id++;
					}
					for(SchoolCode itme1 : list1) {
						
							schoolCodeService.addItem_City(itme1);
							id1++;
						}
				
			}
		} catch(Exception e) {
			//list = new ArrayList<Contacts>();
		}
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		System.out.println("Vincent >>>>>id:"+id+"Vincent >>>>>id1:"+id1);
		return rspHttp.getResult()+rspHttp1.getResult();
	}
	/**
	 * 添加联系人
	 * 
	 * @param passenger_name                     
	 * @param sex_code
	 * @param _birthDate
	 * @param country_code
	 * @param passenger_id_type_code
	 * @param passenger_id_no
	 * @param mobile_no
	 * @param phone_no
	 * @param email
	 * @param address
	 * @param postalcode
	 * @param passenger_type
	 * @param studentInfoDTOprovince_code
	 * @param studentInfoDTOschool_code
	 * @param studentInfoDTOschool_name
	 * @param studentInfoDTOdepartment
	 * @param studentInfoDTOschool_class
	 * @param studentInfoDTOstudent_no
	 * @param studentInfoDTOschool_system
	 * @param studentInfoDTOenter_year
	 * @param studentInfoDTOpreference_card_no
	 * @param studentInfoDTOpreference_from_station_name
	 * @param studentInfoDTOpreference_from_station_code
	 * @param studentInfoDTOpreference_to_station_name
	 * @param studentInfoDTOpreference_to_station_code
	 */
	@ResponseBody
	@RequestMapping(value="/addContacts", method=RequestMethod.POST)
	public String addContacts(HttpServletRequest request) {
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String sex_code = WebUtil.getString(request, "sex_code", "");
		String _birthDate = WebUtil.getString(request, "_birthDate", "");
		String country_code = WebUtil.getString(request, "country_code", "");
		String passenger_id_type_code = WebUtil.getString(request, "passenger_id_type_code", "");
		String passenger_id_no = WebUtil.getString(request, "passenger_id_no", "");
		String mobile_no = WebUtil.getString(request, "mobile_no", "");
		String phone_no = WebUtil.getString(request, "phone_no", "");
		String email = WebUtil.getString(request, "email", "");
		String address = WebUtil.getString(request, "address", "");
		String postalcode = WebUtil.getString(request, "postalcode", "");
		String passenger_type = WebUtil.getString(request, "passenger_type", "");
		String studentInfoDTOprovince_code = WebUtil.getString(request, "studentInfoDTOprovince_code", "");
		String studentInfoDTOschool_code = WebUtil.getString(request, "studentInfoDTOschool_code", "");
		String studentInfoDTOschool_name = WebUtil.getString(request, "studentInfoDTOschool_name", "");
		String studentInfoDTOdepartment = WebUtil.getString(request, "studentInfoDTOdepartment", "");
		String studentInfoDTOschool_class = WebUtil.getString(request, "studentInfoDTOschool_class", "");
		String studentInfoDTOstudent_no = WebUtil.getString(request, "studentInfoDTOstudent_no", "");
		String studentInfoDTOschool_system = WebUtil.getString(request, "studentInfoDTOschool_system", "");
		String studentInfoDTOenter_year = WebUtil.getString(request, "studentInfoDTOenter_year", "");
		String studentInfoDTOpreference_card_no = WebUtil.getString(request, "studentInfoDTOpreference_card_no", "");
		String studentInfoDTOpreference_from_station_name = WebUtil.getString(request, "studentInfoDTOpreference_from_station_name", "");
		String studentInfoDTOpreference_from_station_code = WebUtil.getString(request, "studentInfoDTOpreference_from_station_code", "");
		String studentInfoDTOpreference_to_station_name = WebUtil.getString(request, "studentInfoDTOpreference_to_station_name", "");
		String studentInfoDTOpreference_to_station_code = WebUtil.getString(request, "studentInfoDTOpreference_to_station_code", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		System.out.println("studentInfoDTOschool_name"+studentInfoDTOschool_name);
		System.out.println("studentInfoDTOschool_name"+studentInfoDTOstudent_no);
		System.out.println("studentInfoDTOschool_name"+studentInfoDTOpreference_from_station_name);
		studentInfoDTOschool_code=schoolCodeService.findstationTelecode(studentInfoDTOschool_name);
		studentInfoDTOpreference_from_station_code=schoolCodeService.finds_City_tationTelecode(studentInfoDTOpreference_from_station_name);
		studentInfoDTOpreference_to_station_code=schoolCodeService.finds_City_tationTelecode(studentInfoDTOpreference_to_station_name);
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.addContactsUrl;
		String param = "passenger_name=" + EncoderUtil.encode(passenger_name) + "&sex_code=" + sex_code + "&_birthDate=" + _birthDate +
				"&country_code=" + country_code + "&passenger_id_type_code=" + passenger_id_type_code +
				"&passenger_id_no=" + passenger_id_no + "&mobile_no=" + mobile_no + "&phone_no=" + phone_no + 
				"&email=" + email + "&address=" + EncoderUtil.encode(address) + "&postalcode=" + postalcode + "&passenger_type=" + passenger_type +
				"&studentInfoDTO.province_code=" + studentInfoDTOprovince_code + "&studentInfoDTO.school_code=" + studentInfoDTOschool_code +
				"&studentInfoDTO.school_name=" + EncoderUtil.encode(studentInfoDTOschool_name) + "&studentInfoDTO.department=" + EncoderUtil.encode(studentInfoDTOdepartment) +
				"&studentInfoDTO.school_class=" + EncoderUtil.encode(studentInfoDTOschool_class) + "&studentInfoDTO.student_no=" + studentInfoDTOstudent_no +
				"&studentInfoDTO.school_system=" + studentInfoDTOschool_system + "&studentInfoDTO.enter_year=" + studentInfoDTOenter_year +
				"&studentInfoDTO.preference_card_no=" + studentInfoDTOpreference_card_no +
				"&studentInfoDTO.preference_from_station_name=" + EncoderUtil.encode(studentInfoDTOpreference_from_station_name) +
				"&studentInfoDTO.preference_from_station_code=" + studentInfoDTOpreference_from_station_code
				+"&studentInfoDTO.preference_to_station_name=" + EncoderUtil.encode(studentInfoDTOpreference_to_station_name) +
				"&studentInfoDTO.preference_to_station_code=" + studentInfoDTOpreference_to_station_code;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		System.out.println("studentInfoDTOschool_code"+studentInfoDTOschool_code+"studentInfoDTOpreference_from_station_code"+studentInfoDTOpreference_from_station_code+"studentInfoDTOpreference_to_station_code"+studentInfoDTOpreference_to_station_code);
		return rspHttp.getResult();
	}
	
	/**
	 * 修改联系人
	 * 
	 * @param passenger_name                   姓名
	 * @param old_passenger_name               原姓名 
	 * @param sex_code                         性别： M/F
	 * @param _birthDate                       形式：2016-01-01
	 * @param country_code                     中国：CN
	 * @param passenger_id_type_code           二代身份证：1；护照：B
	 * @param old_passenger_id_type_code       
	 * @param passenger_id_no
	 * @param old_passenger_id_no
	 * @param mobile_no
	 * @param phone_no
	 * @param email
	 * @param address
	 * @param postalcode
	 * @param passenger_type
	 * @param studentInfoDTOprovince_code
	 * @param studentInfoDTOschool_code
	 * @param studentInfoDTOschool_name           简码/汉字
	 * @param studentInfoDTOdepartment
	 * @param studentInfoDTOschool_class
	 * @param studentInfoDTOstudent_no
	 * @param studentInfoDTOschool_system
	 * @param studentInfoDTOenter_year
	 * @param studentInfoDTOpreference_card_no
	 * @param studentInfoDTOpreference_from_station_name           简码/汉字
	 * @param studentInfoDTOpreference_from_station_code
	 * @param studentInfoDTOpreference_to_station_name              简码/汉字
	 * @param studentInfoDTOpreference_to_station_code
	 */
	@ResponseBody
	@RequestMapping(value="/editContacts", method=RequestMethod.POST)
	public String editContacts(HttpServletRequest request) {
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String old_passenger_name = WebUtil.getString(request, "old_passenger_name", "");
		String sex_code = WebUtil.getString(request, "sex_code", "");
		String _birthDate = WebUtil.getString(request, "_birthDate", "");
		String country_code = WebUtil.getString(request, "country_code", "");
		String passenger_id_type_code = WebUtil.getString(request, "passenger_id_type_code", "");
		String old_passenger_id_type_code = WebUtil.getString(request, "old_passenger_id_type_code", "");
		String passenger_id_no = WebUtil.getString(request, "passenger_id_no", "");
		String old_passenger_id_no = WebUtil.getString(request, "old_passenger_id_no", "");
		String mobile_no = WebUtil.getString(request, "mobile_no", "");
		String phone_no = WebUtil.getString(request, "phone_no", "");
		String email = WebUtil.getString(request, "email", "");
		String address = WebUtil.getString(request, "address", "");
		String postalcode = WebUtil.getString(request, "postalcode", "");
		String passenger_type = WebUtil.getString(request, "passenger_type", "");
		String studentInfoDTOprovince_code = WebUtil.getString(request, "studentInfoDTOprovince_code", "11");
		String studentInfoDTOschool_code = WebUtil.getString(request, "studentInfoDTOschool_code", "");
		String studentInfoDTOschool_name = WebUtil.getString(request, "studentInfoDTOschool_name", "简码/汉字");
		String studentInfoDTOdepartment = WebUtil.getString(request, "studentInfoDTOdepartment", "");
		String studentInfoDTOschool_class = WebUtil.getString(request, "studentInfoDTOschool_class", "");
		String studentInfoDTOstudent_no = WebUtil.getString(request, "studentInfoDTOstudent_no", "");
		String studentInfoDTOschool_system = WebUtil.getString(request, "studentInfoDTOschool_system", "1");
		String studentInfoDTOenter_year = WebUtil.getString(request, "studentInfoDTOenter_year", "2016");
		String studentInfoDTOpreference_card_no = WebUtil.getString(request, "studentInfoDTOpreference_card_no", "");
		String studentInfoDTOpreference_from_station_name = WebUtil.getString(request, "studentInfoDTOpreference_from_station_name", "简码/汉字");
		String studentInfoDTOpreference_from_station_code = WebUtil.getString(request, "studentInfoDTOpreference_from_station_code", "");
		String studentInfoDTOpreference_to_station_name = WebUtil.getString(request, "studentInfoDTOpreference_to_station_name", "简码/汉字");
		String studentInfoDTOpreference_to_station_code = WebUtil.getString(request, "studentInfoDTOpreference_to_station_code", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		studentInfoDTOschool_code=schoolCodeService.findstationTelecode(studentInfoDTOschool_name);
		studentInfoDTOpreference_from_station_code=schoolCodeService.finds_City_tationTelecode(studentInfoDTOpreference_from_station_name);
		studentInfoDTOpreference_to_station_code=schoolCodeService.finds_City_tationTelecode(studentInfoDTOpreference_to_station_name);
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.editContactsUrl;
		String param = "passenger_name=" + EncoderUtil.encode(passenger_name) + "&old_passenger_name=" + EncoderUtil.encode(old_passenger_name) + "&sex_code=" + sex_code + "&_birthDate=" + _birthDate +
				"&country_code=" + country_code + "&passenger_id_type_code=" + passenger_id_type_code + "&old_passenger_id_type_code=" + old_passenger_id_type_code +
				"&passenger_id_no=" + passenger_id_no + "&old_passenger_id_no=" + old_passenger_id_no + "&mobile_no=" + mobile_no + "&phone_no=" + phone_no + 
				"&email=" + email + "&address=" + EncoderUtil.encode(address) + "&postalcode=" + postalcode + "&passenger_type=" + passenger_type +
				"&studentInfoDTO.province_code=" + studentInfoDTOprovince_code + "&studentInfoDTO.school_code=" + studentInfoDTOschool_code +
				"&studentInfoDTO.school_name=" + EncoderUtil.encode(studentInfoDTOschool_name) +"&studentInfoDTO.department=" + EncoderUtil.encode(studentInfoDTOdepartment) +
				"&studentInfoDTO.school_class=" + EncoderUtil.encode(studentInfoDTOschool_class) +"&studentInfoDTO.student_no=" + studentInfoDTOstudent_no +
				"&studentInfoDTO.school_system=" + studentInfoDTOschool_system + "&studentInfoDTO.enter_year=" + studentInfoDTOenter_year +
				"&studentInfoDTO.preference_card_no=" + studentInfoDTOpreference_card_no +
				"&studentInfoDTO.preference_from_station_name=" + EncoderUtil.encode(studentInfoDTOpreference_from_station_name) +
				"&studentInfoDTO.preference_from_station_code=" + studentInfoDTOpreference_from_station_code
				+"&studentInfoDTO.preference_to_station_name=" + EncoderUtil.encode(studentInfoDTOpreference_to_station_name) +
				"&studentInfoDTO.preference_to_station_code=" + studentInfoDTOpreference_to_station_code;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		System.out.println(mobile_no);
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		System.out.println("studentInfoDTOschool_code"+studentInfoDTOschool_code+"studentInfoDTOpreference_from_station_code"+studentInfoDTOpreference_from_station_code+"studentInfoDTOpreference_to_station_code"+studentInfoDTOpreference_to_station_code);
		
		return rspHttp.getResult();
	}
	
	/**
	 * 删除联系人
	 * 
	 * @param passenger_name           姓名：
	 * @param passenger_id_type_code   二代身份证：1；护照：B
	 * @param passenger_id_no          身份证号：
	 * @param isUserSelf               是否是本人： Y/N
	 */
	@ResponseBody
	@RequestMapping(value="/deleteContacts", method=RequestMethod.POST)
	public String deleteContacts(HttpServletRequest request) {
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String passenger_id_type_code = WebUtil.getString(request, "passenger_id_type_code", "");
		String passenger_id_no = WebUtil.getString(request, "passenger_id_no", "");
		String isUserSelf = WebUtil.getString(request, "isUserSelf", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.deleteContactsUrl;
		String param = "passenger_name=" +EncoderUtil.encode(passenger_name) + "&passenger_id_type_code=" + passenger_id_type_code +
				"&passenger_id_no=" + passenger_id_no +"&isUserSelf=" + isUserSelf;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		return rspHttp.getResult();
	}
	/**
	 * 初始化联系人信息
	 * 
	 * @param passenger_name           姓名：
	 * @param passenger_id_type_code   二代身份证：1；护照：B
	 * @param passenger_id_no          身份证号：
	 * @param passenger_type               
	 */
	@ResponseBody
	@RequestMapping(value="/showContacts", method=RequestMethod.POST)
	public String showContacts(HttpServletRequest request) {
		String passenger_name = WebUtil.getString(request, "passenger_name", "");
		String passenger_id_type_code = WebUtil.getString(request, "passenger_id_type_code", "");
		String passenger_id_no = WebUtil.getString(request, "passenger_id_no", "");
		String passenger_type = WebUtil.getString(request, "passenger_type", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		//String _json_att = WebUtil.getString(request, "_json_att", "");
		//String _json_att ="";
		String cookie = appkeyService.findCookies(appkey);
//		String pageIndex = WebUtil.getString(request, "pageIndex", "1");
//		String pageSize = WebUtil.getString(request, "pageSize", "10");
//		
//		String url1 = Config.queryContactsUrl;	
//		String param1 = "pageIndex=" + pageIndex + "&pageSize=" + pageSize + 
//				"&passengerDTO.passenger_name=" + EncoderUtil.encode(passenger_name);
//		
//		RspHttp rspHttp = HttpUtil.doPost(url1, param1, cookie);
		String url = Config.showContactsUrl;
		String param = "_json_att="+"&passenger_name=" + EncoderUtil.encode(passenger_name) + "&passenger_id_type_code=" + passenger_id_type_code +
				"&passenger_id_no=" + passenger_id_no +"&passenger_type=" + passenger_type;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		String result = rspHttp.getResult();
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
//		List<Contacts> list = new ArrayList<Contacts>();
//		String result1 = rspHttp1.getResult();
		//String passenger_type="";
//		JSONObject jsonResult = new JSONObject();
//		try {
//			JSONObject jsonObject = JSONObject.fromObject(result);
//			boolean status = jsonObject.getBoolean("status");
//			if(status) {
//				JSONObject jsonData = jsonObject.getJSONObject("data");
//				boolean flag = jsonData.getBoolean("flag");
//				if(flag) {
//					JSONArray jsonArray = jsonData.getJSONArray("datas"); //数据集合
//					System.out.println(jsonArray);
//					list = (List<Contacts>)JSONArray.toCollection(jsonArray, Contacts.class);
//					
//					for(Contacts Contacts : list) {
//						//String queryLeftNewDTO = Contacts.getQueryLeftNewDTO().toString();
//						//TicketBook item = (TicketBook) JSONObject.toBean(JSONObject.fromObject(queryLeftNewDTO), TicketBook.class);
//						
//						if(Contacts.passenger_id_no.equals(passenger_id_no)){
//							jsonResult.put("dates", Contacts);
//							
//						}
//						
//					}
//					
//				}
//			}
//		} catch(Exception e) {
//			//list = new ArrayList<Contacts>();
//		}
		JSONObject jsonResult = new JSONObject();
		Document doc = Jsoup.parse(result); 
		//String studentInfoDTO_province_code=doc.select("sex_code > option[checked=checked]").attr("value");
		String old_passenger_name="";
		
		String old_passenger_id_type_code="";
		String sex_code="F";
		//String sex_code_text="";
		String old_passenger_id_no="";
		String born_date="";
		passenger_name=doc.getElementById("name").val();
		 old_passenger_name=doc.getElementById("oldName").val();
		
		 old_passenger_id_type_code=doc.getElementById("oldCardType").val();
		
		 old_passenger_id_no=doc.getElementById("oldCardCode").val();
		 born_date=doc.getElementById("bornDate").val();
		 String country_code=doc.getElementById("nation").val();
//		 sYSO
		 System.out.println("GGGGGGGGGGGGGGG>>>>>>>>>"+country_code);
		 String mobile_no=doc.getElementById("mobileNo").val();
		 String phone_no=doc.getElementById("phoneNo").val();
		 String email=doc.getElementById("email").val();
		 String address=doc.getElementById("address").val();
		 String postalcode=doc.getElementById("zipCode").val();
		
		 Element man=doc.getElementById("man");
		 if( man.attr("checked").equals("checked")){
			 sex_code=man.attr("value");
			// sex_code_text=man.text();
			 //System.out.println("Vincent: >>>>>>>>>>>"+sex_code+sex_code_text);
		 }
//		 else {
//			 Element female=doc.getElementById("female");
//			 if( female.attr("checked").equals("checked")){
//			 sex_code=female.attr("value");}
//		 }
		
		if(passenger_type.equals("3")){
			String studentInfoDTO_school_name=doc.getElementById("schoolNameText").val();
			String studentInfoDTO_school_code=doc.getElementById("schoolCode").val();
			String studentInfoDTO_department=doc.getElementById("department").val();
			String studentInfoDTO_school_class=doc.getElementById("school_class").val();
			String studentInfoDTO_student_no=doc.getElementById("student_no").val();
			String studentInfoDTO_preference_card_no=doc.getElementById("preference_card_no").val();
			String studentInfoDTO_preference_from_station_name=doc.getElementById("preference_from_station_name").val();
			String studentInfoDTO_preference_from_station_code=doc.getElementById("preferenceFromStationCode").val();
			
			String studentInfoDTO_preference_to_station_name=doc.getElementById("preference_to_station_name").val();
			
			String studentInfoDTO_preference_to_station_code=doc.getElementById("preferenceToStationCode").val();
			
			
			String studentInfoDTO_province_code=doc.select("#province_code > option[selected=selected]").attr("value");
			String studentInfoDTO_school_system=doc.select("#school_system > option[selected=selected]").attr("value");
			String studentInfoDTO_enter_year=doc.select("#enter_year > option[selected=selected]").attr("value");
			
		jsonResult.put("studentInfoDTO_school_name", studentInfoDTO_school_name);
	    jsonResult.put("studentInfoDTO_school_code", studentInfoDTO_school_code);
	    jsonResult.put("studentInfoDTO_department", studentInfoDTO_department);
	    jsonResult.put("studentInfoDTO_school_class", studentInfoDTO_school_class);
	    jsonResult.put("studentInfoDTO_student_no", studentInfoDTO_student_no);
	    jsonResult.put("studentInfoDTO_preference_card_no", studentInfoDTO_preference_card_no);
	    jsonResult.put("studentInfoDTO_preference_from_station_name", studentInfoDTO_preference_from_station_name);
	    jsonResult.put("studentInfoDTO_preference_from_station_code", studentInfoDTO_preference_from_station_code);
	    jsonResult.put("studentInfoDTO_preference_to_station_name", studentInfoDTO_preference_to_station_name);
	    jsonResult.put("studentInfoDTO_preference_to_station_code", studentInfoDTO_preference_to_station_code);
	    jsonResult.put("studentInfoDTO_province_code", studentInfoDTO_province_code);
		 jsonResult.put("studentInfoDTO_school_system", studentInfoDTO_school_system);
		 jsonResult.put("studentInfoDTO_enter_year", studentInfoDTO_enter_year);
		
		}
		 //System.out.println(doc); 
		 String born_date_text = "";
		 String true_text = "";
		 String country_text = "";
		 String passenger_id_type_name="";
		Elements els = doc.getElementsByTag("span");
		   for (Element el: els) {
			   String cc=el.text();
			   if(country_code=="CN"){
			   if(cc.indexOf("国家/地区")>-1){
				   country_text= ((Element) el.nextSibling()).text();
				  }
			   else if(cc.indexOf("证件类型")>-1){
				   passenger_id_type_name= ((Element) el.nextSibling()).text();
				  }}
			   else if(cc.indexOf(")")>-1){
				   born_date_text=el.text();}
			   else if(cc.indexOf("核验状态")>-1){
				   true_text= ((Element) el.nextSibling()).text();
				   break;}
		   }
		   if(!country_code.equals("CN")){
			    country_code=doc.select("#nation > option[selected=selected]").attr("value");
				
		   }
		   System.out.println("..............................."+born_date_text);
			System.out.println("..............................."+true_text);
			System.out.println("..............................."+country_text);
			System.out.println("..............................."+passenger_id_type_name);
			System.out.println("..............VVVVVVVVVV................."+country_code);
//		    System.out.println(el.getElementsByAttribute("value"));
//		  passengerType
//		    System.out.println(el.text());
		//}
			jsonResult.put("born_date_text", born_date_text);
			jsonResult.put("true_text", true_text);
			jsonResult.put("country_text", country_text);
			jsonResult.put("passenger_id_type_name", passenger_id_type_name);
			 
			 
			jsonResult.put("passenger_name", passenger_name);
			jsonResult.put("passenger_id_type_code", passenger_id_type_code);
			jsonResult.put("passenger_id_no", passenger_id_no);
			
			jsonResult.put("sex_code", sex_code);
			jsonResult.put("country_code", country_code);
			jsonResult.put("mobile_no", mobile_no);
			jsonResult.put("phone_no", phone_no);
			jsonResult.put("email", email);
			jsonResult.put("address", address);
			jsonResult.put("postalcode", postalcode);
		    jsonResult.put("old_passenger_name", old_passenger_name);
		    jsonResult.put("old_passenger_id_type_code", old_passenger_id_type_code);
		    jsonResult.put("old_passenger_id_no", old_passenger_id_no);
		    jsonResult.put("born_date", born_date);
		    
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
		System.out.println("Vincent:截取数据---->"+rspData.toJsonStr());
		return rspData.toJsonStr();
		
		}

	
}
