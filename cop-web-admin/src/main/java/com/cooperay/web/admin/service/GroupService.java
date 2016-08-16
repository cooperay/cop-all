package com.cooperay.web.admin.service;


import java.util.List;
import java.util.Map;

import org.aspectj.weaver.reflect.ReflectionBasedResolvedMemberImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.exceptions.BizException;
import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.service.GroupFacade;
import com.cooperay.facade.admin.service.ResourceFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;

@Service
public class GroupService implements BaseFacadeInterface<Group> {

	@Autowired
	private GroupFacade groupFacade;
	
	@Autowired
	private ResourceFacade resourceFacade;
	
	@Override
	public void add(Group t) {
		groupFacade.create(t);
	}

	
	public List<Resource> getAllResource(){
		return resourceFacade.listBy(null);
	}
	
	public void setResource(GroupResource groupResource){
		groupFacade.setResource(groupResource);
	}
	
	public List<GroupResource> getResourcesByGroupId(Long groupId){
		return groupFacade.getResourcesByGroupId(groupId);
	}
	
	
	@Override
	public PageBean page(Integer page, Integer row) {
		PageParam pageParam = new PageParam(page, row);
		PageBean pageBean = groupFacade.listPage(pageParam, null);
		return pageBean;
	}

	
	
	@Override
	public void delete(Group entry) {
		if(entry!=null){
			groupFacade.deleteById(entry.getId());
		}
	}

	@Override
	public void update(Group entry) {
		groupFacade.update(entry);
	}

	@Override
	public void list() {
		groupFacade.listBy(null);
		
	}
	
	public List<Group> listBy(Map<String, Object> paramMap){
		return groupFacade.listBy(paramMap);
	}

	public void setResource(List<GroupResource> groupResources) {
		groupFacade.setResources(groupResources);
	}

	
}
