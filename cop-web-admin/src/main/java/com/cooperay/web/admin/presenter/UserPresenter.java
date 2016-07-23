package com.cooperay.web.admin.presenter;

import com.cooperay.facade.admin.entity.User;
import com.cooperay.web.admin.vo.UserVo;
import com.cooperay.web.vaadin.base.presenter.BasePresenter;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;

public class UserPresenter extends BasePresenter<User, UserVo> {

	public UserPresenter(BaseFacadeInterface<User> service, BaseView<User, UserVo> view) {
		super(service, view);
	}

}
