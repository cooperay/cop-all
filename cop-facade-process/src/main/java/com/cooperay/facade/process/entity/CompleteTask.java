package com.cooperay.facade.process.entity;

import java.util.Map;

public class CompleteTask {
	private String taskId;
	private String comment;
	private Map<String, Object> vars;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Map<String, Object> getVars() {
		return vars;
	}
	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}
	
}
