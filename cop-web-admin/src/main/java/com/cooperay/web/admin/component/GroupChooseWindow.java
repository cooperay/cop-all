package com.cooperay.web.admin.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cooperay.common.page.PageBean;
import com.cooperay.facade.admin.entity.Group;
import com.cooperay.facade.admin.entity.Resource;
import com.cooperay.web.admin.service.GroupService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class GroupChooseWindow extends Window{
	public enum ChooseModel{
		SINGLE,
		MUITIPLE;
	}
	
	private ChooseModel chooseModel;
	private ChooseEventListener chooseEventListener;
	private Grid grid;
	private Panel windowContent;
	
	private List<Group> list;
	private List<Group> selectedList;
	private GroupService groupService;
	
	
	public GroupChooseWindow() {
		this(ChooseModel.SINGLE);
	}
	
	public GroupChooseWindow(ChooseModel chooseModel) {
		//super("选择角色");
		this.chooseModel = chooseModel;
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		groupService = wac.getBean(GroupService.class);
		init();
	}
	
	public void addChooseEventListener(ChooseEventListener listener){
		this.chooseEventListener = listener;
	}
	
	public void init(){
		setWidth("800px");
		setHeight("520px");
		setModal(true);
		setDraggable(false);
		setClosable(true);
		center();
		windowContent = new Panel("选择角色");
		windowContent.setIcon(FontAwesome.GROUP);
		windowContent.setSizeFull();
		
		
		VerticalLayout content = new VerticalLayout();
		content.setMargin(true);
		content.setSpacing(true);
		content.addComponent(createGrid());
		windowContent.setContent(content);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		Button confim = new Button("确定");
		confim.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		confim.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				chooseEventListener.OnChoose(selectedList);
				UI.getCurrent().removeWindow(GroupChooseWindow.this);
			}
		});
		Button cancel = new Button("关闭");
		cancel.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().removeWindow(GroupChooseWindow.this);
			}
		});
		buttonLayout.addComponents(confim,cancel);
		content.addComponent(buttonLayout);
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setHeight("20px");
		progressBar.setWidth("200px");
		content.addComponent(progressBar);
		
		
		setContent(windowContent);
	}
	
	public Component createGrid(){
		BeanItemContainer<Group> container = new BeanItemContainer(Group.class,list);
		grid = new Grid(container);
		grid.setSizeFull();
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumnOrder("groupName","id");
		grid.getColumn("groupName").setHeaderCaption("角色名称");
		grid.getColumn("id").setHeaderCaption("编号");
		grid.addSelectionListener(new SelectionEvent.SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedList = new ArrayList<>();
				Set<Object> set = event.getSelected();
				Iterator<Object> iterator = set.iterator();
				while (iterator.hasNext()) {
					Group object = (Group) iterator.next();
					selectedList.add(object);
				}
			}
		});
		//设置列重新
		return grid;
	}
	
	public void loadGroups(Map<String, Object> paramMap){
		BeanItemContainer<Group> beanItemContainer = (BeanItemContainer)grid.getContainerDataSource();
		beanItemContainer.removeAllItems();
		list = groupService.listBy(paramMap);
		beanItemContainer.addAll(list);
		
	}
	
	
	public void open(String title,List<Group> selected){
		this.open(title, selected,null);
	}
	
	public void open(String title,List<Group> selected,Map<String, Object> paramMap){
		loadGroups(paramMap);
		windowContent.setCaption(title);
		selectedList=selected;
		grid.deselectAll();
		UI.getCurrent().addWindow(this);
		if(selected==null)
			return;
		for (Group group : selected) {
			for (Object obj : list) {
				Group group2 = (Group)obj;
				if(group2.getId().equals(group.getId())){
					grid.select(group2);
				}
			}
		}
	}
	
	
	

	public interface ChooseEventListener{
		void OnChoose(List<Group> list);
	}

}
