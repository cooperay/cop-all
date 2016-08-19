package com.cooperay.facade.process.entity;

import java.util.zip.ZipInputStream;

public class WorkFlowDeploy {
	private String name;
	private String category;
	private String tenantId;
	private ZipInputStream zipInputStream;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ZipInputStream getZipInputStream() {
		return zipInputStream;
	}
	public void setZipInputStream(ZipInputStream zipInputStream) {
		this.zipInputStream = zipInputStream;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	
	
	
	
}
