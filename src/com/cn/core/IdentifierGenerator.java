package com.cn.core;

import com.cn.util.UUIDHexUtil;

/**
 * UUID生成器
 */
public class IdentifierGenerator {

	/**
	 * 16进制UUID生成器
	 */
	private static UUIDHexUtil uuidHexUtil = new UUIDHexUtil();

	/**
	 * 生成UUID标识
	 * 
	 * @return String
	 */
	public static synchronized String generateUUID() {
		return uuidHexUtil.generate();
	}

}
