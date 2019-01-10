package com.cn.api.authkey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.authkey.dao.AuthkeyDao;
import com.cn.api.authkey.service.AuthkeyService;

public class AuthkeyServiceImpl implements AuthkeyService {

	@Autowired
	AuthkeyDao authkeyDao;
	
	public AuthkeyDao getAuthkeyDao() {
		return authkeyDao;
	}

	public void setAuthkeyDao(AuthkeyDao authkeyDao) {
		this.authkeyDao = authkeyDao;
	}

	@Override
	public int findAuthkeyStatus(String authkey) {
		return authkeyDao.findAuthkeyStatus(authkey);
	}
	
}
