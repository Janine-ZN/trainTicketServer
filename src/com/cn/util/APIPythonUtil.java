package com.cn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.cn.config.Config;

public class APIPythonUtil {

	public static String PythonTest(String imagePath,String flag){
		System.out.println("####PythonTest进入####");
		String line = null;
		String date=null;
		String python_path = Config.python_path;
		String resutl_txt = Config.resutl_txt_path;
		if (flag=="buyticket") {
			python_path = Config.python_path_buyticket;
		}
//		String command = "python"+" "+python_path+" "+imagePath+" "+">"+resutl_txt;
		//Janine
		String command = "python"+" "+python_path+" " + imagePath;
		System.out.println("####PythonTest.command####" + command);
		//String[] args = new String[]{"/usr/bin/sh", "-c", command}; //linux下
		String[] args = new String[]{"/bin/sh", "-c", command}; //linux下
		System.out.println("####PythonTest.args####" + args);
		//String[] args = new String[]{"cmd", "/C", command}; //windows下
       
		try {
			Process pr = Runtime.getRuntime().exec(args);
			System.out.println("kkkkkkkkkkkk"+pr);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			System.out.println("kkkkk0000000"+in);
			while ((line=in.readLine())!=null) {
				
				System.out.println("#####line***********:"+line);
				
			}
			in.close();
			pr.waitFor();
			System.out.println("####PythonTest.resutl_txt####" + resutl_txt);
			 date = readString4(resutl_txt);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		System.out.println("####PythonTest.date####" + date);
		return date;
	}

	public static String readString4(String fileName) {
		System.out.println("####readString4.执行####"+fileName);
		int len=0;
        StringBuffer str=new StringBuffer("");
        File file=new File(fileName);
        System.out.println("####readString4.file####"+file);
        try {
            FileInputStream is=new FileInputStream(file);
            System.out.println("####readString4.file.file.is####"+is);
            InputStreamReader isr= new InputStreamReader(is);
            System.out.println("####readString4.isr.isr.isr####"+isr);
            BufferedReader in= new BufferedReader(isr);
            System.out.println("####readString4.in.in.in####"+in);
            String line=null;
            while( (line=in.readLine())!=null )
            {
            	System.out.println("####readString4.line.line.line####"+line);
                if(len != 0)  // 处理换行符的问题
                {
                    str.append(","+line);
                }
                else
                {
                    str.append(line);
                }
                len++;
                System.out.println("####readString4.len.len.len####"+len); 
            }
            in.close();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
        String randCode = str.toString();
        System.out.println("####randCode##replace之前####"+randCode);
        randCode = randCode.replaceAll("\\(", "").replaceAll("\\)", "") .replaceAll("\\s*", "");
        System.out.println("####randCode##replace之后####"+randCode);
        return randCode;
	}
	
}
