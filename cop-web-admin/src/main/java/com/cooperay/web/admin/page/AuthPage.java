package com.cooperay.web.admin.page;

import org.springframework.beans.factory.annotation.Autowired;

import com.cooperay.facade.admin.service.UserFacade;
import com.cooperay.web.admin.component.UserSelecter;
import com.cooperay.web.admin.presenter.AuthPresenter;
import com.cooperay.web.admin.presenter.DeptPresenter;
import com.cooperay.web.admin.presenter.UserPresenter;
import com.cooperay.web.admin.service.AuthService;
import com.cooperay.web.admin.service.DeptService;
import com.cooperay.web.admin.service.UserService;
import com.cooperay.web.admin.view.AuthView;
import com.cooperay.web.admin.view.DeptView;
import com.cooperay.web.admin.view.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = AuthPage.NAME)
public class AuthPage extends VerticalLayout implements View{
	public static final String NAME = "authPage";

	@Autowired
	private AuthService authService;
	
	
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		//addComponent(userSelecter);
		AuthView userView = new AuthView();
		AuthPresenter presenter = new AuthPresenter(authService, userView);
		addComponent(presenter.getView());
		presenter.page(1, 500000);
	}

}
