package com.cooperay.web.admin.view;

import com.cooperay.facade.admin.entity.Department;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.web.admin.vo.ResourceVo;
import com.cooperay.web.vaadin.base.view.TableTreeView;
import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;

public class ResourceView extends TableTreeView<Resource, ResourceVo> {

	private static final Action ADD_ITEM = new Action("新增子项");
	private static final Action REMOVE_ITEM = new Action("删除项");
	private static final Action ADD_CATEGORY = new Action("新增顶级");
	
	private Resource temp;
	public ResourceView() {
		super(new Resource(), "菜单管理",new ResourceVo(), new String[]{"id","resourceName","type","url","icon"});
		// TODO Auto-generated constructor stub
		treeTable.addActionHandler(new Action.Handler() {
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(action==ADD_ITEM){
					Resource r = (Resource)target;
					if(r!=null){
						Resource d = new Resource();
						d.setParentId(r.getId());
						temp = d;
						create();
						temp =null;
					}
				}else if(action == ADD_CATEGORY){
					create();
				}else if(action == REMOVE_ITEM){
					delete();
				}
				
			}
			
			@Override
			public Action[] getActions(Object target, Object sender) {
				 if (target == null) {
	                    // Context menu in an empty space -> add a new main category
	                    return new Action[] { ADD_CATEGORY };
	 
	                } else {
	                    // Context menu for a category
	                    return new Action[] { ADD_ITEM,
	                            REMOVE_ITEM };
	 
	                }
			}
		});
		treeTable.setVisibleColumns("resourceName","id","type","createTime");
		//treeTable.setItemIcon(treeTa, FontAwesome.FOLDER);
		//treeTable.setItemIconPropertyId("icon");
	}


	@Override
	public void create() {
		if(entry!=null){
			Class<Resource> class1 = (Class<Resource>)entry.getClass();
			try {
				entry = class1.newInstance();
				if(temp!=null){
					entry=temp;
				}
				editWindow = createEditWindow(STATE_SAVE);
				getUI().addWindow(editWindow);
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("$==> 初始化实体类失败 "+entry.getClass());
			}
		}else{
			log.error("$==> 新增初始化失败 "+entry.getClass());
		}
	}
	
}
