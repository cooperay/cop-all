package com.cooperay.service.admin.dao.impl;

import org.springframework.stereotype.Repository;

import com.cooperay.common.core.dao.BaseDaoImpl;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.service.admin.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

}
