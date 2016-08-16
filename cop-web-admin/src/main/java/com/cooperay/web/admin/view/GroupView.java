package com.cooperay.web.admin.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.GroupResource;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.web.admin.presenter.GroupPresenter;
import com.cooperay.web.admin.vo.GroupVo;
import com.cooperay.web.vaadin.base.view.BaseView;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

public class GroupView extends BaseView<Group, GroupVo> {
	private static final Action SELECT_SUBITEM = new Action("选中所有子项");
	private static final Action UNSELECT_SUBITEM = new Action("取消所有子项");
	private List<Resource> resources = new ArrayList<>();
	private TreeTable treeTable;
	private Group currentGroup =null;
	
	public GroupView() {
		super(new Group(), "角色管理", new GroupVo() , new String[]{"id","groupName"});
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		super.setPage(pageBean);
		GroupPresenter subLister = (GroupPresenter)linster;
		subLister.initResourceTree();
		currentGroup = null;
	}
	
	@Override
	protected Component createToolBar() {
		Component component =  super.createToolBar();
		Button appaly = new Button("应用授权");
		appaly.setIcon(FontAwesome.CHECK);
		appaly.addStyleName(ValoTheme.BUTTON_SMALL);
		appaly.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		appaly.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				List<GroupResource> groupResources = new ArrayList<>();
				if(currentGroup==null){
					Notification.show("请选择角色", Type.WARNING_MESSAGE);
					return;
				}
				for (Resource r : resources) {
					GroupResource g = new GroupResource();
					g.setGroupId(currentGroup.getId());
					g.setResourceId(r.getId());
					g.setIsEnableInGroup(r.getIsEnableInGroup());
					groupResources.add(g); 
				}
				GroupPresenter subLister = (GroupPresenter)linster;
				subLister.applyResourceChange(groupResources);
				Notification.show("“"+currentGroup.getGroupName()+"“角色应用授权成功",Type.TRAY_NOTIFICATION);
			}
		});
		optTooBar.addComponent(appaly);
		return component;
	}
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 8, 2016
	* @描述：初始化空权限树
	* @参数： @param resources
	* @返回: void 
	* @异常:
	 */
	public void setResourceTree(List<Resource> resources){
		this.resources = resources;
		treeTable.removeAllItems();
		treeTable.addItems(resources);
		for (Resource resource : resources) {
			for (Resource parent : resources) {
				if(resource.getParentId().equals(parent.getId())){
					treeTable.setParent(resource, parent);
				}
			}
		}
		for (Object itemId: treeTable.getContainerDataSource()
                .getItemIds()) {
		treeTable.setCollapsed(itemId, false);
		
		// As we're at it, also disallow children from
		// the current leaves
		if (! treeTable.hasChildren(itemId))
			treeTable.setChildrenAllowed(itemId, false);
		}
	}
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 9, 2016
	* @描述：刷新选中组的对应权限
	* @参数： @param l
	* @返回: void 
	* @异常:
	 */
	public void refreshGroupResourceTree(List<GroupResource> l) {
		for (Resource r : resources) {
			r.setIsEnableInGroup(false);
			for (GroupResource groupResource : l) {
				if(groupResource.getResourceId().equals(r.getId())){
					r.setIsEnableInGroup(true);
				}
			}
		}
		setResourceTree(resources);
	}
	
	/**
	 *重写创建grid方法扩展权限树
	 */
	@Override
	protected Component createGrid() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth("100%");
		layout.setSpacing(true);
		
		Grid grid = (Grid)super.createGrid();
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setWidth("100%");
		grid.addSelectionListener(new SelectionEvent.SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				Set<Object> set = event.getSelected();
				Iterator<Object> iterator = set.iterator();
				currentGroup = null;
				if (iterator.hasNext()) {
					Group group = (Group) iterator.next();
					currentGroup = group;
					GroupPresenter subLister = (GroupPresenter)linster;
					subLister.selectedGroup(group.getId());
				}
			}
		});
		treeTable = createTreeTable();
		treeTable.setHeight(grid.getHeight(),Unit.PIXELS);
		/*treeTable.addActionHandler(new Action.Handler() {
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(action==SELECT_SUBITEM){
					//selectSubItem((Resource)target,true);
				}else if(action == UNSELECT_SUBITEM) {
					//selectSubItem((Resource)target,false);
				}
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				
				 if (target != null && treeTable.areChildrenAllowed(target)) {
					 // Context menu in an empty space -> add a new main category
	                    return new Action[] { SELECT_SUBITEM,UNSELECT_SUBITEM };
				 }
				 return null;
			}});*/
		layout.addComponent(grid);
		layout.addComponent(treeTable);
		layout.setExpandRatio(grid,0.6f);
		layout.setExpandRatio(treeTable, 0.4f);
		return layout;
	}
	
	
	private void selectSubItem(Resource resource,Boolean select){
		for (Resource r : resources) {
			if(r.getId().equals(resource.getId())){
				
				r.setIsEnableInGroup(select);
				if(treeTable.hasChildren(resource)){;
					Iterator iterator = treeTable.getChildren(resource).iterator();
					while(iterator.hasNext()){
						Resource resource2 = (Resource)iterator.next();
						selectSubItem(resource2, select);
					}
				}else{
					return;
				}
			}
		}
		
	}
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 9, 2016
	* @描述：创建权限树
	* @参数： @return
	* @返回: TreeTable 
	* @异常:
	 */
	private TreeTable createTreeTable(){
		treeTable = new TreeTable();
		treeTable.setWidth("100%");
		treeTable.setSelectable(true);
		BeanItemContainer<Resource> container = new BeanItemContainer(Resource.class,resources);
		treeTable.setContainerDataSource(container);
		treeTable.setVisibleColumns("isEnableInGroup","id","url");
		treeTable.setColumnHeader("id", "资源编号");
		treeTable.setColumnHeader("url", "路径");
		treeTable.setColumnHeader("isEnableInGroup", "权限");
		treeTable.setTableFieldFactory(new TableFieldFactory() {
			@Override
			public com.vaadin.ui.Field<?> createField(Container container, Object itemId, Object propertyId,
					Component uiContext) {
				if(propertyId.equals("isEnableInGroup")){
					CheckBox checkBox = new CheckBox();
					Resource g = (Resource)itemId;
					checkBox.setCaption(g.getResourceName());
					return checkBox;
				}
				return null;
			}
		});
		treeTable.setEditable(true);
		return treeTable;
	}
	
	
}
