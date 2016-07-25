package com.cooperay.web.admin;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.alibaba.dubbo.rpc.RpcException;
import com.cooperay.web.admin.page.UserPage;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.cooperay.web.admin.MyAppWidgetset")
@SpringUI
public class AdminUI extends UI {
	
    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
	
    @Autowired
    private MainView mainView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	setContent(mainView);
		mainView.setNav();
		UI.getCurrent().setErrorHandler(new ErrorHandler() {

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				String cause = "<b>The click failed because:</b><br/>";
				for (Throwable t = event.getThrowable(); t != null; t = t.getCause()){
					if (t.getCause() == null){ 
						// We're at final cause  根源异常
						//cause += t.getClass().getName() + "<br/>";
						t.printStackTrace();
						if(t instanceof RpcException){
							cause += "远程调用失败,请联系管理员进行处理.";
						}else{
							cause += "服务器内部错误";
						}
					}
					
				}
				// Display the error message in a custom fashion
				Notification notification = new Notification(cause,Type.ERROR_MESSAGE);
				notification.setHtmlContentAllowed(true);
				notification.show(Page.getCurrent());
			}
		});
    }

    
   
    
   // @VaadinServletConfiguration(ui = AdminUI.class, productionMode = false)
}
