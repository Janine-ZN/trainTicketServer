package com.cn.api.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.module.bean.TicketOrder;
import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.bean.TrainNo;
import com.cn.api.module.dao.TicketOrderDao;
import com.cn.api.module.dao.TrainNoDao;
import com.cn.api.module.service.TicketOrderService;
import com.cn.api.module.service.TrainNoService;

public class TrainNoServiceImpl implements TrainNoService {

	@Autowired
	TrainNoDao trainNoDao;
	

	public TrainNoDao getTrainNoDao() {
		return trainNoDao;
	}

	public void setTrainNoDao(TrainNoDao trainNoDao) {
		this.trainNoDao = trainNoDao;
	}

	@Override
	public int addItem(TrainNo item) {
		// TODO Auto-generated method stub
		return  trainNoDao.addItem(item);
	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateItem(TrainNo item) {
		// TODO Auto-generated method stub
		return trainNoDao.updateItem(item);
	}

	@Override
	public TrainNo findItem(String id) {
		// TODO Auto-generated method stub
		return trainNoDao.findItem(id);
	}

	@Override
	public TrainNo findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTrain(String name) {
		// TODO Auto-generated method stub
		return trainNoDao.findTrain(name);
	}


}
