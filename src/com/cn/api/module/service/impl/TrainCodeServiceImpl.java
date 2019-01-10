package com.cn.api.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.module.bean.TrainCode;
import com.cn.api.module.dao.TrainCodeDao;
import com.cn.api.module.service.TrainCodeService;

public class TrainCodeServiceImpl implements TrainCodeService {

	@Autowired
	TrainCodeDao trainCodeDao;
	
	public TrainCodeDao getTrainCodeDao() {
		return trainCodeDao;
	}

	public void setTrainCodeDao(TrainCodeDao trainCodeDao) {
		this.trainCodeDao = trainCodeDao;
	}

	@Override
	public int addItem(TrainCode item) {
		// TODO Auto-generated method stub
		return  trainCodeDao.addItem(item);
	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateItem(TrainCode item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TrainCode findItem(String id) {
		// TODO Auto-generated method stub
		return trainCodeDao.findItem(id);
	}

	@Override
	public TrainCode findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTrain(String name) {
		// TODO Auto-generated method stub
		return trainCodeDao.findTrain(name);
	}
	@Override
	public String findTrain_name(String code) {
		// TODO Auto-generated method stub
		return trainCodeDao.findTrain_name(code);
	}

//	@Autowired
//	TicketOrderDao TicketOrderDao;
//	
//
//	public TicketOrderDao getTicketOrderDao() {
//		return TicketOrderDao;
//	}
//
//	public void setTicketOrderDao(TicketOrderDao TicketOrderDao) {
//		this.TicketOrderDao = TicketOrderDao;
//	}
//
//	@Override
//	public int addItem(TicketOrder item) {
//		return TicketOrderDao.addItem(item);
//	}
//
//	@Override
//	public int deleteItem(String id) {
//		return TicketOrderDao.deleteItem(id);
//	}
//
//	@Override
//	public int updateItem(TicketOrder item) {
//		return TicketOrderDao.updateItem(item);
//	}
//
//	@Override
//	public TicketOrder findItem(String id) {
//		return TicketOrderDao.findItem(id);
//	}
//
//	@Override
//	public String findOrder(String ordernumber) {
//		return TicketOrderDao.findOrder(ordernumber);
//	}
//
//	@Override
//	public void updateOrder(String ordernumber, String setCookie) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public TicketOrder findItemflag(String id, String num) {
//		// TODO Auto-generated method stub
//		return null;
//	}


//	@Override
//	public void updateItem(String ordernumber, String setCookie) {
//		if(!StringUtil.isNullOrEmpty(setCookie)) {
//			TicketOrder item = TicketOrderDao.findItem(ordernumber);
//			if(item != null) {
//				String order = item.getOrdernumber();
//				if(StringUtil.isNullOrEmpty(order)) {
//					item.setCookies(setCookie);
//				} else {
//					String array[] = setCookie.split(";");
//					for(int i = 0; i < array.length; i++) {
//						if(!StringUtil.isNullOrEmpty(array[i])) {
//							String ck[] = array[i].split("=");
//							String key = ck[0].trim() + "=";
//							int index = cookies.indexOf(key);
//							if(index < 0) {
//								cookies += array[i] + ";";
//							} else {
//								String value = ck[1].trim();
//								int len = key.length();
//								String front = cookies.substring(0, index + len);
//								String temp = cookies.substring(index + len);
//								String behind  = temp.substring(temp.indexOf(";"));
//								cookies = front + value + behind;
//							}
//						}
//					}
//					item.setCookies(cookies);
//				}
//				item.setUpdateTime(DateUtil.getCurrentTimestamp());
//				appkeyDao.updateItem(item);
//			}
//		}
//	}

}
