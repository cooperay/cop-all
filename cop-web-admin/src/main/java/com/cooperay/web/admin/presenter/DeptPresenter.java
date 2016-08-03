package com.cooperay.web.admin.presenter;

import com.cooperay.facade.admin.entity.Department;
import com.cooperay.web.admin.vo.DeptVo;
import com.cooperay.web.vaadin.base.presenter.BasePresenter;
import com.cooperay.web.vaadin.base.presenter.TableTreePresenter;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;

public class DeptPresenter extends TableTreePresenter<Department, DeptVo> {

	public DeptPresenter(BaseFacadeInterface<Department> service, BaseView<Department, DeptVo> view) {
		super(service, view);
		// TODO Auto-generated constructor stub
	}

}
