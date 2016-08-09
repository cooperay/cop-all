package com.cooperay.web.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.service.ResourceFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;


@Service
public class ResourceService implements BaseFacadeInterface<Resource>{

	@Autowired
	private ResourceFacade resourceFacade;
	
	@Override
	public void add(Resource t) {
		if(t.getParentId()==null){
			t.setParentId(0l);
		}
		resourceFacade.create(t);
	}

	@Override
	public PageBean page(Integer page, Integer row) {
		PageParam pageParam = new PageParam(page, row);
		PageBean pageBean = resourceFacade.listPage(pageParam, null);
		return pageBean;
	}

	@Override
	public void delete(Resource entry) {
		System.out.println(entry);
		if(entry!=null)
		resourceFacade.deleteById(entry.getId());
	}

	@Override
	public void update(Resource entry) {
		resourceFacade.update(entry);
	}

	@Override
	public void list() {
		resourceFacade.listBy(null);
		
	}

}
