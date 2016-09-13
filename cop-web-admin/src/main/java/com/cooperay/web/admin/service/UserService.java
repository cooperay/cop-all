package com.cooperay.web.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.page.PageBean;
import com.cooperay.common.page.PageParam;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;

@Service
public class UserService implements BaseFacadeInterface<User> {

	@Autowired
	private UserFacade userFacade;
	
	@Override
	public void add(User t) {
		System.out.println(t.getDeptId());
		userFacade.create(t);
	}

	@Override
	public PageBean page(Integer page, Integer row) {
		PageParam pageParam = new PageParam(page, row);
		PageBean pageBean = userFacade.listPage(pageParam, null);
		return pageBean;
	}

	@Override
	public void delete(User user) {
		if(user!=null)
		userFacade.deleteById(user.getId());
		
	}

	@Override
	public void update(User entry) {
		userFacade.update(entry);
	}

	@Override
	public void list() {
		
	}
	
	public List<Resource> getAllUserResources(Long userId){
		return userFacade.getAllUserResource(userId);
	}

}
