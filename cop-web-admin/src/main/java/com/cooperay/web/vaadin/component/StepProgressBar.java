package com.cooperay.web.vaadin.component;

import java.util.Iterator;

import javax.sound.sampled.LineUnavailableException;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;

public class StepProgressBar extends AbsoluteLayout {

	private ProgressBar progressBar;
	
	private HorizontalLayout horizontalLayout;
	
	public StepProgressBar() {
		setWidth("200px");
		//setHeight("50px");
		
		horizontalLayout = new HorizontalLayout();
		addComponent(horizontalLayout);
		horizontalLayout.setWidth("100%");
		
		StepLable label = new StepLable("1","审核中");
		StepLable label3 = new StepLable("2","提交");
		StepLable label4 = new StepLable("3","修改中");
		
		horizontalLayout.addComponents(label,label3,label4);
		horizontalLayout.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);
		horizontalLayout.setComponentAlignment(label3, Alignment.MIDDLE_RIGHT);
		horizontalLayout.setComponentAlignment(label4, Alignment.MIDDLE_RIGHT);
		
		progressBar = new ProgressBar();
		progressBar.setWidth("100%");
		progressBar.setValue(0.33f);
		
		
		addComponent(progressBar,"left:0px;top:13px");
		addComponent(horizontalLayout,"left:0px;top:0px");
		
	}
	
	
	public class StepLable extends Label{
		private String id;
		private String dis;
		public StepLable(String id,String dis) {
			super(FontAwesome.CIRCLE.getHtml(),ContentMode.HTML);
			this.id = id;
			this.dis = dis;
			init();
		}
		
		private void init(){
			addStyleName("step-label");
			setWidthUndefined();
			setDescription(dis);
			//setValue(getValue()+dis);
			
		}
		
		public void select(){
			this.addStyleName("step-label-select");
		}
	}

	
}
