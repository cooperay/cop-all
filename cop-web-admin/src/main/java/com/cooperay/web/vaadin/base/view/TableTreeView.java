package com.cooperay.web.vaadin.base.view;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

import com.cooperay.common.entity.BaseEntity;
import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Department;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.Action;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TreeTable;

public class TableTreeView<T,V> extends BaseView<T, V> {

	protected TreeTable treeTable;
	
	Label info = new Label();
	
	public TableTreeView(T t, String caption, V vo, Object[] colOrder) {
		super(t, caption, vo, colOrder);
	}

	
	@Override
	protected Component createToolBar() {
		// TODO Auto-generated method stub
		return super.createToolBar();
	}
	
	@Override
	public void setPage(PageBean pageBean) {
		// TODO Auto-generated method stub
		//super.setPage(pageBean);
		//treeTable.addItem(itemId)
		treeTable.removeAllItems();
		this.pageBean = pageBean;
		List<T> l = (List)pageBean.getRecordList();
		treeTable.addItems(l);
		try {
			for (T t : l) {
				PropertyDescriptor pd2;
					pd2 = new PropertyDescriptor("parentId",t.getClass());
					Object pid = pd2.getReadMethod().invoke(t);
				if(pid==null || (Long)pid==0){
					continue;
				}
				for (T parent : l) {
					PropertyDescriptor pd = new PropertyDescriptor("id",t.getClass());
					Object id = pd.getReadMethod().invoke(parent);
					if(pid.equals(id)){
						treeTable.setParent(t, parent);
					}
				}
			}
		}  catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.debug("树结构排序失败");
			e.printStackTrace();
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

	@Override
	protected Component createGrid() {
		treeTable = new TreeTable();
		treeTable.setSizeFull();
		treeTable.setSelectable(true);
		treeTable.setHeight(Page.getCurrent().getBrowserWindowHeight()-240, Unit.PIXELS);
		BeanItemContainer<T> container = new BeanItemContainer(entry.getClass(),list);
		treeTable.setContainerDataSource(container);
		Field[] fields= vo.getClass().getDeclaredFields();
		for (Field voField : fields) {
			FormProperty formProperty = voField.getAnnotation(FormProperty.class);
			if(formProperty==null){
				continue;
			}
			treeTable.setColumnHeader(voField.getName(), formProperty.text());
			
		}
		treeTable.addValueChangeListener(new Property.ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				selectedList = new ArrayList<>();
				T t = (T)event.getProperty().getValue();
				if(t!=null){
					selectedList.add(t);
					entry = t;
				}
				System.out.println(selectedList);
			}
		});
		
		
		return treeTable;
	}
	
	@Override
	public void resizeAble(){
		Page.getCurrent().addBrowserWindowResizeListener(new Page.BrowserWindowResizeListener() {
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				if(mainComponent!=null){
					mainComponent.setHeight(Page.getCurrent().getBrowserWindowHeight()-240,Unit.PIXELS);
				}
			}
		});
	}
}
