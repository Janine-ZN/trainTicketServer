package com.cn.util;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;

import com.cn.api.module.bean.RspHttp;
import com.cn.config.Config;

public class HttpUtil {

	static HostnameVerifier hv = new HostnameVerifier() {  
        public boolean verify(String urlHostName, SSLSession session) {  
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "  
                               + session.getPeerHost());  
            return true;  
        }  
    };  
      
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 		发送请求的URL
	 * @param param 	请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 			所代表远程资源的响应结果
	 */
	public static RspHttp doGet(String url, String param, String cookie) {
		RspHttp rsp = new RspHttp();
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			HttpTrust.trustAllHttpsCertificates();  
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			//conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; rv:44.0) Gecko/20100101 Firefox/44.0");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; rv:45.0) Gecko/20100101 Firefox/45.0");
			if(!StringUtil.isNullOrEmpty(cookie)) {
				conn.setRequestProperty("Cookie", cookie);
			}
			// 建立实际的连接
			conn.connect();
			
			int statusCode = conn.getResponseCode();
			rsp.setCode(statusCode);
			
			if(statusCode == HttpStatus.SC_OK) {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				String result = "";
				while ((line = in.readLine()) != null) {
					result += line;
				}
				rsp.setResult(result);
				
				Map<String, List<String>> maps = conn.getHeaderFields();  
			    String setCookie = "";
				for (String key : maps.keySet()) {
					if("Set-Cookie".equals(key)) {
						List<String> cookielist = maps.get(key);  
						StringBuilder buf = new StringBuilder();
						for(String item : cookielist){
							buf.append(item.substring(0, item.indexOf(";") + 1));
					    }
						setCookie = buf.toString();
					}
				}
				rsp.setCookie(setCookie);
			} 
		} catch (Exception e) {
			rsp.setCode(0);
			rsp.setResult("error");
			rsp.setCookie("");
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rsp;
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url 		发送请求的 URL
	 * @param param 	请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 			所代表远程资源的响应结果
	 */
	public static RspHttp doPost(String url, String param, String cookie) {
		RspHttp rsp = new RspHttp();
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			HttpTrust.trustAllHttpsCertificates();  
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			
			conn.setInstanceFollowRedirects(false);  
		
			// 设置通用的请求属性
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:45.0) Gecko/20100101 Firefox/45.0");
			if(!StringUtil.isNullOrEmpty(cookie)) {
				conn.setRequestProperty("Cookie", cookie); 
			}
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应

			int statusCode = conn.getResponseCode();
			rsp.setCode(statusCode);
			
			if(statusCode == HttpStatus.SC_OK) {
				String result = "";
				in = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
				rsp.setResult(result);
				
				Map<String, List<String>> maps = conn.getHeaderFields();  
			    String setCookie = "";
				for (String key : maps.keySet()) {
					if("Set-Cookie".equals(key)) {
						List<String> cookielist = maps.get(key);  
						StringBuilder buf = new StringBuilder();
						for(String item : cookielist){
							buf.append(item.substring(0, item.indexOf(";") + 1));
					    }
						setCookie = buf.toString();
					}
				}
				rsp.setCookie(setCookie);
			}
			
		} catch (Exception e) {
			rsp.setCode(0);
			rsp.setResult("error");
			rsp.setCookie("");
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return rsp;
	}
	
	/**
	 * 获取验证码
	 */
	public static String getPassCodeNew(String urlString, String cookie)  {
		try {
			HttpTrust.trustAllHttpsCertificates();  
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			
			FileUtil.deleteCaptcha(); //删除过期的验证码
			String fileName = FileUtil.getFileName() + ".jpg";
			String filePath = Application.getRoot() + Config.captchaPath + "/" + fileName;
			
		    // 构造URL
		    URL url = new URL(urlString);
		    // 打开连接
		    URLConnection con = url.openConnection();
		    
		    if(cookie != null && !"".equals(cookie)) {
				con.setRequestProperty("Cookie", cookie);
			}
		    
		    // 输入流
		    InputStream is = con.getInputStream();
		    // 1K的数据缓冲
		    byte[] bs = new byte[1024];
		    // 读取到的数据长度
		    int len;
		    // 输出的文件流
		    OutputStream os = new FileOutputStream(filePath);
		    // 开始读取
		    while ((len = is.read(bs)) != -1) {
		      os.write(bs, 0, len);
		    }
		    
		    Map<String, List<String>> maps = con.getHeaderFields();  
		    String setCookie = "";
			for (String key : maps.keySet()) {
				if("Set-Cookie".equals(key)) {
					List<String> cookielist = maps.get(key);  
					StringBuilder buf = new StringBuilder();
					for(String item : cookielist){
						buf.append(item.substring(0, item.indexOf(";") + 1));
				    }
					setCookie = buf.toString();
				}
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fileName", fileName);
			
			if(!StringUtil.isNullOrEmpty(setCookie)) {
				jsonObject.put("setCookie", setCookie);
			} else {
				jsonObject.put("setCookie", "");
			}
			
		    // 完毕，关闭所有链接
		    os.close();
		    is.close();
		    
		    return jsonObject.toString();
		    
		} catch(Exception e) {
			return "";
		}
	}   

	
}
