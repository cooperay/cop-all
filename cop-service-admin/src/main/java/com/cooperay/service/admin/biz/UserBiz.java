package com.cooperay.service.admin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.service.admin.dao.UserDao;

@Service
public class UserBiz extends BaseBizImpl<User>{
	@Autowired
	private UserDao userDao;
	@Override
	protected BaseDao<User> getDao() {
		return userDao;
	}

	
	
}
