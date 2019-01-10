package com.cn.common.page;

/**
 * sqlserver数据库分页查询语句构造器。
 */
public class SqlServerPageStatementBuilder 
implements PageStatementBuilder {
	
	/**
	 * @see com.cn.common.page.PageStatementBuilder
	 */
	@Override
	public String buildPageStatement(String sql, 
	int startIndex, int endIndex, int pageSize) {
		 endIndex =startIndex+pageSize;
		 StringBuffer pagingBuilder = new StringBuffer();     
         String orderby = getOrderByPart(sql);     
         String distinctStr = "";     
         String loweredString = sql.toLowerCase();     
         String sqlPartString = sql.trim();     
         if (loweredString.trim().startsWith("select")) {     
             int index = 6;     
             if (loweredString.startsWith("select distinct")) {     
                 distinctStr = "DISTINCT ";     
                 index = 15;     
             }     
             sqlPartString = sqlPartString.substring(index);     
         }     
         pagingBuilder.append(sqlPartString);     

         // if no ORDER BY is specified use fake ORDER BY field to avoid errors     
         if (orderby == null || orderby.length() == 0) {     
             orderby = "ORDER BY CURRENT_TIMESTAMP";     
         }     
         StringBuffer result = new StringBuffer();     
         result.append("SELECT * FROM (")      
         .append("SELECT ")     
         .append(distinctStr)     
         .append(" TOP 100 PERCENT ROW_NUMBER() OVER (") //使用TOP 100 PERCENT可以提高性能     
         .append(orderby)     
         .append(") AS rowNum, ")     
         .append(pagingBuilder)     
         .append(") as temp_table WHERE rowNum >")   
         .append(startIndex)     
         .append(" AND rowNum <=")     
         .append(endIndex)     
         .append(" ORDER BY rowNum"); 
         
        // System.out.println("reslut==="+result.toString());
         return result.toString();   
	}

	/**
	 * 构造查询总数SQL
	 * @see com.cn.common.page.buildCountStatement
	 */
	@Override
	public String buildCountStatement(String sql) {
		
		int startIndex =  sql.indexOf("from");
		if (startIndex == -1) {
			startIndex =  sql.indexOf("From");
		}
		int endIndex =  sql.indexOf("ORDER BY") == -1 ?  sql.length() :  sql.indexOf("ORDER BY");
		
		String countHql = "select count(*) "+  sql.substring(startIndex, endIndex);
		return countHql;
	}
	
	
	/**
	 * 取得orderBy 排序条件
	 * @param sql
	 * @return
	 */
	private static String getOrderByPart(String sql) {   
	        String loweredString = sql.toLowerCase();   
	        int orderByIndex = loweredString.indexOf("order by");   
	        if (orderByIndex != -1) {   
	            return sql.substring(orderByIndex);   
	        } else {   
	            return "";   
	        }   
	 }	
}
