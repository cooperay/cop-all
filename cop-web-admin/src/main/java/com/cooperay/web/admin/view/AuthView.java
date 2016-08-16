package com.cooperay.web.admin.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.DeptGroup;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.facade.admin.entity.User;
import com.cooperay.facade.admin.entity.UserGroup;
import com.cooperay.web.admin.component.GroupChooseWindow;
import com.cooperay.web.admin.presenter.AuthPresenter;
import com.cooperay.web.admin.presenter.GroupPresenter;
import com.cooperay.web.admin.vo.DeptVo;
import com.cooperay.web.vaadin.base.view.TableTreeView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.ValoTheme;

public class AuthView extends TableTreeView<Department, DeptVo> {

	private String authType = "dept";
	
	private List<User> userList;
	private List<Group> groupList;
	private Table userGrid;
	private Table groupGrid;
	private Panel groupPanel;
	
	private Department currentDepartment;
	private User currentUser;
	private Group currentGroup;
	private GroupChooseWindow groupChooseWindow;
	public AuthView() {
		super(new Department(), "部门管理", new DeptVo(), new Object[] { "id", "deptName" });
		initChooseWindow();
	}

	private void initChooseWindow(){
		groupChooseWindow = new GroupChooseWindow();
		groupChooseWindow.addChooseEventListener(new GroupChooseWindow.ChooseEventListener() {
			@Override
			public void OnChoose(List<Group> list) {
				AuthPresenter subLister = (AuthPresenter)linster;
				if("dept".equals(authType) && currentDepartment!=null){
					List<DeptGroup> deptGroups = new ArrayList<>();
					for(Group g1 : groupList){
						DeptGroup deptGroup = new DeptGroup();
						deptGroup.setDeptId(currentDepartment.getId());
						deptGroup.setGroupId(g1.getId());
						deptGroup.setIsEnable(false);
						deptGroups.add(deptGroup);
					}
					for (Group g : list) {
						DeptGroup deptGroup = new DeptGroup();
						deptGroup.setDeptId(currentDepartment.getId());
						deptGroup.setGroupId(g.getId());
						deptGroup.setIsEnable(true);
						deptGroups.add(deptGroup);
					}
					subLister.deptAuth(deptGroups);
					subLister.selectDept(currentDepartment.getId());
					Notification.show("部门：“"+currentDepartment.getDeptName()+"“授权成功",Type.TRAY_NOTIFICATION);
				}else if("user".equals(authType) && currentUser!=null) {
					List<UserGroup> userGroups = new ArrayList<>();
					for(Group g1 : groupList){
						UserGroup userGroup = new UserGroup();
						userGroup.setGroupId(g1.getId());
						userGroup.setUserId(currentUser.getId());
						userGroup.setEnable(false);
						userGroups.add(userGroup);
					}
					for (Group g : list) {
						UserGroup userGroup = new UserGroup();
						userGroup.setGroupId(g.getId());
						userGroup.setUserId(currentUser.getId());
						userGroup.setEnable(true);
						userGroups.add(userGroup);
					}
					subLister.userAuth(userGroups);
					subLister.selectUser(currentUser.getId());
					Notification.show("用户：“"+currentUser.getUserName()+"“授权成功",Type.TRAY_NOTIFICATION);
				}
			}
		});
	}
	
