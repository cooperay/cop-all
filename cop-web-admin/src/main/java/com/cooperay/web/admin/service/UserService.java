package com.cooperay.web.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;

@Service
public class UserService implements BaseFacadeInterface<User> {

	@Autowired
	private UserFacade userFacade;
	
	@Override
	public void add(User t) {
		userFacade.create(t);
	}

	@Override
	public List<User> page(Integer page, Integer row) {
		// TODO Auto-generated method stub
		PageParam pageParam = new PageParam(page, row);
		PageBean pageBean = userFacade.listPage(pageParam, null);
		return userFacade.listBy(null);
	}

}
