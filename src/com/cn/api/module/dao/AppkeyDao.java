package com.cn.api.module.dao;

import com.cn.api.module.bean.Appkey;
import com.cn.common.database.BaseDao;

/** 
 * appkey数据接口   
 */
public interface AppkeyDao extends BaseDao<Appkey> {

	public abstract String findCookies(String appkey);
	
}
