package com.cooperay.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;

import com.cooperay.web.admin.presenter.ResourcePresenter;
import com.cooperay.web.admin.service.ResourceService;
import com.cooperay.web.admin.view.ResourceView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name=ResourcePage.NAME)
public class ResourcePage extends VerticalLayout implements View{
	public static final String NAME = "resourcePage";

	@Autowired
	private ResourceService resourceService;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		//addComponent(userSelecter);
		ResourceView view = new ResourceView();
		ResourcePresenter presenter = new ResourcePresenter(resourceService, view);
		addComponent(presenter.getView());
		presenter.page(1, 5000);
	}
}
