package com.cooperay.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;

import com.cooperay.web.admin.presenter.GroupPresenter;
import com.cooperay.web.admin.service.GroupService;
import com.cooperay.web.admin.view.GroupView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;
@SpringView(name=GroupPage.NAME)
public class GroupPage extends VerticalLayout implements View {
	public static final String NAME = "groupPage";
	@Autowired
	private GroupService groupService;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		//addComponent(userSelecter);
		GroupView view = new GroupView();
		GroupPresenter presenter = new GroupPresenter(groupService, view);
		addComponent(presenter.getView());
		presenter.page(1, 5000);
	}
}
