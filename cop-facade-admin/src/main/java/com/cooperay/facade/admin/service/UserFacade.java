package com.cooperay.facade.admin.service;

import java.util.List;

import com.cooperay.common.facade.BaseFacade;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;

public interface UserFacade extends BaseFacade<User> {

	public void setGroup(UserGroup userGroup);
	
	public void setGroups(List<UserGroup> userGroups);
}
