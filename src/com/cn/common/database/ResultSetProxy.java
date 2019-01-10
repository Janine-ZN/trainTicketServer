package com.cn.common.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 记录集访问代理。
 */
public class ResultSetProxy {
    
	/**
	 * 日志
	 */
    private static final Log log = 
    	LogFactory.getLog(ResultSetProxy.class);
    
    /**
     * 记录集
     */
    protected ResultSet rs;
    
    /**
     * 记录集元数据
     */
    protected ResultSetMetaData meta;
    
    /**
     * 构造
     * 
     * @throws SQLException
     */
    protected ResultSetProxy() 
    throws SQLException {
    	this(null);
    }
    
    /**
     * 构造
     * 
     * @param rs
     * @throws SQLException
     */
    public ResultSetProxy(ResultSet rs) 
    throws SQLException {
        this.rs = rs;
        initialize();   
    }

    /**
     * 获取文本字段值
     * 
     * @param name
     * 
     * @return String
     */
    public String getText(String name) {
        return getTextField(rs, name, "");        
    }
    
    /**
     * 获取文本字段值
     * 
     * @param name
     * @param defaultValue
     * @return String
     */
    public String getText(String name,String defaultValue) {
        String value = defaultValue;
        if (fieldExists(name)) {
            value = getTextField(rs, name, defaultValue);
        } 
        return value;         
    }
    
    /**
     * 获取字符字段值
     * 
     * @param name
     * @param defaultValue
     * @return String
     */
    public String getString(String name, String defaultValue) {
        String value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getString(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        }
        return value;       
    }
    
    /**
     * 获取int字段值
     * 
     * @param name
     * @return int
     * @throws SQLException
     */
    public int getInt(String name) throws SQLException {
        int value = 0;
        if (fieldExists(name)) {
        	value = rs.getInt(name);
        }
        return value;
    }

    /**
     * 获取int字段值
     * @param name
     * @param defaultValue
     * @return int
     */
    public int getInt(String name,int defaultValue) {
        int value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getInt(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        }
        return value;
    }
    
    /**
     * 获取长整形值
     * 
     * @param name
     * @return
     * @throws SQLException
     */
    public long getLong(String name) 
    		throws SQLException {
        long value = 0L;
        if (fieldExists(name)) {
        	value = rs.getLong(name);
        }
        return value;
    }

    /**
     * 获取长整形值
     * 
     * @param name
     * @param defaultValue
     * @return long
     */
    public long getLong(String name, long defaultValue) {
        long value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getLong(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        }
        return value;
    }    

    /**
     * 获取浮点型值
     * @param name
     * @return float
     * @throws SQLException
     */
    public float getFloat(String name)
    throws SQLException {
        float value = 0;
        if (fieldExists(name)) {
        	value = rs.getFloat(name);
        }
        return value;
    }
    
    /**
     * 获取双精度型值
     * @param name
     * @return float
     * @throws SQLException
     */
    public double getDouble(String name)
    throws SQLException {
    	double value = 0;
        if (fieldExists(name)) {
        	value = rs.getDouble(name);
        }
        return value;
    }

    /**
     * 获取浮点型值
     * 
     * @param name
     * @param defaultValue
     * @return float
     */
    public double getDouble(String name,double defaultValue) {
    	double value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getDouble(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        } 
        return value;
    }
    
    /**
     * 获取浮点型值
     * 
     * @param name
     * @param defaultValue
     * @return float
     */
    public float getFloat(String name,float defaultValue) {
        float value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getFloat(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        } 
        return value;
    }
   
    /**
     * 获取日期值
     * 
     * @param name
     * @return Date
     * @throws SQLException
     */
    public Date getDate(String name) 
    throws SQLException {
        Date value = null;
        if (fieldExists(name)) {
        	value = rs.getDate(name);
        }
        return value;
    }

    /**
     * 获取日期值
     * 
     * @param name
     * @param defaultValue
     * @return Date
     */
    public Date getDate(String name,Date defaultValue) {
        Date value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getDate(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        } 
        return value;
    }
    
    /**
     * 获取时间戳值
     * 
     * @param name
     * @return Timestamp
     * @throws SQLException
     */
    public Timestamp getTimestamp(String name) 
    throws SQLException {
        Timestamp value = null;
        if (fieldExists(name)) {
        	value = rs.getTimestamp(name);
        }
        return value;
    }

    /**
     * 获取时间戳值
     * 
     * @param name
     * @param defaultValue
     * @return Timestamp
     */
    public Timestamp getTimestamp(String name,Timestamp defaultValue) {
        Timestamp value = defaultValue;
        if (fieldExists(name)) {
            try {
                value = rs.getTimestamp(name);
            } catch (SQLException sqle) {
                log.warn(sqle);
            }
        } 
        return value;
    }
    
    /**
     * 初始化
     * 
     * @throws SQLException
     */
    private void initialize() 
    throws SQLException {
        if (rs != null) {
        	meta = rs.getMetaData();
        }    
    }
    
    /**
     * 读取数据库字段值
     * 
     * @param rs
     * @param fieldName
     * @param defaultValue
     * @return String
     */
    public String getTextField(ResultSet rs,
            String fieldName,String defaultValue) {
        String value = null;
        try {
            value = rs.getString(fieldName);
        } catch (Exception e) {
            //ignore
        }
        if (value == null)
            value = defaultValue;
        return value;
    }
    
    /**
     * 字段是否存在
     * @param name
     * @return boolean
     */
    private boolean fieldExists(String name) {
        boolean in = false;
        if (getMetaData() != null && name != null) {
            ResultSetMetaData meta = getMetaData();
            try {
		        for (int i = 1; i <= meta.getColumnCount(); i ++) {
		            if (meta.getColumnName(i).toLowerCase().
		                    equals(name.toLowerCase())) {
		                in = true;
		                break;
		            }
		        }    
            } catch (SQLException sqle) {
                log.warn(sqle);
            }    
        }
        return in;
    }     
    
    /**
     * 获取描述信息
     * @return ResultSetMetaData
     */
    private ResultSetMetaData getMetaData() {
        return meta;
    }
}
