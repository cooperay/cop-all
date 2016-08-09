package com.cooperay.web.admin.vo;

import javax.validation.constraints.NotNull;

import com.cooperay.facade.admin.enums.ResourceType;
import com.cooperay.web.admin.component.ResourceSelecter;
import com.cooperay.web.vaadin.base.ann.FormProperty;
import com.cooperay.web.vaadin.base.ann.HideInForm;

public class ResourceVo {

	@FormProperty(text="编码",readonly = true)
	@HideInForm
	private Long id;
	
	@FormProperty(text = "资源名称")
	@NotNull
	private String resourceName;
	@FormProperty(text="上级名称",fieldClass=ResourceSelecter.class)
    private Long parentId;
	@NotNull
	@FormProperty(text="类型")
    private ResourceType type;
	@FormProperty(text="资源路径")
    private String url;
	@FormProperty(text="图标")
    private String icon;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
    
    
    
    
	
}
