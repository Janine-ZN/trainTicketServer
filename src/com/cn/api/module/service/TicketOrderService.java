package com.cn.api.module.service;

import com.cn.api.module.bean.TicketOrder;
import com.cn.common.database.BaseService;

/**
 * TicketOrder服务类
 */
public interface TicketOrderService extends BaseService<TicketOrder> {

//	public abstract String findCookies(String appkey);
	public abstract String findOrder(String ordernumber);
	
//	public abstract void updateCookies(String appkey, String setCookie);
	public abstract void updateOrder(String ordernumber, String setCookie);

	
}
