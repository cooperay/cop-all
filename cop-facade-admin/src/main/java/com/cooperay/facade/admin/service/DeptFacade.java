package com.cooperay.facade.admin.service;

import java.util.List;

import com.cooperay.common.facade.BaseFacade;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.DeptGroup;

public interface DeptFacade extends BaseFacade<Department> {

	public void setGroup(DeptGroup deptGroup);
	
	public void setGroups(List<DeptGroup> deptGroups);
}
