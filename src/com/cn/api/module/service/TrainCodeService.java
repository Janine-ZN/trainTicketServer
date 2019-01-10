package com.cn.api.module.service;

import com.cn.api.module.bean.TrainCode;
import com.cn.common.database.BaseService;

/**
 * TicketOrder服务类
 */
public interface TrainCodeService extends BaseService<TrainCode> {

//	public abstract String findCookies(String appkey);
	public abstract String findTrain(String name);
	public abstract String findTrain_name(String code) ;
//	public abstract void updateCookies(String appkey, String setCookie);
//	public abstract void updateOrder(String ordernumber, String setCookie);

	
}
