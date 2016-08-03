package com.cooperay.service.admin.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.service.admin.dao.DeptDao;

@Service
public class DeptBiz extends BaseBizImpl<Department> {

	@Autowired
	private DeptDao deptDao;
	
	@Override
	protected BaseDao<Department> getDao() {
		// TODO Auto-generated method stub
		return deptDao;
	}

}
