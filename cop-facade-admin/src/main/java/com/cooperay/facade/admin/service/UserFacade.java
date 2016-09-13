package com.cooperay.facade.admin.service;

import java.util.List;

import com.cooperay.common.facade.BaseFacade;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;

public interface UserFacade extends BaseFacade<User> {

	public void setGroup(UserGroup userGroup);
	
	public void setGroups(List<UserGroup> userGroups);
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 21, 2016
	* @描述：得到用户所有的资源列表，菜单，按钮
	* @参数： @return
	* @返回: List<Resource> 
	* @异常:
	 */
	public List<Resource> getAllUserResource(Long userId);
}
