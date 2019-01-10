package com.cn.api.mvc.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.service.AppkeyService;
import com.cn.config.Config;
import com.cn.util.HttpUtil;
import com.cn.util.WebUtil;

/**
 * 个人信息
 */
@Controller
@RequestMapping("/")
public class APIUserInfoController {

	@Autowired
	AppkeyService appkeyService;
	
	public AppkeyService getAppkeyService() {
		return appkeyService;
	}

	public void setAppkeyService(AppkeyService appkeyService) {
		this.appkeyService = appkeyService;
	}
	
	/**
	 * 修改个人信息
	 * 
	 * @param userDTOloginUserDTOuser_name
	 * @param userDTOsex_code
	 * @param userDTOcountry_code
	 * @param userDTOborn_date
	 * @param userDTOpassword
	 * @param userDTOmobile_no
	 * @param userDTOphone_no
	 * @param userDTOemail
	 * @param userDTOaddress
	 * @param userDTOpostalcode
	 * @param userDTOloginUserDTOuser_type
	 * @param userDTOstudentInfoDTOprovince_name
	 * @param userDTOstudentInfoDTOprovince_code
	 * @param userDTOstudentInfoDTOschool_name
	 * @param userDTOstudentInfoDTOschool_code
	 * @param userDTOstudentInfoDTOdepartment
	 * @param userDTOstudentInfoDTOschool_class
	 * @param userDTOstudentInfoDTOstudent_no
	 * @param userDTOstudentInfoDTOschool_system
	 * @param userDTOstudentInfoDTOenter_year
	 * @param userDTOstudentInfoDTOpreference_card_no
	 * @param userDTOstudentInfoDTOpreference_from_province_code
	 * @param userDTOstudentInfoDTOpreference_to_province_code
	 * @param userDTOstudentInfoDTOpreference_from_station_name
	 * @param userDTOstudentInfoDTOpreference_from_station_code
	 * @param userDTOstudentInfoDTOpreference_to_station_name
	 * @param userDTOstudentInfoDTOpreference_to_station_code
	 * @param userDTOis_active
	 * @param userDTOcheck_code
	 * @param userDTOrevSm_code
	 * @param userDTOflag
	 * @param userDTOlast_login_time
	 * @param userDTOfirst_letter
	 * @param userDTOuser_id
	 * @param userDTOphone_flag
	 * @param userDTOencourage_flag
	 * @param userDTOneedModifyPassword
	 * @param userDTOneedModifyEmail
	 * @param userDTOflag_member
	 * @param userDTOloginUserDTOchannel
	 * @param userDTOloginUserDTOagent_contact
	 * @param userDTOloginUserDTOuser_id
	 * @param userDTOloginUserDTOrefundLogin
	 * @param userDTOstudentInfoDTOstudent_name
	 * @param userDTOstudentInfoDTOcity_name
	 * @param userDTOstudentInfoDTOcity_code
	 * @param isInputPassword
	 */
	@ResponseBody
	@RequestMapping(value="/editUserInfo", method=RequestMethod.POST)
	public String editUserInfo(HttpServletRequest request) {
		String userDTOloginUserDTOuser_name = WebUtil.getString(request, "userDTOloginUserDTOuser_name", "");
		String userDTOsex_code = WebUtil.getString(request, "userDTOsex_code", "");
		String userDTOborn_date = WebUtil.getString(request, "userDTOborn_date", "");
		String userDTOpassword = WebUtil.getString(request, "userDTOpassword", "");
		String userDTOmobile_no = WebUtil.getString(request, "userDTOmobile_no", "");
		String userDTOphone_no = WebUtil.getString(request, "userDTOphone_no", "");
		String userDTOemail = WebUtil.getString(request, "userDTOemail", "");
		String userDTOaddress = WebUtil.getString(request, "userDTOaddress", "");
		String userDTOpostalcode = WebUtil.getString(request, "userDTOpostalcode", "");
		String userDTOloginUserDTOuser_type = WebUtil.getString(request, "userDTOloginUserDTOuser_type", "");
		String userDTOstudentInfoDTOprovince_name = WebUtil.getString(request, "userDTOstudentInfoDTOprovince_name", "");
		String userDTOstudentInfoDTOprovince_code = WebUtil.getString(request, "userDTOstudentInfoDTOprovince_code", "");
		String userDTOstudentInfoDTOschool_name = WebUtil.getString(request, "userDTOstudentInfoDTOschool_name", "");
		String userDTOstudentInfoDTOschool_code = WebUtil.getString(request, "userDTOstudentInfoDTOschool_code", "");
		String userDTOstudentInfoDTOdepartment = WebUtil.getString(request, "userDTOstudentInfoDTOdepartment", "");
		String userDTOstudentInfoDTOschool_class = WebUtil.getString(request, "userDTOstudentInfoDTOschool_class", "");
		String userDTOstudentInfoDTOstudent_no = WebUtil.getString(request, "userDTOstudentInfoDTOstudent_no", "");
		String userDTOstudentInfoDTOschool_system = WebUtil.getString(request, "userDTOstudentInfoDTOschool_system", "");
		String userDTOstudentInfoDTOenter_year = WebUtil.getString(request, "userDTOstudentInfoDTOenter_year", "");
		String userDTOstudentInfoDTOpreference_card_no = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_card_no", "");
		String userDTOstudentInfoDTOpreference_from_province_code = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_from_province_code", "");
		String userDTOstudentInfoDTOpreference_to_province_code = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_to_province_code", "");
		String userDTOstudentInfoDTOpreference_from_station_name = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_from_station_name", "");
		String userDTOstudentInfoDTOpreference_from_station_code = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_from_station_code", "");
		String userDTOstudentInfoDTOpreference_to_station_name = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_to_station_name", "");
		String userDTOstudentInfoDTOpreference_to_station_code = WebUtil.getString(request, "userDTOstudentInfoDTOpreference_to_station_code", "");
		String userDTOis_active = WebUtil.getString(request, "userDTOis_active", "");
		String userDTOcheck_code = WebUtil.getString(request, "userDTOcheck_code", "");
		String userDTOrevSm_code = WebUtil.getString(request, "userDTOrevSm_code", "");
		String userDTOflag = WebUtil.getString(request, "userDTOflag", "");
		String userDTOlast_login_time = WebUtil.getString(request, "userDTOlast_login_time", "");
		String userDTOfirst_letter = WebUtil.getString(request, "userDTOfirst_letter", "");
		String userDTOuser_id = WebUtil.getString(request, "userDTOuser_id", "");
		String userDTOphone_flag = WebUtil.getString(request, "userDTOphone_flag", "");
		String userDTOencourage_flag = WebUtil.getString(request, "userDTOencourage_flag", "");
		String userDTOneedModifyPassword = WebUtil.getString(request, "userDTOneedModifyPassword", "");
		String userDTOneedModifyEmail = WebUtil.getString(request, "userDTOneedModifyEmail", "");
		String userDTOflag_member = WebUtil.getString(request, "userDTOflag_member", "");
		String userDTOloginUserDTOchannel = WebUtil.getString(request, "userDTOloginUserDTOchannel", "");
		String userDTOloginUserDTOagent_contact = WebUtil.getString(request, "userDTOloginUserDTOagent_contact", "");
		String userDTOloginUserDTOuser_id = WebUtil.getString(request, "userDTOloginUserDTOuser_id", "");
		String userDTOloginUserDTOrefundLogin = WebUtil.getString(request, "userDTOloginUserDTOrefundLogin", "");
		String userDTOstudentInfoDTOstudent_name = WebUtil.getString(request, "userDTOstudentInfoDTOstudent_name", "");
		String userDTOstudentInfoDTOcity_name = WebUtil.getString(request, "userDTOstudentInfoDTOcity_name", "");
		String userDTOstudentInfoDTOcity_code = WebUtil.getString(request, "userDTOstudentInfoDTOcity_code", "");
		String isInputPassword = WebUtil.getString(request, "isInputPassword", "");
		String appkey = WebUtil.getString(request, "appkey", "");
		
		String cookie = appkeyService.findCookies(appkey);
		
		String url = Config.editUserInfoUrl;
		String param = "userDTO.loginUserDTO.user_name=" + userDTOloginUserDTOuser_name + "&userDTO.sex_code=" + userDTOsex_code + 
				"&userDTO.country_code=" + userDTOborn_date + "&userDTO.born_date=" + userDTOborn_date + 
				"&userDTO.password=" + userDTOpassword + "&userDTO.mobile_no=" + userDTOmobile_no + 
				"&userDTO.phone_no=" + userDTOphone_no + "&userDTO.email=" + userDTOemail + 
				"&userDTO.address=" + userDTOaddress + "&userDTO.postalcode=" + userDTOpostalcode + 
				"&userDTO.loginUserDTO.user_type=" + userDTOloginUserDTOuser_type + "&userDTO.studentInfoDTO.province_name=" + userDTOstudentInfoDTOprovince_name + 
				"userDTO.studentInfoDTO.province_code=" + userDTOstudentInfoDTOprovince_code + "&userDTO.studentInfoDTO.province_code=" + userDTOstudentInfoDTOprovince_code + 
				"&userDTO.studentInfoDTO.school_name=" + userDTOstudentInfoDTOschool_name + "&userDTO.studentInfoDTO.school_code=" + userDTOstudentInfoDTOschool_code + 
				"&userDTO.studentInfoDTO.department=" + userDTOstudentInfoDTOdepartment + "&userDTO.studentInfoDTO.school_class=" + userDTOstudentInfoDTOschool_class + 
				"&userDTO.studentInfoDTO.student_no=" + userDTOstudentInfoDTOstudent_no + "&userDTO.studentInfoDTO.school_system=" + userDTOstudentInfoDTOschool_system + 
				"&userDTO.studentInfoDTO.enter_year=" + userDTOstudentInfoDTOenter_year + "&userDTO.studentInfoDTO.preference_card_no=" + userDTOstudentInfoDTOpreference_card_no + 
				"&userDTO.studentInfoDTO.preference_from_province_code=" + userDTOstudentInfoDTOpreference_from_province_code + 
				"&userDTO.studentInfoDTO.preference_to_province_code=" + userDTOstudentInfoDTOpreference_to_province_code + 
				"&userDTO.studentInfoDTO.preference_to_province_code=" + userDTOstudentInfoDTOpreference_to_province_code + 
				"&userDTO.studentInfoDTO.preference_from_station_name=" + userDTOstudentInfoDTOpreference_from_station_name + 
				"&userDTO.studentInfoDTO.preference_from_station_code=" + userDTOstudentInfoDTOpreference_from_station_code + 
				"&userDTO.studentInfoDTO.preference_to_station_name=" + userDTOstudentInfoDTOpreference_to_station_name + 
				"&userDTO.studentInfoDTO.preference_to_station_code=" + userDTOstudentInfoDTOpreference_to_station_code + 
				"&userDTO.studentInfoDTO.preference_to_station_code=" + userDTOstudentInfoDTOpreference_to_station_code + 
				"&userDTO.is_active" + userDTOis_active + "&userDTO.check_code=" + userDTOcheck_code + 
				"&userDTO.revSm_code=" + userDTOrevSm_code + "&userDTO.flag=" + userDTOflag + 
				"&userDTO.last_login_time=" + userDTOlast_login_time + "&userDTO.first_letter=" + userDTOfirst_letter + 
				"&userDTO.user_id=" + userDTOuser_id + "&userDTO.phone_flag=" + userDTOphone_flag + 
				"&userDTO.encourage_flag=" + userDTOencourage_flag + "&userDTO.needModifyPassword=" + userDTOneedModifyPassword + 
				"&userDTO.needModifyEmail=" + userDTOneedModifyEmail + "&userDTO.flag_member=" + userDTOflag_member + 
				"&userDTO.loginUserDTO.channel=" + userDTOloginUserDTOchannel + "&userDTO.loginUserDTO.agent_contact=" + userDTOloginUserDTOagent_contact + 
				"&userDTO.loginUserDTO.user_id=" + userDTOloginUserDTOuser_id + "&userDTO.loginUserDTO.refundLogin=" + userDTOloginUserDTOrefundLogin + 
				"&userDTO.studentInfoDTO.student_name=" + userDTOstudentInfoDTOstudent_name + "&userDTO.studentInfoDTO.city_name=" + userDTOstudentInfoDTOcity_name + 
				"&userDTO.studentInfoDTO.city_code=" + userDTOstudentInfoDTOcity_code + "&isInputPassword=" + isInputPassword;
		
		RspHttp rspHttp = HttpUtil.doPost(url, param, cookie);
		
		String setCookie = rspHttp.getCookie();
		appkeyService.updateCookies(appkey, setCookie);
		
		return rspHttp.getResult();
	}
	
}
