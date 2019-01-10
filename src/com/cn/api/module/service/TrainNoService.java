package com.cn.api.module.service;

import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.bean.TrainNo;
import com.cn.common.database.BaseService;

/**
 * TicketOrder服务类
 */
public interface TrainNoService extends BaseService<TrainNo> {

//	public abstract String findCookies(String appkey);
	public abstract String findTrain(String name);
	
//	public abstract void updateCookies(String appkey, String setCookie);
//	public abstract void updateOrder(String ordernumber, String setCookie);

	
}
