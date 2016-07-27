package com.cooperay.web.vaadin.component;

import java.io.File;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class DownloadWindow extends Window {
	
	public DownloadWindow(String fileName) {
		super("下载窗口");
		init("点击确定开始下载",fileName);
	}
	

	private void init(String content,String fileName){
		setWidth(250,Unit.PIXELS);
		setModal(true);
		center();
		setResizable(false);
		VerticalLayout layout = new VerticalLayout();
		HorizontalLayout buttonLayout = new HorizontalLayout();
		Label label = new Label(content);

		Button confim = new Button("确定");
		Button cancel = new Button("取消");

		layout.setMargin(true);
		layout.setSpacing(true);
		layout.addComponent(label);
		layout.addComponent(buttonLayout);
		buttonLayout.setSpacing(true);
		confim.addStyleName(ValoTheme.BUTTON_DANGER);
		Resource res = new FileResource(new File(fileName));
		FileDownloader fd = new FileDownloader(res);
		fd.extend(confim);
		confim.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				DownloadWindow.this.close();
			}
		});
		
		cancel.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				DownloadWindow.this.close();
			}
		});
		
		
		buttonLayout.addComponents(confim,cancel);
		setContent(layout);
	}
	
}
