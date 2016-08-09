package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;
import com.cooperay.facade.admin.enums.ResourceType;

public class Resource extends BaseEntity {

    private String resourceName;

    private ResourceType type;

    private String url;

    private String icon;

    private Long parentId;
    
    private Boolean isEnableInGroup;
    

    public Boolean getIsEnableInGroup() {
		return isEnableInGroup;
	}

	public void setIsEnableInGroup(Boolean isEnableInGroup) {
		this.isEnableInGroup = isEnableInGroup;
	}

	public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type ;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}