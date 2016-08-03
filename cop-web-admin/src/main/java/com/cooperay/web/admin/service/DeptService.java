package com.cooperay.web.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.service.DeptFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;

@Service
public class DeptService implements BaseFacadeInterface<Department> {
	
	@Autowired
	private DeptFacade deptFacade;
	

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
		System.out.println(entry);
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

}
