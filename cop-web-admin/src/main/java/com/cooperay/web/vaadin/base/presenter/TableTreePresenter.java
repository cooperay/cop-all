package com.cooperay.web.vaadin.base.presenter;

import com.cooperay.common.page.PageBean;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;
import com.cooperay.web.vaadin.base.view.TableTreeView;

public class TableTreePresenter<T,V> extends BasePresenter<T, V> {

	public TableTreePresenter(BaseFacadeInterface<T> service, BaseView<T, V> view) {
		super(service, view);
	}
	
	

}
