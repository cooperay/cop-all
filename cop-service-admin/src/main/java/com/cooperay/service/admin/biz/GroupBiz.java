package com.cooperay.service.admin.biz;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.common.exceptions.BizException;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.service.admin.dao.GroupDao;
import com.cooperay.service.admin.dao.GroupResourceDao;

@Service
public class GroupBiz extends BaseBizImpl<Group> {

	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private GroupResourceDao groupResourceDao;
	
	@Override
	protected BaseDao<Group> getDao() {
		return groupDao;
	}
	
	public void setResource(GroupResource groupResource){
		if(groupResource==null || groupResource.getResourceId()==null || groupResource.getGroupId()==null){
			throw new BizException("$==>field Resource type"); 
		}
		if(groupResource.getIsEnableInGroup()==null){
			groupResource.setIsEnableInGroup(false);
		}
		
		Map<String, Object> par = new HashMap<>();
		par.put("groupId", groupResource.getGroupId());
		par.put("resourceId", groupResource.getResourceId());
		GroupResource gr = groupResourceDao.getBy(par);
		if(groupResource.getIsEnableInGroup()&&gr==null){
			groupResourceDao.insert(groupResource);
			return;
		}
		
		if(!groupResource.getIsEnableInGroup()&&gr!=null){
			groupResourceDao.deleteById(gr.getId());
			return;
		}
	}
	
	public void setResources(List<GroupResource> groupResource){
		for (GroupResource groupResource2 : groupResource) {
			setResource(groupResource2);
		}
	}
	
	
	public List<GroupResource> getResourcesByGroupId(Long groupId){
		if(groupId == null){
			throw new BizException("$==>group id can/t be null");
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("groupId", groupId);
		List<GroupResource> groupResources = groupResourceDao.listBy(paramMap);
		for (GroupResource groupResource : groupResources) {
			groupResource.setIsEnableInGroup(true);
		}
		return groupResources;
	}

}
