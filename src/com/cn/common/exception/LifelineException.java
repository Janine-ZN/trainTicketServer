package com.cn.common.exception;

/**
 * 异常基类，继承此类的异常应用程序可以捕获也可以不捕获。
 */
public class LifelineException 
extends RuntimeException {
	
	/**
	 * 版本标识
	 */
	private static final long serialVersionUID = 8654955654324598351L;
	
	private String message;
    private Exception exception;	
    
    
    /**
     * 构造
     * 
     * @param message    异常信息
     */
    public LifelineException(String message) {
        this(message,null);
    }

    /**
     * 构造
     * 
     * @param message    异常信息
     * @param throwable  异常对象
     */
    public LifelineException(String message,Exception exception) {
        super(message, exception);
        this.message = message;
        this.exception = exception;
    }

    /**
     * 返回异常信息
     * 
     * @return String
     * @uml.property name="message"
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 返回异常对象
     * 
     * @return Exception
     * @uml.property name="exception"
     */
    public Exception getException() {
        return this.exception;
    }
}