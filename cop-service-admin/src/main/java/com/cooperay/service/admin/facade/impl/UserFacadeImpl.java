package com.cooperay.service.admin.facade.impl;

import org.springframework.stereotype.Service;

import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.service.admin.biz.UserBiz;

@Service("userFacade")
public class UserFacadeImpl extends UserBiz implements UserFacade {
}
