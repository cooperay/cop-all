package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class DeptGroup extends BaseEntity {
    private Long groupId;

    private Long deptId;
    
    private Boolean isEnable;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

    
    
}