package com.cn.api.module.service;

import com.cn.api.module.bean.SchoolCode;
import com.cn.common.database.BaseService;

/**
 * TicketOrder服务类
 */
public interface SchoolCodeService extends BaseService<SchoolCode> {

	public abstract int addItem_City(SchoolCode item);
	public abstract String finds_City_tationTelecode(String name);
	public abstract String findstationTelecode(String name);

}
