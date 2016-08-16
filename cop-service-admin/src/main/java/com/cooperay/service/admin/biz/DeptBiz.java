package com.cooperay.service.admin.biz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.core.biz.BaseBizImpl;
import com.cooperay.common.core.dao.BaseDao;
import com.cooperay.common.exceptions.BizException;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.DeptGroup;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.service.admin.dao.DeptDao;
import com.cooperay.service.admin.dao.DeptGroupDao;

@Service
public class DeptBiz extends BaseBizImpl<Department> {

	@Autowired
	private DeptDao deptDao;
	
	@Autowired
	private DeptGroupDao deptGroupDao;
	
	
	@Override
	protected BaseDao<Department> getDao() {
		// TODO Auto-generated method stub
		return deptDao;
	}

	public void setGroup(DeptGroup deptGroup){
		if(deptGroup==null || deptGroup.getDeptId()==null || deptGroup.getGroupId()==null){
			throw new BizException("$==>field Resource type"); 
		}
		if(deptGroup.getIsEnable()==null){
			deptGroup.setIsEnable(false);
		}
		
		Map<String, Object> par = new HashMap<>();
		par.put("groupId", deptGroup.getGroupId());
		par.put("deptId", deptGroup.getDeptId());
		DeptGroup dg = deptGroupDao.getBy(par);
		if(deptGroup.getIsEnable()&&dg==null){
			deptGroupDao.insert(deptGroup);
			return;
		}
		
		if(!deptGroup.getIsEnable()&&dg!=null){
			deptGroupDao.deleteById(dg.getId());
			return;
		}
	}
	
	public void setGroups(List<DeptGroup> deptGroups){
		for (DeptGroup deptGroup : deptGroups) {
			setGroup(deptGroup);
		}
	}
}
