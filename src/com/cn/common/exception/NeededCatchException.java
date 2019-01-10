package com.cn.common.exception;

/**
 * 异常基类，继承此类的异常应用程序均需要捕获并处理。
 */
public class NeededCatchException extends Exception {

	/**
	 * 版本标识
	 */
	private static final long serialVersionUID = 8654955654324598002L;
	
	private String message;
    private Exception exception;	
    
    
    /**
     * 构造
     * 
     * @param message    异常信息
     */
    public NeededCatchException(String message) {
        this(message, null);
    }

    /**
     * 构造
     * 
     * @param message    异常信息
     * @param throwable  异常对象
     */
    public NeededCatchException(String message,Exception exception) {
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
