package com.cn.api.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.module.bean.Appkey;
import com.cn.api.module.dao.AppkeyDao;
import com.cn.api.module.service.AppkeyService;
import com.cn.util.DateUtil;
import com.cn.util.StringUtil;

public class AppkeyServiceImpl implements AppkeyService {

	@Autowired
	AppkeyDao appkeyDao;
	
	public AppkeyDao getAppkeyDao() {
		return appkeyDao;
	}

	public void setAppkeyDao(AppkeyDao appkeyDao) {
		this.appkeyDao = appkeyDao;
	}

	@Override
	public int addItem(Appkey item) {
		return appkeyDao.addItem(item);
	}

	@Override
	public int deleteItem(String id) {
		return appkeyDao.deleteItem(id);
	}

	@Override
	public int updateItem(Appkey item) {
		return appkeyDao.updateItem(item);
	}

	@Override
	public Appkey findItem(String id) {
		return appkeyDao.findItem(id);
	}

	@Override
	public String findCookies(String appkey) {
		return appkeyDao.findCookies(appkey);
	}

	@Override
	public void updateCookies(String appkey, String setCookie) {
		if(!StringUtil.isNullOrEmpty(setCookie)) {
			Appkey item = appkeyDao.findItem(appkey);
			if(item != null) {
				String cookies = item.getCookies();
				if(StringUtil.isNullOrEmpty(cookies)) {
					item.setCookies(setCookie);
				} else {
					String array[] = setCookie.split(";");
					for(int i = 0; i < array.length; i++) {
						if(!StringUtil.isNullOrEmpty(array[i])) {
							String ck[] = array[i].split("=");
							String key = ck[0].trim() + "=";
							int index = cookies.indexOf(key);
							if(index < 0) {
								cookies += array[i] + ";";
							} else {
								String value = ck[1].trim();
								int len = key.length();
								String front = cookies.substring(0, index + len);
								String temp = cookies.substring(index + len);
								String behind  = temp.substring(temp.indexOf(";"));
								cookies = front + value + behind;
							}
						}
					}
					item.setCookies(cookies);
				}			
				item.setUpdateTime(DateUtil.getCurrentTimestamp());
				appkeyDao.updateItem(item);
			}
		}
	}

	@Override
	public Appkey findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

}