	@Override
	protected Component createToolBar() {
		Component component =  super.createToolBar();
		optTooBar.removeAllComponents();
		Button appaly = new Button("部门授权");
		appaly.setIcon(FontAwesome.PLUS);
		appaly.addStyleName(ValoTheme.BUTTON_SMALL);
		appaly.addStyleName(ValoTheme.BUTTON_PRIMARY);
		appaly.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(currentDepartment==null){
					Notification.show("请选择授权部门",Type.WARNING_MESSAGE);
					return;
				}
				selectDept();
				authType = "dept";
				String title = "为部门:“"+currentDepartment.getDeptName()+"“授权角色";
				groupChooseWindow.open(title, groupList);
			}
		});
		
		Button appaly2 = new Button("用户授权");
		appaly2.setIcon(FontAwesome.PLUS);
		appaly2.addStyleName(ValoTheme.BUTTON_SMALL);
		appaly2.addStyleName(ValoTheme.BUTTON_PRIMARY);
		appaly2.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(currentUser==null){
					Notification.show("请选择授权用户",Type.WARNING_MESSAGE);
					return;
				}
				authType ="user";
				String title = "为用户:“"+currentUser.getUserName()+"“授权角色";
				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("deptId",currentDepartment.getId());
				groupChooseWindow.open(title, groupList,paramMap);
			}
		});
		
		/*Button unBindGroup = new Button("取消授权");
		unBindGroup.setIcon(FontAwesome.REMOVE);
		unBindGroup.addStyleName(ValoTheme.BUTTON_SMALL);
		unBindGroup.addStyleName(ValoTheme.BUTTON_DANGER);
		unBindGroup.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(currentGroup==null){
					Notification.show("请选择要取消的角色",Type.WARNING_MESSAGE);
					return;
				}
				if(currentUser!=null){
					
					return;
				}
				if(currentGroup!=null){
					
					return;
				}
				Notification.show("没有指定的部门或用户",Type.WARNING_MESSAGE);
			}
		});*/
		
		optTooBar.addComponents(appaly,appaly2);
		return component;
	}
	
	
	@Override
	protected Component createGrid() {
		super.createGrid();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setSpacing(true);
		treeTable.setVisibleColumns("deptName", "id", "createTime");
		layout.addComponent(treeTable);
		treeTable.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				currentDepartment = null;
				currentUser = null;
				Department department = (Department)event.getProperty().getValue();
				currentDepartment = department;
				if(department==null){
					setUserGrid(null);
					setGroupGrid(null);
					groupPanel.setCaption("权限列表");
					return;
				}
				selectDept();
			}
		});
		
		Component userGrid = createUserGrid();
		layout.addComponent(userGrid);
		
		Component groupGrid = createGroupGrid();
		layout.addComponent(groupGrid);
		return layout;
	}

	protected Component createUserGrid(){
		BeanItemContainer<User> container = new BeanItemContainer(User.class,userList);
		userGrid = new Table();
		userGrid.setSelectable(true);
		userGrid.setContainerDataSource(container);
		userGrid.setSizeFull();
		userGrid.setVisibleColumns("userName","id");
		userGrid.setColumnHeader("userName", "用户名");
		userGrid.setColumnHeader("id", "编号");
		userGrid.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				currentUser = null;
				User u = (User)event.getProperty().getValue();
				currentUser = u;
				if(u==null){
					selectDept();
					return;
				}
				selectUser();
			}
		});
		return userGrid;
	}
	
	protected Component createGroupGrid(){
		groupPanel = new Panel("权限列表");
		groupPanel.setSizeFull();
		BeanItemContainer<Group> container = new BeanItemContainer(Group.class,groupList);
		groupGrid = new Table();
		groupGrid.setContainerDataSource(container);
		groupGrid.setSizeFull();
		groupGrid.setVisibleColumns("groupName","id");
		groupGrid.setColumnHeader("groupName", "角色名称");
		groupGrid.setColumnHeader("id", "编号");
		/*groupGrid.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				currentGroup = null;
				Group g = (Group)event.getProperty().getValue();
				if(g==null)
					return;
				currentGroup = g;
			}
		});*/
		groupPanel.setContent(groupGrid);
		return groupPanel;
	}
	
	private void selectDept(){
		AuthPresenter subListener  = (AuthPresenter)linster;
		subListener.selectDept(currentDepartment.getId());
		currentUser = null;
		groupPanel.setCaption("部门:“"+currentDepartment.getDeptName()+"”的权限列表");
	}
	
	private void selectUser(){
		AuthPresenter subListener  = (AuthPresenter)linster;
		subListener.selectUser(currentUser.getId());
		groupPanel.setCaption(currentDepartment.getDeptName()+"下属用户:“"+currentUser.getUserName()+"”的权限列表");
	}
	
	public void setUserGrid(List<User> users){
		BeanItemContainer<User> beanItemContainer = (BeanItemContainer)userGrid.getContainerDataSource();
		beanItemContainer.removeAllContainerFilters();
		beanItemContainer.removeAllItems();
		if(users==null||users.size()<=0)
			return;
		beanItemContainer.addAll(users);
	}
	
	public void setGroupGrid(List<Group> groups){
		groupList = groups;
		BeanItemContainer<Group> beanItemContainer = (BeanItemContainer)groupGrid.getContainerDataSource();
		beanItemContainer.removeAllContainerFilters();
		beanItemContainer.removeAllItems();
		if(groups==null||groups.size()<=0)
			return;
		beanItemContainer.addAll(groups);
	}
}
