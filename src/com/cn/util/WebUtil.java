package com.cn.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.*;

//import com.cn.web.InjectionFilter;

/**
 * HTTP请求参数解析
 */
public class WebUtil {
	
	/**
	 * 防注入过滤器
	 */
/*	private static InjectionFilter injectionFilter = 
		new InjectionFilter();*/
	
    /**
     *  获取字符串<code>String</code>信息
     *
     * @param request 请求对象
     * @param name 请求的参数名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的参数不存在,返回<code>defaultValue</code>
     */
     public static String getString(HttpServletRequest request,
                                    String name,String defaultValue) {
          //String value = injectionFilter.checkSQL(getRequestValue(request,name));
          String value = getRequestValue(request,name);
          if (value == null) {
             value = defaultValue;
          }
          /*if (value != null) {
              value = value.trim();
          }*/    
          return value;
     }

    /**
     *  获取<code>boolean</code>值信息
     *
     * @param request 请求对象
     * @param name 请求的数据名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的参数不存在,返回<code>defaultValue</code>
     */
     public static boolean getBoolean(HttpServletRequest request,
                                    String name,boolean defaultValue) {
          String value = getRequestValue(request,name);
          boolean r = false;
          if (value != null) {
              if ("true".equals(value) || "yes".equals(value)) {
                 r = true;
              }
          } else {
              r = defaultValue;
          }
          return (r);
     }

    /**
     *  获取<code>int</code>值信息
     *
     * @param request 请求对象
     * @param name 请求的参数名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的数据为空值,返回<code>defaultValue</code>
     */
     public static int getInt(HttpServletRequest request,
                                    String name,int defaultValue) {

          String value = getRequestValue(request,name);
          int r = 0;
          if (value != null) {
              try {
                  r = Integer.parseInt(value);
              } catch (Exception e) {
                  r = defaultValue;
              }
          } else {
              r = defaultValue;
          }
          return r;
     }

    /**
     *  获取<code>long</code>值信息
     *
     * @param request 请求对象
     * @param name 请求的参数名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的数据为空值,返回<code>defaultValue</code>
     */
     public static long getLong(HttpServletRequest request,
                                    String name,long defaultValue) {
          String value = getRequestValue(request,name);
          long r = 0;
          if (value != null) {
              try {
                  r = Long.parseLong(value);
              } catch (Exception e) {
                  r = defaultValue;
              }
          } else {
              r = defaultValue;
          }
          return r;
     }

    /**
     *  获取<code>long</code>值信息
     *
     * @param request 请求对象
     * @param name 请求的参数名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的数据为空值,返回<code>defaultValue</code>
     */
     public static double getDouble(HttpServletRequest request,
                                    String name,double defaultValue) {
          String value = getRequestValue(request,name);
          double r = 0.0;
          if (value != null) {
              try {
                  r = Double.parseDouble(value);
              } catch (Exception e) {
                  r = defaultValue;
              }
          } else {
              r = defaultValue;
          }
          return r;
     }

    /**
     *  获取<code>long</code>值信息
     *
     * @param request 请求对象
     * @param name 请求的参数名
     * @param defaultValue 缺省值
     *
     * @return 请求的参数的值,如果请求的数据为空值,返回<code>defaultValue</code>
     */
     public static float getFloat(HttpServletRequest request,
                                    String name,float defaultValue) {
          String value = getRequestValue(request,name);
          float r = 0;
          if (value != null) {
              try {
                  r = Float.parseFloat(value);
              } catch (Exception e) {
                  r = defaultValue;
              }
          } else {
              r = defaultValue;
          }
          return r;
     }


     /**
      * 获取<code>java.util.Date</code>值信息
      * 
      * @param request
      * @param name
      * @param defaultValue
      * 
      * @return Date
      */
     public static java.util.Date getDate(HttpServletRequest request,
                                             String name,Date defaultValue) {         
         String value = getRequestValue(request,name);
         Date date = null;
         if (value != null) {
             value.replace('\\','-');
             value.replace('/','-');         
	         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");	         
	         try {
	             date = format.parse(value);
	         } catch (Exception e) {
	             date = defaultValue;
	         }
         }
         return date;
     }
     
