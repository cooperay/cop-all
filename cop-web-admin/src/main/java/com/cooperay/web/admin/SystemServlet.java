package com.cooperay.web.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.spring.server.SpringVaadinServlet;

public class SystemServlet extends SpringVaadinServlet {
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
	/*	Object user = request.getSession().getAttribute("user");
		if(user==null){
			response.sendRedirect("index.jsp");
		}else{
			super.service(request, response);
		}*/
	}
	
}
