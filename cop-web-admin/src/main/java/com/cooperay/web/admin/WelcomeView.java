package com.cooperay.web.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = WelcomeView.NAME)
public class WelcomeView extends VerticalLayout implements View {
	public WelcomeView() {
		setMargin(true);
	}

	public static final String NAME = "welcomeView";
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		addComponent(buildContent());
	}
	
	public Component buildContent(){
		HorizontalLayout layout = new HorizontalLayout();
		layout.setSizeFull();
		for(int i=0 ; i < 4 ;i++){
			Panel panel = new Panel("Info"+i);
			panel.addStyleName(ValoTheme.PANEL_WELL);
			panel.setWidth(400,Unit.PIXELS);
			panel.setHeight(200,Unit.PIXELS);
			layout.addComponent(panel);
		}
		System.out.println(VaadinSession.getCurrent());
		return layout;
	}

}
