package com.cn.common.page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

/**
 * 数据库分页查询语句构造器接口。
 */
public class PageNavigator {
    
	/**
	 * 页面
	 */
	@SuppressWarnings({"rawtypes" })
	private Page page;
    
    /**
     * 页面参数
     */
    private Map<String, Object> parameters;
    
    /**
     * 页面URL
     */
    private String pageURL;
    
	/**
     * 构造方法
     * 
     * @param page
     * @param pageURL
     */
	@SuppressWarnings("rawtypes")
	public PageNavigator(Page page, String pageURL) {
        this.page = page;
        this.pageURL = pageURL;
        parameters = new HashMap<String, Object>();
    }
    
    /**
     * 添加键值
     * 
     * @param param
     * @param value
     */
	public void addParameter(String param,String value) {
    	String text = value;
    	if (text == null) {
    		text = "";
    	}
        parameters.remove(param);
        parameters.put(param, text);
    }

    /**
     * 添加键值
     * 
     * @param param
     * @param value
     */
    public void addParameter(String param,long value) {
        parameters.remove(param);
        parameters.put(param,Long.toString(value));
    }

    /**
     * 添加键值
     * 
     * @param param
     * @param value
     */
    public void addParameter(String param,int value) {
        parameters.remove(param);
        parameters.put(param,Integer.toString(value));
    }

