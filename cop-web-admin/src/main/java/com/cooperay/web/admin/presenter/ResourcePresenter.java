package com.cooperay.web.admin.presenter;

import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.web.admin.vo.ResourceVo;
import com.cooperay.web.vaadin.base.presenter.TableTreePresenter;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;

public class ResourcePresenter extends TableTreePresenter<Resource, ResourceVo> {

	public ResourcePresenter(BaseFacadeInterface<Resource> service, BaseView<Resource, ResourceVo> view) {
		super(service, view);
		// TODO Auto-generated constructor stub
	}

	
}
