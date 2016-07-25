package com.cooperay.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;

import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.web.admin.presenter.UserPresenter;
import com.cooperay.web.admin.service.UserService;
import com.cooperay.web.admin.view.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = UserPage.NAME)
public class UserPage extends VerticalLayout implements View{
	public static final String NAME = "userPage";

	@Autowired
	private UserService userService;
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		UserView userView = new UserView();
		UserPresenter presenter = new UserPresenter(userService, userView);
		addComponent(presenter.getView());
		presenter.page(1, 15);
	}

}
