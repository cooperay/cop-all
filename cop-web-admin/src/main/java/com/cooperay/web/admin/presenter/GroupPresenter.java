package com.cooperay.web.admin.presenter;


import java.util.List;

import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.web.admin.service.GroupService;
import com.cooperay.web.admin.view.GroupView;
import com.cooperay.web.admin.vo.GroupVo;
import com.cooperay.web.vaadin.base.presenter.BasePresenter;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;

public class GroupPresenter extends BasePresenter<Group, GroupVo> {

	GroupService groupService ;
	GroupView groupView  ;
	
	public GroupPresenter(BaseFacadeInterface<Group> service, BaseView<Group, GroupVo> view) {
		super(service, view);
		groupService = (GroupService)service;
		groupView = (GroupView)view;
		// TODO Auto-generated constructor stub
	}
	
	public void initResourceTree(){
		List<Resource> l = groupService.getAllResource();
		groupView.setResourceTree(l);
		
	}
	
	public void selectedGroup(Long groupId){
		List<GroupResource> resources = groupService.getResourcesByGroupId(groupId);
		groupView.refreshGroupResourceTree(resources);
	}
	
	public void changeGroupResource(GroupResource groupResource){
		groupService.setResource(groupResource);
	}

	public void applyResourceChange(List<GroupResource> groupResources){
		groupService.setResource(groupResources);
	}
	
	
	
	
}
