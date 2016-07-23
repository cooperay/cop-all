package com.cooperay.web.admin.vo;

import javax.validation.constraints.NotNull;

import com.cooperay.web.vaadin.base.ann.FormProperty;

public class UserVo {

	private Long id;

	@FormProperty(text = "用户名")
	@NotNull
	private String userName;

	@FormProperty(text = "用户名")
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
