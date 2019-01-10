package com.cn.api.module.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cn.api.module.service.SchoolCodeService;
import com.cn.api.module.bean.SchoolCode;
import com.cn.api.module.dao.SchoolCodeDao;


public class SchoolCodeServiceImpl implements SchoolCodeService {

	@Autowired
	SchoolCodeDao SchoolCodeDao;
	
	public SchoolCodeDao getSchoolCodeDao() {
		return SchoolCodeDao;
	}

	public void setSchoolCodeDao(SchoolCodeDao SchoolCodeDao) {
		this.SchoolCodeDao = SchoolCodeDao;
	}

	@Override
	public int addItem(SchoolCode item) {
		// TODO Auto-generated method stub
		return  SchoolCodeDao.addItem(item);
	}

	@Override
	public int deleteItem(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateItem(SchoolCode item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SchoolCode findItem(String id) {
		// TODO Auto-generated method stub
		return SchoolCodeDao.findItem(id);
	}

	@Override
	public SchoolCode findItemflag(String id, String num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findstationTelecode(String name) {
		// TODO Auto-generated method stub
		return SchoolCodeDao.findstationTelecode(name);
	}

	@Override
	public int addItem_City(SchoolCode item) {
		// TODO Auto-generated method stub
		return  SchoolCodeDao.addItem_City(item);
	}

	@Override
	public String finds_City_tationTelecode(String name) {
		// TODO Auto-generated method stub
		return SchoolCodeDao.finds_City_tationTelecode(name);
	}
	
}
