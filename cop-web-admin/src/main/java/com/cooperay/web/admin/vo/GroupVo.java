package com.cooperay.web.admin.vo;

import javax.validation.constraints.NotNull;

import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;

public class GroupVo {

	@FormProperty(text="编码",readonly = true)
	@HideInForm
	private Long id;
	@FormProperty(text = "角色名称")
	@NotNull
    private String groupName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

    
	
	
}
