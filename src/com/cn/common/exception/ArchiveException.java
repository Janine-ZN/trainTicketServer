package com.cn.common.exception;

/**
 * 档案序列化、反序列化异常。
 */
public class ArchiveException 
extends LifelineException {
	
	/**
	 * 版本标识
	 */	
	private static final long serialVersionUID = -8334122196750919728L;

	private String message;
    private Exception exception;	
    
    /**
     * 构造方法
     * @param message    异常信息
     */
    public ArchiveException(Exception exception) {
        this(null, exception);
    }
    
    /**
     * 构造方法
     * @param message    异常信息
     */
    public ArchiveException(String message) {
        this(message,null);
    }

    /**
     * 构造方法
     * 
     * @param message    异常信息
     * @param throwable  异常对象
     */
    public ArchiveException(String message,Exception exception) {
        super(message, exception);
        this.message = message;
        this.exception = exception;
    }    
    

    /**
     * 返回异常信息
     * 
     * @return String
     */
    public String getMessage() {
        return this.message;
    }
    
    /**
     * 返回异常对象
     * 
     * @return Exception
     */
    public Exception getException() {
        return this.exception;
    }      
}
