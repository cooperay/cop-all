package com.cooperay.web.admin;

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.web.servlet.config.VelocityConfigurerBeanDefinitionParser;

import com.cooperay.facade.admin.entity.User;
import com.cooperay.web.vaadin.component.StepProgressBar;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.VaadinUriResolver;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;

@SpringComponent("loginView")
@UIScope
public class LoginView extends VerticalLayout implements View {
	public static final String NAME = "loginView";

	private LoginListener loginListener; 
	public LoginView() {
		init2();
	}
	
	private void init2(){
		removeAllComponents();
		Panel panel = new Panel("登陆信息");
		panel.setIcon(FontAwesome.USER);
		panel.setWidth("370px");
		panel.setHeight("285px");
		/*panel.setCaptionAsHtml(true);
		panel.setCaption("<font color='red'>登陆信息有误，登陆失败</font>");*/
		
		FormLayout layout = new FormLayout();
		layout.setWidth("95%");
		
		TextField username = new TextField();
		username.setInputPrompt("用户名");
		username.setSizeFull();
		
		PasswordField password = new PasswordField();
		password.setInputPrompt("密 码");
		password.setSizeFull();
		
		CheckBox reme = new CheckBox();
		reme.setCaption("记住我");
		reme.setSizeFull();
		
		Button login = new Button("登陆");
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setSizeFull();
		login.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Boolean boolean1 = loginListener.onLogin(username.getValue(), password.getValue());
				if(!boolean1){
					panel.setCaptionAsHtml(true);
					panel.setCaption("<font color='red'>登陆信息有误，登陆失败</font>");
				}
			}
		});
		
		layout.addComponents(username,password,reme,login);
		panel.setContent(layout);
		addComponent(panel);
		StepProgressBar stepProgressBar = new StepProgressBar();
		stepProgressBar.setWidth("500px");
		addComponent(stepProgressBar);
		setComponentAlignment(panel,Alignment.MIDDLE_CENTER);
	}
	
	
	/**
	 * 
	* @作者：李阳
	* @时间：Aug 16, 2016
	* @描述：使用html布局
	* @参数： 
	* @返回: void 
	* @异常:
	 */
	private void init(){
		removeAllComponents();
		
		CustomLayout sample = new CustomLayout("login_layout");
		// Create components and bind them to the location tags
		// in the custom layout.
		final TextField username = new TextField();
		username.setWidth(100.0f, Unit.PERCENTAGE);
		sample.addComponent(username, "username");

		final PasswordField password = new PasswordField();
		password.setWidth(100.0f, Unit.PERCENTAGE);
		sample.addComponent(password, "password");
		
		final Label notify = new Label();
		notify.setSizeUndefined();
		sample.addComponent(notify,"notify");

		final Button ok = new Button("登陆");
		ok.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				Boolean boolean1 = loginListener.onLogin(username.getValue(), password.getValue());
				if(!boolean1){
					notify.setCaption("用户名或密码错误");
				}
			}
		});
		sample.addComponent(ok, "okbutton");
		addComponent(sample);
		setComponentAlignment(sample,Alignment.MIDDLE_CENTER);
	}
	
	public interface LoginListener{
		Boolean onLogin(String userName,String passWord);
	}

	public void addLoginListener(LoginListener listener){
		this.loginListener = listener;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
