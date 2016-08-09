package com.cooperay.facade.admin.service;

import java.util.List;

import com.cooperay.common.exceptions.BizException;
import com.cooperay.common.facade.BaseFacade;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;

public interface GroupFacade extends BaseFacade<Group>{

	public void setResource(GroupResource groupResource);
	
	public void setResources(List<GroupResource> groupResource);
	
	public List<GroupResource> getResourcesByGroupId(Long groupId);
	
}
