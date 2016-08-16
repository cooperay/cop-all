package com.cooperay.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.rpc.RpcException;
import com.cooperay.facade.admin.entity.User;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

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
    
    @Autowired
    private LoginView loginView;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	//setContent(loginView);
    	//setContent(mainView);
    	loginView.addLoginListener(new LoginView.LoginListener() {
			@Override
			public Boolean onLogin(String userName, String passWord) {
				VaadinSession.getCurrent().setAttribute(User.class.getName(),new User());
				updateContent();
				return true;
			}
		});
    	mainView.addLoginListener(new MainView.LogOutListener() {
			@Override
			public void onLogOut() {
				VaadinSession.getCurrent().setAttribute(User.class.getName(),null);
				//getPage().reload();
				updateContent();
			}
		});
    	loginView.setHeight(Page.getCurrent().getBrowserWindowHeight()-2,Unit.PIXELS);
    	mainView.setHeight(Page.getCurrent().getBrowserWindowHeight()-2,Unit.PIXELS);
    	updateContent();
		//mainView.setNav();
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
		
		Page.getCurrent().addBrowserWindowResizeListener(new Page.BrowserWindowResizeListener() {
			@Override
			public void browserWindowResized(BrowserWindowResizeEvent event) {
				mainView.setHeight(Page.getCurrent().getBrowserWindowHeight()-2,Unit.PIXELS);
			}
		});
		VaadinServletRequest request = (VaadinServletRequest)vaadinRequest;
		System.out.println("-----------"+request.getSession());
		//VaadinService.getCurrent()
    }
    
    public void updateContent(){
    	User user = (User) VaadinSession.getCurrent().getAttribute(
                User.class.getName());
        if (user != null) {
            // Authenticated user
            setContent(mainView);
            mainView.setNav();
        } else {
            setContent(loginView);
        }
    }

    
   
    
   // @VaadinServletConfiguration(ui = AdminUI.class, productionMode = false)
}
