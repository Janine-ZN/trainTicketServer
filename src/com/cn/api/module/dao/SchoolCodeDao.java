package com.cn.api.module.dao;

import com.cn.common.database.BaseDao;
import com.cn.api.module.bean.SchoolCode;
import com.cn.api.module.bean.TicketOrder;
/** 
 * apporder数据接口   
 */
public interface SchoolCodeDao extends BaseDao<SchoolCode> {
	public abstract int addItem_City(SchoolCode item);
	public abstract String finds_City_tationTelecode(String ordernumber);
	public abstract String findstationTelecode(String ordernumber);
	
	
}
