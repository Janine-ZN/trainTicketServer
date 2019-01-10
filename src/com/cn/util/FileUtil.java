package com.cn.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.cn.config.Config;

public class FileUtil {

	/**
	 * 生成文件名
	 */
	public static String getFileName() {
		Calendar calendar = Calendar.getInstance();// 获得初始化的时间类；
		String rand = rand();
		String filename = rand + String.valueOf(calendar.getTimeInMillis());// 将时间的毫秒作为文件名字的字符串
		return filename;
	}

	/**
	 * 生成6位随机数
	 */
	public static String rand() {
		String str = "";
		str += (int) (Math.random() * 9 + 1);
		for (int i = 0; i < 5; i++) {
			str += (int) (Math.random() * 10);
		}
		return str;
	}

	/**
	 * 删除过期的验证码
	 */
	public static void deleteCaptcha() {
		String path = Application.getRoot() + Config.captchaPath + "/";
		File file = new File(path);
		File[] list = file.listFiles();
		
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.MINUTE, -2); //当前时间往前推2分钟
		Long current = c.getTimeInMillis();
		
		for (int i = 0; i < list.length; i++) {
			File f = list[i];
			if (f.isFile()) {
				Long time = f.lastModified();
				if(time < current) {
					f.delete();
				}
			}
		}
	}

	
	/*
	 * 保存未识别的图片
	 * 
	 */
	
	public static void copy(String oldPath) throws Exception
	{
		String newPath = Application.getRoot()+Config.UnreImagePath+"/";
		File oldfile = new File(oldPath);
		copyFile(oldfile,new File(newPath+oldfile.getName()));
	}
	
	
	public static void copyFile(File sourceFile,File targetFile) throws IOException 
	{

		FileInputStream input = new FileInputStream(sourceFile); 
		BufferedInputStream inBuff=new BufferedInputStream(input); 

		FileOutputStream output = new FileOutputStream(targetFile); 
		BufferedOutputStream outBuff=new BufferedOutputStream(output); 
		
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len =inBuff.read(b)) != -1){
			outBuff.write(b, 0, len);
		}
		outBuff.flush();
		
		inBuff.close(); 
		outBuff.close(); 
		output.close(); 
		input.close();
	}
}
