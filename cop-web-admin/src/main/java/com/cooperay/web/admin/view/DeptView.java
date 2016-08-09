package com.cooperay.web.admin.view;

import java.util.List;

import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.web.admin.vo.DeptVo;
import com.cooperay.web.vaadin.base.view.BaseView;
import com.cooperay.web.vaadin.base.view.TableTreeView;
import com.vaadin.event.Action;

public class DeptView extends TableTreeView<Department, DeptVo> {
	private static final Action ADD_ITEM = new Action("新增子项");
	private static final Action REMOVE_ITEM = new Action("删除项");
	private static final Action ADD_CATEGORY = new Action("新增顶级");
	
	private Department temp;
	public DeptView() {
		super(new Department(), "部门管理", new DeptVo(),new Object[]{"id","deptName"});
		treeTable.addActionHandler(new Action.Handler() {
			@Override
			public void handleAction(Action action, Object sender, Object target) {
				if(action==ADD_ITEM){
					Department department = (Department)target;
					if(department!=null){
						Department d = new Department();
						d.setParentId(department.getId());
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
		treeTable.setVisibleColumns("deptName","id","createTime");
		//treeTable.setPageLength(5);
		//treeTable.setCurrentPageFirstItemIndex(5);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void create() {
		if(entry!=null){
			Class<Department> class1 = (Class<Department>)entry.getClass();
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
