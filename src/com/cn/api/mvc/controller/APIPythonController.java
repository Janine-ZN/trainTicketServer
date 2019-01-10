package com.cn.api.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.config.Config;
import com.cn.util.APIPythonUtil;
import com.cn.util.Application;
import com.cn.util.FileUtil;
import com.cn.util.WebUtil;

@Controller
@RequestMapping("/")
public class APIPythonController {

	/**
	 * 自动识别验证码
	 */
	@ResponseBody  
	@RequestMapping(value="/getRandCodeAuto", method=RequestMethod.GET)
	public String getRandCodeAuto(HttpServletRequest request) {
		
		String imgName = WebUtil.getString(request, "imgName", "");
		String imgPath = Application.getRoot() + Config.captchaPath + "/" + imgName;
		System.out.println("######imgPath路径######" + imgPath );
		String flag = WebUtil.getString(request, "flag", "");
		
		return APIPythonUtil.PythonTest(imgPath,flag);
	}
	
	/*
	 * 储存验证码
	 */
	@RequestMapping(value="/setRandCode", method=RequestMethod.GET)
	public void setRandCode( HttpServletRequest request) throws Exception {
		
		String imgName = WebUtil.getString(request, "imgName", "");
		String imgPath = Application.getRoot() + Config.captchaPath + "/" + imgName;
		FileUtil.copy(imgPath);
	}
}