     /**
      * 获取<code>java.lang.String</code>值信息
      * 
      * @param request
      * @param name
      * @param defaultValue
      * 
      * @return Date
      */
     public static String getDateString(HttpServletRequest request,
             String name,String defaultValue) {
         Date date = getDate(request,name,null);
         String value = null;
         if (date != null) {
        	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	 value = format.format(date);
         }
         if (value == null)
             value = defaultValue;         
         return value;    
     }
    
     /**
      * 比较表单提交方法
      * 
      * @param request
      * @param method
      * 
      * @return boolean
      */
     public static boolean compareMethod(HttpServletRequest request,String method) {
         String m = "";
         if (request.getMethod() != null)
             m = request.getMethod();
         return m.toLowerCase().equals(method.toLowerCase());
     }
     
     /**
      * 是否是Multipart请求
      * 
      * @param request
      * 
      * @return boolean
      */
     public static boolean isMultipart(HttpServletRequest request) {
         boolean isMime = false;
         if (request.getContentType() != null)
             isMime = request.getContentType().toLowerCase().indexOf("multipart") > -1;
         return isMime;
     }
     
     /**
      * 获取值
      * 
      * @param request
      * @param name
      * @return
      */
     private static String getRequestValue(HttpServletRequest request,String name) {

         String value = null;
         try {
        	// value = request.getParameter(name);
            
            value= java.net.URLDecoder.decode(request.getParameter(name),"UTF-8");

         } catch (Exception e) {
             value = null;
         }
         return (value);
     }
     
     private static final String DEFAULT_PREFIX="&nbsp;&nbsp;&nbsp;&nbsp;";
 	
     /*
      * 
      * 生成级别缩进格式
      */
     public static String getLevelFlagImg(int level) {
    	 String rootpath = "../images/";
    	 if (level == 1) {
    		return toImage(new StringBuilder(
    			String.valueOf(rootpath)).append(
    			"icon_node.gif").toString()
    		);
 		}
 		StringBuffer result = new StringBuffer();
 		for (int i = 1; i < level; i++) {
 			result.append(toImage(rootpath + "icon_line.gif"));
 		}
 		result.append(toImage(rootpath + "icon_node.gif"));
 		return result.toString();
 	}
 	
 	/**
 	 * 根据级别输出字符串
 	 * 
 	 * @param level
 	 * @param info
 	 * @return
 	 */
 	public static String getLevelFlag(String level, String info) {
 		return getLevelFlag(level, info, DEFAULT_PREFIX);
 	}
 	
 	/**
 	 * 返回级别
 	 * 
 	 * @param level
 	 * @param info
 	 * @param prefix
 	 * @return
 	 */
 	public static String getLevelFlag(String level, String info,String prefix) {
 		StringBuilder temp = new StringBuilder();
 		int lvl = StringUtil.strToInt(level);
 		if (lvl < 1 || lvl ==1)
 			return info;
 		for (int i = 1; i < lvl; i++) {
 			temp.append(prefix);
 		}
 		return temp + "|"+info;
 	}
 	
 	/**
 	 * 是否是密码
 	 * 
 	 * @param source
 	 * @return boolean
 	 */
 	public static boolean isPassword(String source) {
 		Pattern pattern = Pattern.compile("^([0-9A-Za-z_]){3,20}$");
 		return pattern.matcher(source).find();
 	}
 	
 	/**
 	 * 是否是用户名
 	 * 
 	 * @param source
 	 * @return
 	 */
 	public static boolean isUserName(String source) {
		Pattern pattern = Pattern.compile("^([0-9A-Za-z_]){3,20}$");
 		return pattern.matcher(source).find();
 	}
 
 	/**
 	 * 是否是登录码
 	 * 
 	 * @param source
 	 * @return boolean
 	 */
 	public static boolean isLoginCode(String source) {
		Pattern pattern = Pattern.compile("^([0-9A-Za-z_]){4,6}$");
 		return pattern.matcher(source).find();	
 	}
 	
 	/**
 	 * URL转换为IMG标签
 	 * 
 	 * @param url
 	 * @return
 	 */
 	private static String toImage(String url) {
 		return "<img src='" + url + "' border=0>";
 	}
 	
 }