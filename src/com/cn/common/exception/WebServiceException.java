package com.cn.common.exception;

/**
 * Web服务异常。
 */
public class WebServiceException 
extends NeededCatchException {

	/**
	 * 版本标识
	 */
	private static final long serialVersionUID = 8654955654324598352L;

	/**
     * 构造
     * 
     * @param cause 异常原因
     */
    public WebServiceException(String cause) {
        super(cause);
    }   
}
