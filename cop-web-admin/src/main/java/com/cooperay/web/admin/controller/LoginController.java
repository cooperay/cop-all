package com.cooperay.web.admin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value = "login" , method = RequestMethod.POST)
	public void login(@RequestParam String username, @RequestParam String passwd,HttpServletRequest request,HttpServletResponse response){
		request.setAttribute("isauth","");
		try {
			request.getSession().setAttribute("test", "测试session");
			response.sendRedirect("vaadin");
			if("admin".equals(username)){
			}else {
				request.setAttribute("message","用户名或秘密错误");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response){
		try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
