package com.cooperay.service.admin.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.common.exceptions.BizException;
import com.cooperay.facade.admin.entity.DeptGroup;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;
import com.cooperay.service.admin.dao.ResourceDao;
import com.cooperay.service.admin.dao.UserDao;
import com.cooperay.service.admin.dao.UserGroupDao;

@Service
public class UserBiz extends BaseBizImpl<User>{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserGroupDao userGroupDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private MessageBiz messageBiz;
	
	@Override
	protected BaseDao<User> getDao() {
		return userDao;
	}
	
	@Override
	public long create(User entity) {
		// TODO Auto-generated method stub
		Long long1 = super.create(entity);
		System.out.println(entity.getId());
		messageBiz.sendMessage(JSON.toJSONString(entity));
		return long1;
	}

	public void setGroup(UserGroup userGroup){
		if(userGroup==null || userGroup.getUserId()==null || userGroup.getGroupId()==null){
			throw new BizException("$==>field Resource type"); 
		}
		if(userGroup.isEnable()==null){
			userGroup.setEnable(false);
		}
		
		Map<String, Object> par = new HashMap<>();
		par.put("groupId", userGroup.getGroupId());
		par.put("userId", userGroup.getUserId());
		UserGroup ug = userGroupDao.getBy(par);
		if(userGroup.isEnable()&&ug==null){
			userGroupDao.insert(userGroup);
			return;
		}
		
		if(!userGroup.isEnable()&&ug!=null){
			userGroupDao.deleteById(ug.getId());
			return;
		}
	}
	
	public void setGroups(List<UserGroup> userGroups){
		for (UserGroup userGroup : userGroups) {
			setGroup(userGroup);
		}
	}
	
	public List<Resource> getAllUserResource(Long userId){
		return resourceDao.getAllUserResources(userId);
	}

	
	
}
