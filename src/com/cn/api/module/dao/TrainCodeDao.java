package com.cn.api.module.dao;

import com.cn.common.database.BaseDao;
import com.cn.api.module.bean.TrainCode;
/** 
 * traincode数据接口   
 */
public interface TrainCodeDao extends BaseDao<TrainCode> {

	public abstract String findTrain(String name);
	public abstract String findTrain_name(String code) ;
	
}
