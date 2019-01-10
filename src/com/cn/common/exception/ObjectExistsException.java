package com.cn.common.exception;

/**
 * 对象已经存在异常。
 */
public class ObjectExistsException
extends NeededCatchException {
    
    /**
	 * 版本标识
	 */
	private static final long serialVersionUID = 1684802455362415894L;

	/**
     * 构造
     * 
     * @param cause 异常原因
     */
    public ObjectExistsException(String cause) {
        super(cause);
    }    
}