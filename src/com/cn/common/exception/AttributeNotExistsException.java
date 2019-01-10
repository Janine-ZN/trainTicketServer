package com.cn.common.exception;

/**
 * 属性不存在异常
 */
public class AttributeNotExistsException 
extends NeededCatchException {

	/**
	 * 版本标识
	 */
	private static final long serialVersionUID = 8587344865452079629L;

	/**
     * 构造方法
     * 
     * @param cause 异常原因
     */
    public AttributeNotExistsException(String cause) {
        super(cause);
    } 
}
