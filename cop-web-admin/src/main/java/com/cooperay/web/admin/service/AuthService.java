package com.cooperay.web.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.DeptGroup;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;
import com.cooperay.facade.admin.service.DeptFacade;
import com.cooperay.facade.admin.service.GroupFacade;
import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;

@Service
public class AuthService implements BaseFacadeInterface<Department> {
	
	@Autowired
	private DeptFacade deptFacade;
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private GroupFacade groupFacade;

	@Override
	public void add(Department t) {
		if(t.getParentId()==null){
			t.setParentId(0l);
		}
		deptFacade.create(t);
	}

	@Override
	public PageBean page(Integer page, Integer row) {
		PageParam pageParam = new PageParam(page, row);
		PageBean pageBean = deptFacade.listPage(pageParam, null);
		return pageBean;
	}

	@Override
	public void delete(Department entry) {
		if(entry!=null)
		deptFacade.deleteById(entry.getId());
		
	}

	@Override
	public void update(Department entry) {
		deptFacade.update(entry);
	}

	@Override
	public void list() {
		deptFacade.listBy(null);
	}
	
	public List<User> getUsersByDeptId(Long deptId){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("deptId", deptId);
		return userFacade.listBy(paramMap);
	}
	
	public List<Group> getGroupsByDeptId(Long deptId){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("deptId", deptId);
		return groupFacade.listBy(paramMap);
	}

	public List<Group> getGroupsByUserId(Long userId){
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("userId", userId);
		return groupFacade.listBy(paramMap);
	}
	
	public void setUserGroups(List<UserGroup> userGroups){
		userFacade.setGroups(userGroups);
	}
	
	public void setDeptGroups(List<DeptGroup> deptGroups){
		deptFacade.setGroups(deptGroups);
	}
}
