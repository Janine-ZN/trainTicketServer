package com.cn.api.module.service;

import com.cn.api.module.bean.Appkey;
import com.cn.common.database.BaseService;

/**
 * appkey服务类
 */
public interface AppkeyService extends BaseService<Appkey> {

	public abstract String findCookies(String appkey);
	
	public abstract void updateCookies(String appkey, String setCookie);
	
}
