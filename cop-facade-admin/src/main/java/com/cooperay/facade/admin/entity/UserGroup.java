package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class UserGroup extends BaseEntity {

    private Long groupId;

    private Long userId;
    
    private Boolean enable;
    
    public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}