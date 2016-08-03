package com.cooperay.web.admin.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.cooperay.web.admin.component.DeptSelecter;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;
import com.cooperay.web.vaadin.base.ann.HideInGrid;
import com.cooperay.web.vaadin.base.ann.SeachProperty;

public class DeptVo {

	
	@FormProperty(text="编码",readonly = true)
	@HideInForm
	private Long id;
	
	@SeachProperty(text="==部门名称==")
	@FormProperty(text = "部门名称")
	@NotNull
	private String deptName;

	@FormProperty(text = "上级部门",fieldClass=DeptSelecter.class)
	private Long parentId;
	
	@FormProperty(text="创建时间",readonly=true)
	private Date createTime;
	
	@FormProperty(text="版本",readonly=true)
	@HideInGrid
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	
	
	
	
	
}
