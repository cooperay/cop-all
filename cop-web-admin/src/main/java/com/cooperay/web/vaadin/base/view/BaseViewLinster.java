package com.cooperay.web.vaadin.base.view;

public interface BaseViewLinster<T> {
	void add(T entry);
	
	void delete(Long id);
	
	void update(T entry);
	
	void page(Integer page,Integer rows);
}
