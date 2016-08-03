package com.cooperay.web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.server.SpringVaadinServlet;

public class SystemServlet extends SpringVaadinServlet {
	
	@Override
	protected void servletInitialized() throws ServletException {
		// TODO Auto-generated method stub
		super.servletInitialized();
		VaadinService.getCurrent().setSystemMessagesProvider(new SystemMessagesProvider() {
			@Override
			public SystemMessages getSystemMessages(SystemMessagesInfo systemMessagesInfo) {
				CustomizedSystemMessages customizedSystemMessages = new CustomizedSystemMessages();
				customizedSystemMessages.setSessionExpiredCaption("会话超时");
				customizedSystemMessages.setSessionExpiredMessage("长时间未操作会话已经超时,点击这里或者按ESC键继续");
				return customizedSystemMessages;
			}
		});
		/*VaadinService.getCurrent().addSessionInitListener(new SessionInitListener() {
			@Override
			public void sessionInit(SessionInitEvent event) throws ServiceException {
				
				
			}
		});*/
	}
	
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
		//System.out.println(VaadinSession.getCurrent());
	/*	Object user = request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect("index.jsp");
		}else{
			super.service(request, response);
		}*/
	}
}
