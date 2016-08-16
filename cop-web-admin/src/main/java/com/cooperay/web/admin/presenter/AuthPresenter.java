package com.cooperay.web.admin.presenter;

import java.util.List;

import com.alibaba.dubbo.common.utils.Log;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.DeptGroup;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;
import com.cooperay.web.admin.service.AuthService;
import com.cooperay.web.admin.view.AuthView;
import com.cooperay.web.admin.vo.DeptVo;
import com.cooperay.web.vaadin.base.presenter.BasePresenter;
import com.cooperay.web.vaadin.base.presenter.TableTreePresenter;
import com.cooperay.web.vaadin.base.service.BaseFacadeInterface;
import com.cooperay.web.vaadin.base.view.BaseView;

public class AuthPresenter extends TableTreePresenter<Department, DeptVo> {

	AuthService authService;
	AuthView authView;
	
	public AuthPresenter(BaseFacadeInterface<Department> service, BaseView<Department, DeptVo> view) {
		super(service, view);
		this.authService = (AuthService)service;
		this.authView = (AuthView)view;
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 12, 2016
	* @描述：选中部门 
	* @参数： @param deptId
	* @返回: void 
	* @异常:
	 */
	public void selectDept(Long deptId){
		List<User> users = authService.getUsersByDeptId(deptId);
		List<Group> groups = authService.getGroupsByDeptId(deptId);
		authView.setUserGrid(users);
		authView.setGroupGrid(groups);
	}
	
	public void selectUser(Long userId){
		List<Group> groups = authService.getGroupsByUserId(userId);
		authView.setGroupGrid(groups);
	}
	
	/**
	 *
	* @作者：李阳
	* @时间：Aug 16, 2016
	* @描述：部门授权
	* @参数： 
	* @返回: void 
	* @异常:
	 */
	public void deptAuth(List<DeptGroup> deptGroups){
		authService.setDeptGroups(deptGroups);
	}
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 16, 2016
	* @描述：用户授权
	* @参数： 
	* @返回: void 
	* @异常:
	 */
	public void userAuth(List<UserGroup> userGroups){
		authService.setUserGroups(userGroups);
	}
	
	
	
}
