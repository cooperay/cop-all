package com.cooperay.facade.process.entity;

import java.util.Date;

public class JsonTask {
	
	private String id;
	private String proceDefId;
	private String name;
	private String assignee;
	private Date createTime;
	private String description;
	private String proceDefName;
	private String formPath;
	private Integer priority;
	private String inputUserName; 
	private String taskDefKey;
	
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getInputUserName() {
		return inputUserName;
	}
	public void setInputUserName(String inputUserName) {
		this.inputUserName = inputUserName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getFormPath() {
		return formPath;
	}
	public void setFormPath(String formPath) {
		this.formPath = formPath;
	}
	public String getProceDefName() {
		return proceDefName;
	}
	public void setProceDefName(String proceDefName) {
		this.proceDefName = proceDefName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProceDefId() {
		return proceDefId;
	}
	public void setProceDefId(String proceDefId) {
		this.proceDefId = proceDefId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
