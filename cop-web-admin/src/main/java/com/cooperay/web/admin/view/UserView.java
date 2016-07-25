package com.cooperay.web.admin.view;

import com.cooperay.facade.admin.entity.User;
import com.cooperay.web.admin.vo.UserVo;
import com.cooperay.web.vaadin.base.view.BaseView;
public class UserView extends BaseView<User, UserVo> {
	public UserView() {
		super(new User(), "用户管理", new UserVo(),new Object[]{"id","userName","password"});
		setPageRow(15);
	}
}
