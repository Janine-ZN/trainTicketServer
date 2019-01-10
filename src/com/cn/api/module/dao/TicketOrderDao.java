package com.cn.api.module.dao;

import com.cn.common.database.BaseDao;
import com.cn.api.module.bean.TicketOrder;
/** 
 * apporder数据接口   
 */
public interface TicketOrderDao extends BaseDao<TicketOrder> {

	public abstract String findOrder(String ordernumber);
	
}
