package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class Department extends BaseEntity{

    private String deptName;

    private Long parentId;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

   
}