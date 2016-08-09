package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class GroupResource extends BaseEntity {

    private Long groupId;

    private Long resourceId;
    
    private Boolean isEnableInGroup;
    
    

	public Boolean getIsEnableInGroup() {
		return isEnableInGroup;
	}

	public void setIsEnableInGroup(Boolean isEnableInGroup) {
		this.isEnableInGroup = isEnableInGroup;
	}

	public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}