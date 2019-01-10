package com.cn.api.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.module.bean.RspHttp;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.service.AppkeyService;
import com.cn.api.module.service.TrainCodeService;
import com.cn.api.module.service.TrainNoService;
import com.cn.config.Config;
import com.cn.util.DoUtil;
import com.cn.util.HttpUtil;
import com.cn.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JUheData {
	
	@Autowired
	AppkeyService appkeyService;
	
	
	@Autowired
	TrainCodeService  trainCodeService;
	
	@Autowired
	TrainNoService  trainNoService;
	
	
//	   File f = new File("f:\\train\\NEW.txt");
//	   BufferedReader bf = new BufferedReader(new FileReader(f));
//	   String str;
//	   String train;
//	   File file = new File("f:\\train\\train_xu_e.txt");
//	   File notrainFile = new  File("f:\\train\\no_train.txt");
//	   File dataFile = new  File("f:\\train\\data_train.txt");
//		 if (!file.exists()) {
//			    file.createNewFile();
//			   }
//		 if(!notrainFile.exists()){
//			 notrainFile.createNewFile();
//		 }
//		 
//		 if(!dataFile.exists()){
//			 dataFile.createNewFile();
//		 }
//		 
//		 FileWriter fw = new FileWriter(file.getAbsoluteFile());
//		 BufferedWriter bw = new BufferedWriter(fw);
//		 FileWriter nofw = new FileWriter(notrainFile.getAbsoluteFile());
//		 BufferedWriter nobw = new BufferedWriter(nofw);
//		 
//		 
//		 while((str=bf.readLine())!=null)
//		 {
//			  String[] s=str.split(",");
//			  if(!str.trim().isEmpty())
//			  {
//				  for (int i = 0; i < s.length; i++)
//				  {
//					  train=s[i] ;
//					  String appkey = "e550a48c3f23cae2683354fef485b516";
//					  
//					  String url2 = Config.JuheTrainQueryUrl;
//		    		String param2 = "train="+train+"&key=" + appkey;
//		    		
//		    		RspHttp rspHttp1 = HttpUtil.doGet(url2, param2,"");
//		    		String data = rspHttp1.getResult();
//		    		JSONObject jsonObject1 = JSONObject.fromObject(data);
//		    		JSONObject jsonReault = jsonObject1.getJSONObject("result"); //数据
//		    		if (!jsonReault.isNullObject()) 
//		    		{
//		    			//获取出发站,目的站
//		    			jsonObject1 = JSONObject.fromObject(data);
//	    				JSONObject  jsonList= jsonReault.getJSONObject("list");
//		    			String start_station = jsonList.getString("start_station");
//		    			String end_station = jsonList.getString("end_station");
//		    			String start_station_code = trainCodeService.findTrain(start_station);
//		    			String end_station_code = trainCodeService.findTrain(end_station);
//		    			
//		    			String url_yp= Config.queryTicketUrl;
//		    			String param_yp = "purpose_codes=" + "ADULT" + "&queryDate=" + "2016-06-04" + 
//    							"&from_station=" + start_station_code + "&to_station=" + end_station_code;
//		    			String param_yp_e = "purpose_codes=" + "ADULT" + "&queryDate=" + "2016-08-02" + 
//    							"&from_station=" + start_station_code + "&to_station=" + end_station_code;
//		    			
//		    			String data_yp =DoUtil.makeGet(url_yp, param_yp, "");//03号的数据
//		    			String data_yp_e =DoUtil.makeGet(url_yp, param_yp_e, "");//1号的数据
//		    		
//		    			if (!StringUtil.isNullOrEmpty(data_yp)&&!data_yp.equals("-1")&&!StringUtil.isNullOrEmpty(data_yp_e)&&!data_yp_e.equals("-1"))
//		    			{
//		    				 JSONObject jsonObject = JSONObject.fromObject(data_yp);
//		    				 jsonObject = jsonObject.getJSONObject("data");
//		    				 boolean flag = jsonObject.getBoolean("flag");
//		    				 if (flag)
//		    				 {
//		    					 JSONArray jsonArray = jsonObject.getJSONArray("datas");
//		    						System.out.println("!!!!!!!!!!!"+jsonArray);
//		    					 if (jsonArray.size()>0) 
//		    					 {
//		    						 for (int k = 0; k < jsonArray.size(); k++)
//		    						 {
//		    							 System.out.println("******"+jsonArray.getJSONObject(k));
		    							 
//		    							 String train_no= jsonArray.getJSONObject(k).getString("train_no");
//		    							 String station_train_code2= jsonArray.getJSONObject(k).getString("station_train_code");
//		    							 String start_station_code2= jsonArray.getJSONObject(k).getString("start_station_telecode");
//		    							 String end_station_code2= jsonArray.getJSONObject(k).getString("end_station_telecode");
//		    							 String currentTime = DateUtil.getCurrentDate();
//					    				 Timestamp current = DateUtil.getCurrentTimestamp();
//					    				 System.out.println("train"+station_train_code2);
//					    				 TrainNo trainIteam = new TrainNo();
//					    				 trainIteam.setTrain_no(station_train_code2);
//					    				 trainIteam.setCreateTime(current);
//					    				 trainIteam.setUpdateTime(currentTime);
//					    				 trainIteam.setBookTime("0-60");
//											trainIteam.setTrain_nu(train_no);
//											trainNoService.addItem(trainIteam);
											//这里面的每一辆车得到起train_no
//											String url_cx = Config.queryByTrainNoUrl;
//											System.out.println("start_station_code2"+start_station_code2);
//											System.out.println("end_station_code2"+end_station_code2);
//											String param_cx= "train_no=" + train_no + "&from_station_telecode=" + start_station_code2 + 
//													"&to_station_telecode=" + end_station_code2 + "&depart_date=" + "2016-06-03";
//											System.out.println("param_cx"+param_cx);
//											String data_cx =DoUtil.makeGet(url_cx, param_cx, "");
//											System.out.println("data_cx********"+data_cx);
//											if (!StringUtil.isNullOrEmpty(data_cx)&&!data_cx.equals("-1")) 
//											{
//											
//												JSONObject jsondata_cx = JSONObject.fromObject(data_cx);
//												JSONObject	jsonObject_cx = jsondata_cx.getJSONObject("data");
//												JSONArray jsonArray_cx = jsonObject_cx.getJSONArray("data");
//								    			 if (jsonArray_cx.size()>0)
//								    			 {
//								    				 String station_train_code= jsonArray.getJSONObject(0).getString("station_train_code");
//								    				 bw.write(station_train_code);
//								    				 bw.write("\u0000");
//								    				 bw.write("\u0000");
//								    				 bw.write("\u0000");
//								    				 bw.write("-----预售期60天-----");
//								    				 bw.write("\r\n");
//								    				 for (int j = 0; j < jsonArray_cx.size(); j++)
//								    				 {
//								    					 
//								    					 String station_name= jsonArray_cx.getJSONObject(j).getString("station_name");
//									    				 String arrive_time= jsonArray_cx.getJSONObject(j).getString("arrive_time");
//									    				 String start_time= jsonArray_cx.getJSONObject(j).getString("start_time");
//									    				 String station_no= jsonArray_cx.getJSONObject(j).getString("station_no");
//									    				 String stopover_time= jsonArray_cx.getJSONObject(j).getString("stopover_time");
//									    				 bw.write(station_no);
//									    				 bw.write("\u0000");
//									    				 bw.write(station_name);
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write(arrive_time);
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write(start_time);
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write("\u0000");
//									    				 bw.write(stopover_time);
//									    				 bw.write("\r\n");
//								    				 }
//								    			 }
//								    			 bw.write("\r\n");
//					    						 bw.write("\r\n");
//											}
//		    						 }
//		    						 bw.write("\r\n");
//		    						 bw.write("\r\n");
//		    					 }
//		    				 }
//		    			}
//		    		}else {
//						//把车辆保存在文档里面
//		    			nobw.write(train);
//		    			nobw.write(",");
//					}
//				  }
//			  }
//		 }
/*****************************读取每辆车经过的车站end***************************************************/
	
	
		 
}
