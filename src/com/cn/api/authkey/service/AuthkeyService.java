package com.cn.api.authkey.service;

public interface AuthkeyService {

	/**
	 * 判断key的状态
	 * 
	 * @param 	authkey
	 * @return	0、key可用，1、key不存在，2、key存在，但已过期
	 */
	public abstract int findAuthkeyStatus(String authkey);
	
}
