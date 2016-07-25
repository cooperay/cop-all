package com.cooperay.web.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.ReadOnly;

public class UserVo {

	
	@FormProperty(text="编码",readonly = true)
	private Long id;

	@FormProperty(text = "用户名")
	@NotNull
	private String userName;

	@FormProperty(text = "密码")
	@NotNull
	private String password;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