    /**
     * 添加键值
     * 
     * @param param
     * @param value
     */
    public void addParameter(String param,Date value) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        parameters.remove(param);        
        parameters.put(param,formater.format(value));
    }
    
    /**
     * 返回所有参数
     * 
     * @return
     */
    public Map<String, Object> getParameters() {
    	return parameters;
    }
    
    /**
     * 页面连接地址
     * 
     * @return 返回 pageURL。
     */
    public String getPageURL() {
        return pageURL;
    }
    
    /**
     * 页面连接地址
     * 
     * @param pageURL 要设置的 pageURL。
     */
    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }
    
    /**
	 * @return the page
	 */
	@SuppressWarnings("rawtypes")
	public Page getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	@SuppressWarnings("rawtypes")
	public void setPage(Page page) {
		this.page = page;
	}

	/**
     * 获取导航信息
     * 
     * @return String
     */
    public String getBar() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("   <form name=\"_pageNavForm\"" +
        		" method=\"GET\" action=\""+ getPageURL() +"\">\n");
        if (page.getPageCount() > 0 ) {
            buffer.append(
            		"共&nbsp;" 
            		+ page.getPageCount() 
            		+ "&nbsp;页&nbsp;").append("当前第&nbsp;"
            		+ page.getPageNumber() + "&nbsp;页");
        }
                
        if (hasPreviousPage()) {
            if (page.getPageNumber() > 1)
                buffer.append("&nbsp;<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(1)
                		+ "\">首页</a></font>");           
            
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() - 1) 
            		+ "\">上一页</a>");
        }        
        if (hasNextPage()) {
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() + 1) 
            		+ "\">下一页</a>");
            
            if (page.getPageNumber() < page.getPageCount())
                buffer.append("&nbsp;<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(page.getPageCount()) 
                		+ "\">尾页</a>");
        }        
        
        if (page.getPageCount() > 1 ) {
            buffer.append("&nbsp;&nbsp;转至 <input name=\"page\"" +
            		" type=\"text\" size=\"2\"")
          	   .append(" maxlength=\"6\" value=\""+page.getPageNumber())
               .append("\" class=\"inputflat\"> 页\n ")
               .append(" <input name=\"btnGoPage\" type=\"button\"" +
               		" border=\"0\" value=\"GO\" class=\"btnflat\")")
               .append(" onclick=\"return _pageCheck(this.form,"
            		   + page.getPageCount() +")\">\n")
               .append(formatParameters())
               .append("\n");           
            buffer.append(" <script language='javascript'>\n")
               .append(" <!-- \n")
               .append("    function _pageCheck(frm,pageCount) { \n")
               .append("        var failed = false;  \n")
               .append("        var page = 1; \n")
               .append("        try { \n")
               .append("           page = parseInt(frm.page.value); \n")
               .append("        } catch (e){ \n")
               .append("            failed = true; \n")
               .append("        } \n")
               .append("        if (failed) { \n")
               .append("            alert('页码无效！'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        if (page > pageCount) { \n")
               .append("            alert('输入的页码超过最大页数，" +
               		"页码范围[ 1 - ' + pageCount + ' ]'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        frm.submit(); \n")
               .append("    }\n")
               .append(" //-->\n");
            buffer.append(" </script> \n");
        }     
        buffer.append("   </form>\n");
        return buffer.toString();
    }
    
    /**
     * 获取导航信息
     * 
     * @return String
     */
    public String getCustomBar() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("   <form name=\"_pageNavForm\"" +
        		" method=\"GET\" action=\""+ getPageURL() +"\">\n");
        if (page.getPageCount() > 0 ) {
            buffer.append(
            		"共&nbsp;" 
            		+ page.getPageCount() 
            		+ "&nbsp;页&nbsp;").append("当前第&nbsp;"
            		+ page.getPageNumber() + "&nbsp;页");
        }
        
        if (page.getPageCount() > 0 ) {
            buffer.append("&nbsp;<select name=\"pageSize\" onchange=\"this.form.submit();\">" +
            	"<option value=\"15\""+(this.getPage().getPageSize()==15?" selected":"")+">每页15条</option>" +
            	"<option value=\"20\""+(this.getPage().getPageSize()==20?" selected":"")+">每页20条</option>" +
            	"<option value=\"25\""+(this.getPage().getPageSize()==25?" selected":"")+">每页25条</option>" +
            	"<option value=\"30\""+(this.getPage().getPageSize()==30?" selected":"")+">每页30条</option>" +
            	"<option value=\"35\""+(this.getPage().getPageSize()==35?" selected":"")+">每页35条</option>" +
            	"<option value=\"40\""+(this.getPage().getPageSize()==40?" selected":"")+">每页40条</option>" +
            	"<option value=\"45\""+(this.getPage().getPageSize()==45?" selected":"")+">每页45条</option>" +
            	"<option value=\"50\""+(this.getPage().getPageSize()==50?" selected":"")+">每页50条</option>" +
            	"</selected>"
            );
        }
                
        if (hasPreviousPage()) {
            if (page.getPageNumber() > 1)
                buffer.append("&nbsp;<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(1)
                		+ "\">首页</a></font>");           
            
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() - 1) 
            		+ "\">上一页</a>");
        }        
        if (hasNextPage()) {
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() + 1) 
            		+ "\">下一页</a>");
            
            if (page.getPageNumber() < page.getPageCount())
                buffer.append("&nbsp;<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(page.getPageCount()) 
                		+ "\">尾页</a>");
        }        
        
        if (page.getPageCount() > 1 ) {
            buffer.append("&nbsp;&nbsp;转至 <input name=\"page\"" +
            		" type=\"text\" size=\"2\"")
          	   .append(" maxlength=\"6\" value=\""+page.getPageNumber())
               .append("\" class=\"inputflat\"> 页\n ")
               .append(" <input name=\"btnGoPage\" type=\"button\"" +
               		" border=\"0\" value=\"GO\" class=\"btnflat\")")
               .append(" onclick=\"return _pageCheck(this.form,"
            		   + page.getPageCount() +")\">\n")
               .append(formatParameters())
               .append("\n");           
            buffer.append(" <script language='javascript'>\n")
               .append(" <!-- \n")
               .append("    function _pageCheck(frm,pageCount) { \n")
               .append("        var failed = false;  \n")
               .append("        var page = 1; \n")
               .append("        try { \n")
               .append("           page = parseInt(frm.page.value); \n")
               .append("        } catch (e){ \n")
               .append("            failed = true; \n")
               .append("        } \n")
               .append("        if (failed) { \n")
               .append("            alert('页码无效！'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        if (page > pageCount) { \n")
               .append("            alert('输入的页码超过最大页数，" +
               		"页码范围[ 1 - ' + pageCount + ' ]'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        frm.submit(); \n")
               .append("    }\n")
               .append(" //-->\n");
            buffer.append(" </script> \n");
        }     
        buffer.append("   </form>\n");
        return buffer.toString();
    }
    
    /**
     * 获取导航信息
     * 
     * @return String
     */
    public String getPlatBar() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("   <form name=\"_pageNavForm\"" +
        		" method=\"GET\" action=\""+ getPageURL() +"\">\n");
        if (page.getPageCount() > 0 ) {
            buffer.append("共&nbsp;" 
            		+ page.getRecordCount() 
            		+ "&nbsp;条记录 " 
            		+ page.getPageCount() 
            		+ "&nbsp;页&nbsp;").append("当前第&nbsp;"
            		+ page.getPageNumber() 
            		+ "&nbsp;页");
        }
                
        if (hasPreviousPage()) {
            if (page.getPageNumber() > 1)
                buffer.append("&nbsp;<a href=\""
                		+ getPageURL() + parseParameters(1) 
                		+ "\">首页</a></font>");           
            buffer.append("&nbsp;<a href=\"" + getPageURL() 
            		+ parseParameters(page.getPageNumber() - 1) 
            		+ "\">上一页</a>");
        }        
        if (hasNextPage()) {
            buffer.append("&nbsp;<a href=\"" + getPageURL() 
            		+ parseParameters(page.getPageNumber() + 1)
            		+ "\">下一页</a>");
            if (page.getPageNumber() < page.getPageCount())
                buffer.append("&nbsp;<a href=\""
                		+ getPageURL() 
                		+ parseParameters(page.getPageCount()) 
                		+ "\">尾页</a>");
        }        
        
        if (page.getPageCount() > 1 ) {
            buffer.append("&nbsp;&nbsp;转至 <input name=\"page\"" +
            		" type=\"text\" size=\"2\"")
          	   .append(" maxlength=\"6\" value=\""
          			   + page.getPageNumber())
               .append("\" class=\"inputflat\"> 页\n ")
               .append(" <input name=\"btnGoPage\" type=\"button\"" +
               		" border=\"0\" value=\"GO\" class=\"btnflat\")")
               .append(" onclick=\"return _pageCheck(this.form,"
            		   + page.getPageCount() +")\">\n")
               .append(formatParameters())
               .append("\n");           
            buffer.append(" <script language='javascript'>\n")
               .append(" <!-- \n")
               .append("    function _pageCheck(frm,pageCount) { \n")
               .append("        var failed = false;  \n")
               .append("        var page = 1; \n")
               .append("        try { \n")
               .append("           page = parseInt(frm.page.value); \n")
               .append("        } catch (e){ \n")
               .append("            failed = true; \n")
               .append("        } \n")
               .append("        if (failed) { \n")
               .append("            alert('页码无效！'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        if (page > pageCount) { \n")
               .append("            alert('输入的页码超过最大页数，" +
               		"页码范围[ 1 - ' + pageCount + ' ]'); \n")
               .append("            frm.page.focus(); \n")
               .append("            return; \n")
               .append("        } \n")
               .append("        frm.submit(); \n")
               .append("    }\n")
               .append(" -->\n");
            buffer.append(" </script> \n");
        }     
        buffer.append("   </form>\n");
        return buffer.toString();
    }
    
    /**
     * 获取导航信息
     * 
     * @return
     */
    public String getSimpleBar() {
    	
        StringBuffer buffer = new StringBuffer();

        if (hasPreviousPage()) {
            if (page.getPageNumber() > 1)
                buffer.append("<a href=\"" + getPageURL() 
                		+ parseParameters(1) 
                		+ "\">首页</a></font>");  
            
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() - 1) 
            		+ "\">上一页</a>");
        }   
        
        if (hasNextPage()) {
            buffer.append("&nbsp;<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber() + 1) 
            		+ "\">下一页</a>");
            
            if (page.getPageNumber() < page.getPageCount())
                buffer.append("&nbsp;<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(page.getPageCount())
                		+ "\">尾页</a>");
        }         
        
        if (page.getPageCount() > 1) {
        	buffer.append("&nbsp;&nbsp;" + page.getPageCount()
        			+ " / <font color=#FF0000>"
        			+ page.getPageNumber()+"</font>");
        }
        
        return buffer.toString();
    }
    
    /**
     * 获取导航信息
     * 
     * @return
     */
    public String getDigitBar() {
    	StringBuffer buffer = new StringBuffer();
    	    	
    	// buffer.append(page.getPageNumber() + "," + page.getPageCount());
    	
    	if (page.getPageNumber() > 5) {
            buffer.append("<a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(page.getPageNumber()-5) 
            		+ "\" title=\"浏览前5页记录\"><font face=webdings>9</font></a> ");
    	}    
    	
    	int c = 0;
    	long index = (page.getPageNumber() - (page.getPageNumber() % 6));
    	index = ((index == 0) ? 1 : index);
    	
    	while (index <= page.getPageCount()) {
    		if (++c > 5) break;
            if (page.getPageNumber() == index) {
            	buffer.append("[<font color='#FF0000'>"
            		+ ""+index+"</font>]");
            } else {
                buffer.append("[<a href=\"" 
                		+ getPageURL() 
                		+ parseParameters(index) 
                		+ "\">"+index+"</a>]");
            }
            index ++;            
    	}
    	if (page.getPageCount() >= Math.max(page.getPageNumber(),index)) {
            buffer.append(" <a href=\"" 
            		+ getPageURL() 
            		+ parseParameters(
            		(page.getPageCount() - page.getPageNumber() <= 5) ? page.getPageCount() : page.getPageNumber()+5) 
            		+ "\" title=\"浏览后5页记录\"><font face=webdings>:</font></a>");
    	}
    	
    	buffer.append(" 共 " + page.getPageCount() + " 页");
    	return buffer.toString();
    }
    
    /**
     * @see com.ants.geo.query.Page#clearParameters()
     */
    public void clearParameters() {
        parameters.clear();
    }
    
    /**
     * 取得所有参数
     * 
     * @param no
     * @return
     */
    private String formatParameters() {
        StringBuffer buffer = new StringBuffer();
        parameters.remove("page");
        Iterator<String> it = parameters.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            buffer.append("<input type=\"hidden\" name=\""+ (String)key + "\" " +
                "value=\""+(String)parameters.get(key)+"\">\n");
                            
        }
        return buffer.toString();
    }
    
    /**
     * 取得所有参数
     * 
     * @param no
     * @return
     */
    private String parseParameters(long no) {
        StringBuffer buffer = new StringBuffer();
        parameters.remove("page");
        parameters.put("page",Long.toString(no));
        Iterator<String> it = parameters.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            if (buffer.length() == 0)
                buffer.append("?"+(String)key + "=" + (String)parameters.get(key));
            else
                buffer.append("&"+(String)key + "=" + (String)parameters.get(key));
                            
        }
        return buffer.toString();
    }
    
    /**
     * 是否有前一页
     * 
     * @return boolean
     */
    private boolean hasPreviousPage() {
        return (page.getPageNumber() - 1 > 0);     
    }
    
    /**
     * 是否有下一页
     * 
     * @return boolean
     */
    private boolean hasNextPage() {
        return (page.getPageNumber() + 1 <= page.getPageCount());
    }    
}