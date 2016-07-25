package com.cooperay.web.vaadin.base.service;

import com.cooperay.common.page.PageBean;

public interface BaseFacadeInterface <T>{

	void add(T t);
	
	PageBean page(Integer page,Integer row);
	
	
	void delete(T entry);
	
	void update(T entry);
	
	void list();
	
	
}
