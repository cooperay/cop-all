package com.cooperay.web.admin.component;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class IconSelecter extends Button{
	public IconSelecter() {
		init(); 
	}
	
	private void init(){
		Window selectWindow = new Window("选择图标");
		selectWindow.setModal(true);
		selectWindow.center();
		selectWindow.setWidth(200,Unit.PIXELS);
		selectWindow.setHeight(200,Unit.PIXELS);
		
		
		
		FontAwesome[] array = FontAwesome.values(); 
		for (FontAwesome fontAwesome : array) {
			Button label = new Button(fontAwesome.name());
			label.setIcon(fontAwesome);
			
		}
		
	}
}
