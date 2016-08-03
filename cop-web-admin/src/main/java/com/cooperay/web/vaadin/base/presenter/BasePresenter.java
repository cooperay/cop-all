package com.cooperay.web.vaadin.base.presenter;

import java.util.List;

import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;
import com.cooperay.web.vaadin.base.view.BaseViewLinster;



public class BasePresenter<T,V> implements BaseViewLinster<T> {

	protected BaseFacadeInterface<T> service;
	
	protected BaseView<T,V> view;
	
	public BasePresenter(BaseFacadeInterface<T> service,BaseView<T,V> view) {
		this.service = service;
		this.view = view;
		view.addListener(this);
	}

	public BaseView<T,V> getView() {
		return view;
	}


	@Override
	public void add(T entry) {
		service.add(entry);
	}

	@Override
	public void delete(T entry) {
		service.delete(entry);
	}

	@Override
	public void update(T entry) {
		service.update(entry);
	}

	@Override
	public void page(Integer page, Integer rows) {
		view.setPage(service.page(page,rows));
	}

	@Override
	public void list() {
		service.list();
	}
	
}
