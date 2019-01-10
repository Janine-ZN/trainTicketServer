package com.cn.api.module.dao;

import com.cn.common.database.BaseDao;
import com.cn.api.module.bean.TrainNo;
/** 
 * trainNo数据接口   
 */
public interface TrainNoDao extends BaseDao<TrainNo> {

	public abstract String findTrain(String name);
	
}
