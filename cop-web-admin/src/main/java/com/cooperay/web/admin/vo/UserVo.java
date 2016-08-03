package com.cooperay.web.admin.vo;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.cooperay.facade.admin.entity.User.State;
import com.cooperay.web.admin.component.DeptSelecter;
import com.cooperay.web.admin.component.UserSelecter;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;
import com.cooperay.web.vaadin.base.ann.HideInGrid;
import com.cooperay.web.vaadin.base.ann.SeachProperty;
import com.cooperay.web.vaadin.base.ann.ServerField;
import com.cooperay.web.vaadin.base.enums.BooleanEnum;

public class UserVo {

	
	@FormProperty(text="编码",readonly = true)
	@HideInForm
	private Long id;

	@SeachProperty(text="用户名")
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
	@HideInForm(exclude={"update"})
	private State state;
	
	@SeachProperty(text="--是否启用--")
	@HideInGrid
	@HideInForm
	private BooleanEnum isEnable2;

	@FormProperty(text="Bar测试")
	private Double age;
	
	@NotNull
	@FormProperty(text="用户组",fieldClass=DeptSelecter.class)
	private Long deptId;
	
	//@HideInForm(exclude={"update"})
	@FormProperty(text="是否启用")
	private Boolean isEnable;
	
	

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Double getAge() {
		return age;
	}

	public void setAge(Double age) {
		this.age = age;
	}

	public BooleanEnum getIsEnable2() {
		return isEnable2;
	}

	public void setIsEnable2(BooleanEnum isEnable2) {
		this.isEnable2 = isEnable2;
	}

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
