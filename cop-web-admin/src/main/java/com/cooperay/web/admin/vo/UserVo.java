package com.cooperay.web.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.cooperay.facade.admin.entity.User.State;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;
import com.cooperay.web.vaadin.base.ann.HideInGrid;

public class UserVo {

	
	@FormProperty(text="编码",readonly = true)
	@HideInForm
	private Long id;

	@FormProperty(text = "用户名")
	@NotNull
	private String userName;

	@FormProperty(text = "密码")
	@NotNull
	private String password;
	
	@FormProperty(text="创建时间",readonly=true)
	private Date createTime;
	
	@FormProperty(text="版本",readonly=true)
	@HideInGrid
	private Integer version;
	
	@NotNull
	@FormProperty(text="状态")
	private State state;
	
	@FormProperty(text="是否启用")
	private Boolean isEnable;
	
	
	
	public Boolean getIsEnable() {
		return isEnable;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

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
