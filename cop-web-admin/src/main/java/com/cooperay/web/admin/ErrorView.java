package com.cooperay.web.admin;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ErrorView extends VerticalLayout implements View {

	public ErrorView() {
		setMargin(true);
	}
	@Override
	public void enter(ViewChangeEvent event) {
		removeAllComponents();
		Label label = new Label("Opps page not found error");
		label.addStyleName(ValoTheme.LABEL_H1);
		addComponent(label);
		
	}

}
