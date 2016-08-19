package com.cooperay.service.process.facade.impl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooperay.common.exceptions.BizException;
import com.cooperay.facade.process.entity.CompleteTask;
import com.cooperay.facade.process.entity.JsonComment;
import com.cooperay.facade.process.entity.JsonProcessDefinition;
import com.cooperay.facade.process.entity.JsonTask;
import com.cooperay.facade.process.entity.WorkFlowDeploy;
import com.cooperay.facade.process.enums.WorkFlowConstant;
import com.cooperay.facade.process.service.WorkFlowFacade;

@Service("workFlowFacade")
public class WorkFlowServiceImpl implements WorkFlowFacade {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;

	@Override
	public void deploy(WorkFlowDeploy deploy) {
		ZipInputStream input;
		input = new ZipInputStream(deploy.getZipInputStream());
		repositoryService.createDeployment()
		.name(deploy.getName())
		.category(deploy.getCategory())
		.tenantId(deploy.getTenantId())
		.addZipInputStream(input)
		.deploy();
	}
	
	@Override
	public void deploy(ZipInputStream inputStream,String deployName) {
		if(inputStream==null)
			throw new BizException("deploy input stream canot be null");
		repositoryService.createDeployment()
		.name(deployName)
		.addZipInputStream(inputStream)
		.deploy();
	}

	
	
	@Override
	public List<JsonProcessDefinition> list() {
		List<JsonProcessDefinition> myList = new ArrayList<JsonProcessDefinition>();
		List<ProcessDefinition> pList = repositoryService.createProcessDefinitionQuery().list();
		for (ProcessDefinition p : pList) {
			JsonProcessDefinition i = new JsonProcessDefinition();
			i.setId(p.getId());
			i.setKey(p.getKey());
			i.setName(p.getName());
			i.setDeploymentId(p.getDeploymentId());
			i.setDescription(p.getDescription());
			i.setResourceName(p.getResourceName());
			i.setDiagramResourceName(p.getDiagramResourceName());
			i.setVersion(p.getVersion());
			myList.add(i);
		}
		return myList;
	}

	@Override
	public int remove(String id) {
		repositoryService.deleteDeployment(id, true);
		return 0;

	}

	@Override
	public void complete(CompleteTask cTask) {
		if (cTask == null || cTask.getTaskId() == null || "".equals(cTask.getTaskId())) {
			throw new RuntimeException("没有指定办理任务的Id");
		}
		String taskId = cTask.getTaskId();
		String comment = cTask.getComment();
		Map<String, Object> vars = cTask.getVars();
		String outStr = (String) vars.get("outGoing");
		String processInstanceId = getProcessInstanceByTaskId(taskId).getProcessInstanceId();
		if (comment != null && !"".equals(comment)) {
			comment = outStr + ":" + comment;
			taskService.addComment(taskId, processInstanceId, comment);
		}
		if (vars != null && !vars.isEmpty()) {
			taskService.complete(taskId, vars);
		} else {
			taskService.complete(taskId);
		}
	}

	@Override
	public InputStream viewImage(String id, String resourceName) {
		return repositoryService.getResourceAsStream(id, resourceName);
	}

	@Override
	public void startProcess(String key, String bKey, Map<String, Object> vars) {
		runtimeService.startProcessInstanceByKey(key, bKey, vars);
	}

	@Override
	public void startProcessAndCompleteFirst(String key, String bKey, Map<String, Object> vars) {
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(key, bKey, vars);
		Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).active().singleResult();
		taskService.addComment(task.getId(), instance.getId(), "启动流程");
		taskService.complete(task.getId());
	}

	/**
	 * 
	 */
	/*
	 * @Override public void drowCurrentTaskImage(HttpServletResponse
	 * response,String taskId) { HistoricTaskInstance hti =
	 * historyService.createHistoricTaskInstanceQuery().taskId(taskId).
	 * singleResult(); ProcessDefinition pd =
	 * repositoryService.createProcessDefinitionQuery()
	 * .processDefinitionId(hti.getProcessDefinitionId()).singleResult();
	 * //获取流程图 String deploymentId = pd.getDeploymentId(); String imageName =
	 * pd.getDiagramResourceName(); InputStream input =
	 * repositoryService.getResourceAsStream(deploymentId, imageName);
	 * //获取当前活动任务 ProcessInstance instance =
	 * runtimeService.createProcessInstanceQuery()
	 * .processInstanceId(hti.getProcessInstanceId()) .singleResult(); String
	 * activityId = null; int corner = 22; //流程图红框圆角 //流程执行完毕 if(instance ==
	 * null){ HistoricActivityInstance act =
	 * historyService.createHistoricActivityInstanceQuery().processInstanceId(
	 * hti.getProcessInstanceId()).activityType("endEvent").singleResult();
	 * activityId = act.getActivityId(); corner = 30; }else{ activityId =
	 * instance.getActivityId(); }
	 * //获取流程定义信息，包括bpmn文件中的定义，普通通过query方式查询到的是数据库中的流程定义信息 不包括bpmn文件和内存中的信息
	 * ProcessDefinitionEntity pde =
	 * (ProcessDefinitionEntity)repositoryService.getProcessDefinition(pd.getId(
	 * ));
	 * 
	 * //获取当前活动任务坐标信息 ActivityImpl activity = pde.findActivity(activityId); try{
	 * BufferedImage buffImg = ImageIO.read(input); Graphics2D g2d =
	 * buffImg.createGraphics();
	 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); g2d.setColor(Color.RED); BasicStroke
	 * wideStroke = new BasicStroke(2.5f); g2d.setStroke(wideStroke);
	 * g2d.drawRoundRect(activity.getX(),activity.getY(), activity.getWidth(),
	 * activity.getHeight(), corner, corner); ServletOutputStream sos =
	 * response.getOutputStream(); ImageIO.write(buffImg, "png", sos);
	 * sos.close(); sos.flush(); }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * @Override public void drowCurrentTaskImageByBkey(HttpServletResponse
	 * response,String bKey) {
	 * 
	 * String pdId = null; String activityId = null; int corner = 22;
	 * ProcessInstance instance = runtimeService.createProcessInstanceQuery()
	 * .processInstanceBusinessKey(bKey) .singleResult(); if(instance==null){
	 * HistoricProcessInstance hpi =
	 * historyService.createHistoricProcessInstanceQuery().
	 * processInstanceBusinessKey(bKey).singleResult(); pdId =
	 * hpi.getProcessDefinitionId(); HistoricActivityInstance act =
	 * historyService.createHistoricActivityInstanceQuery().processInstanceId(
	 * hpi.getId()).activityType("endEvent").singleResult(); activityId =
	 * act.getActivityId(); corner = 30; }else{ pdId =
	 * instance.getProcessDefinitionId(); activityId = instance.getActivityId();
	 * }
	 * 
	 * ProcessDefinition pd =
	 * repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId
	 * ).singleResult();
	 * 
	 * //获取流程图 String deploymentId = pd.getDeploymentId(); String imageName =
	 * pd.getDiagramResourceName(); InputStream input =
	 * repositoryService.getResourceAsStream(deploymentId, imageName);
	 * //获取当前活动任务
	 * 
	 * 
	 * 
	 * //获取流程定义信息，包括bpmn文件中的定义，普通通过query方式查询到的是数据库中的流程定义信息 不包括bpmn文件和内存中的信息
	 * ProcessDefinitionEntity pde =
	 * (ProcessDefinitionEntity)repositoryService.getProcessDefinition(pd.getId(
	 * ));
	 * 
	 * //获取当前活动任务坐标信息 ActivityImpl activity = pde.findActivity(activityId); try{
	 * BufferedImage buffImg = ImageIO.read(input); Graphics2D g2d =
	 * buffImg.createGraphics();
	 * g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	 * RenderingHints.VALUE_ANTIALIAS_ON); g2d.setColor(Color.red); BasicStroke
	 * wideStroke = new BasicStroke(2.5f); g2d.setStroke(wideStroke);
	 * 
	 * g2d.drawRoundRect(activity.getX(),activity.getY(), activity.getWidth(),
	 * activity.getHeight(), corner, corner); ServletOutputStream sos =
	 * response.getOutputStream(); ImageIO.write(buffImg, "png", sos);
	 * sos.close(); sos.flush(); }catch(Exception e){ e.printStackTrace(); }
	 * 
	 * }
	 */
	@Override
	public List<JsonTask> selfTaskList(String user, Integer pageNO, Integer rows) {
		List<JsonTask> tasks = new ArrayList<JsonTask>();
		List<Task> t = taskService.createTaskQuery().taskAssignee(user).orderByTaskCreateTime().desc()
				.listPage((pageNO - 1) * rows, rows);
		for (Task task : t) {
			JsonTask jt = convertToJsonTask(task);
			jt.setFormPath(getPath(task));
			jt.setPriority(task.getPriority());
			jt.setInputUserName(
					(String) runtimeService.getVariable(task.getExecutionId(), WorkFlowConstant.INPUT_USER_NAME));
			tasks.add(jt);
		}
		return tasks;
	}

	private String getPath(Task task) {
		if (task == null || task.getId() == null || "".equals(task.getId())) {
			return null;
		}
		String objId = getObjId(task.getId());
		if (objId == null || "".equals(objId)) {
			return null;
		}
		if (task.getFormKey() == null || "".equals(task.getFormKey())) {
			return null;
		}
		return task.getFormKey() + objId;

	}

	@Override
	public List<JsonTask> selfTaskHisList(String userName, Integer pageNO, Integer rows) {
		List<JsonTask> tasks = new ArrayList<JsonTask>();
		List<HistoricTaskInstance> t = historyService.createHistoricTaskInstanceQuery().taskAssignee(userName)
				.finished().orderByTaskCreateTime().desc().listPage((pageNO - 1) * rows, rows);
		for (HistoricTaskInstance task : t) {
			JsonTask jt = new JsonTask();
			jt.setId(task.getId());
			jt.setAssignee(task.getAssignee());
			jt.setCreateTime(task.getCreateTime());
			jt.setName(task.getName());
			jt.setProceDefId(task.getProcessDefinitionId());
			jt.setDescription(task.getDescription());
			jt.setProceDefName(getPdById(task.getProcessDefinitionId()).getName());
			task.getDescription();
			tasks.add(jt);
		}
		return tasks;
	}

	public ActivityImpl getActivityImplByTaskId(String taskId) {
		HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(hti.getProcessInstanceId()).singleResult();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(hti.getProcessDefinitionId()).singleResult();
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(pd.getId());
		// 获取当前活动任务坐标信息
		ActivityImpl activity = pde.findActivity(instance.getActivityId());
		return activity;
	}

	@Override
	public List<String> getFormOutGoing(String taskId) {
		List<String> outGoingNames = new ArrayList<>();
		ActivityImpl activityImpl = getActivityImplByTaskId(taskId);
		List<PvmTransition> outGoings = activityImpl.getOutgoingTransitions();
		for (PvmTransition pvmTransition : outGoings) {
			String name = (String) pvmTransition.getProperty("name");
			if (name != null && !"".equals(name)) {
				outGoingNames.add(name);
			}
		}
		if (outGoingNames.isEmpty()) {
			outGoingNames.add(WorkFlowConstant.DEFAULT_OUT_NAME);
		}
		return outGoingNames;
	}

	public ProcessInstance getProcessInstanceByTaskId(String taskId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(getTaskById(taskId).getProcessInstanceId())
				.singleResult();
	}

	public Task getTaskById(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		return task;
	}

	/**
	 * 根据任务
	 * 
	 * @param taskId
	 * @return
	 */
	@Override
	public List<JsonComment> getProcessInstanceCommentByTaskId(String taskId) {
		String processInstanceId = getProcessInstanceByTaskId(taskId).getProcessInstanceId();
		List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
		List<JsonComment> cs = new ArrayList<>();
		for (Comment comment : comments) {
			JsonComment jc = new JsonComment();
			jc.setId(comment.getId());
			jc.setUserId(comment.getUserId());
			jc.setTime(comment.getTime());
			jc.setTaskId(comment.getTaskId());
			jc.setProcessInstanceId(comment.getProcessInstanceId());
			jc.setType(comment.getType());
			jc.setFullMessage(comment.getFullMessage());
			cs.add(jc);
		}
		return cs;
	}

	public ProcessDefinition getPdById(String id) {
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
		return pd;
	}

	@Override
	public String getObjId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId())
				.singleResult();
		String bKey = pi.getBusinessKey();
		if (bKey == null || "".equals(bKey)) {
			return null;
		}
		String objId = bKey.substring(bKey.indexOf(":") + 1, bKey.length());
		return objId;
	}

	@Override
	public Object getInputUser(String taskId) {
		String inputUser = (String) taskService.getVariable(taskId, "inputUser");
		return inputUser;
	}

	@Override
	public Long selfTaskCount(String userName) {
		return taskService.createTaskQuery().taskAssignee(userName).count();
	}

	@Override
	public Long selfTaskHisCount(String userName) {
		return historyService.createHistoricTaskInstanceQuery().taskAssignee(userName).finished().count();
	}

	@Override
	public JsonTask getJsonTaskById(String taskId) {
		Task task = getTaskById(taskId);
		return convertToJsonTask(task);
	}

	@Override
	public Long groupTaskCount(String groupName) {
		return taskService.createTaskQuery().taskCandidateGroup(groupName).count();
	}

	@Override
	public List<JsonTask> groupTask(String groupName) {
		List<Task> list = taskService.createTaskQuery().taskCandidateGroup(groupName).list();
		List<JsonTask> jts = new ArrayList<>();
		for (Task task : list) {
			JsonTask jt = convertToJsonTask(task);
			jt.setInputUserName(
					(String) runtimeService.getVariable(task.getExecutionId(), WorkFlowConstant.INPUT_USER_NAME));
			jts.add(jt);
		}
		return jts;
	}

	private JsonTask convertToJsonTask(Task task) {
		JsonTask jt = new JsonTask();
		jt.setId(task.getId());
		jt.setAssignee(task.getAssignee());
		jt.setCreateTime(task.getCreateTime());
		jt.setName(task.getName());
		jt.setProceDefId(task.getProcessDefinitionId());
		jt.setDescription(task.getDescription());
		jt.setProceDefName(getPdById(task.getProcessDefinitionId()).getName());
		jt.setTaskDefKey(task.getTaskDefinitionKey());
		return jt;
	}

	/**
	 * 李阳 认领任务，返回1认领成功，返回0认领失败
	 */
	@Override
	public Integer claim(String taskId, String userCode) {
		try {
			taskService.claim(taskId, userCode);
		} catch (ActivitiTaskAlreadyClaimedException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public void drowCurrentTaskImage(OutputStream imageStream, String taskId) {
		HistoricTaskInstance hti = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
								.processDefinitionId(hti.getProcessDefinitionId()).singleResult();
		//获取流程图
		String deploymentId = pd.getDeploymentId();
		String imageName = pd.getDiagramResourceName();
		InputStream input = repositoryService.getResourceAsStream(deploymentId, imageName);
		//获取当前活动任务
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
									.processInstanceId(hti.getProcessInstanceId())
									.singleResult();
		String activityId = null;
		int corner = 22;  //流程图红框圆角
		//流程执行完毕
		if(instance == null){
			HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(hti.getProcessInstanceId()).activityType("endEvent").singleResult();
			activityId = act.getActivityId();
			corner = 30;
		}else{
			activityId = instance.getActivityId();
		}
		//获取流程定义信息，包括bpmn文件中的定义，普通通过query方式查询到的是数据库中的流程定义信息 不包括bpmn文件和内存中的信息
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(pd.getId());
		
		//获取当前活动任务坐标信息
		ActivityImpl activity = pde.findActivity(activityId);
		try{
			BufferedImage buffImg = ImageIO.read(input);
			Graphics2D g2d = buffImg.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.RED);
			BasicStroke wideStroke = new BasicStroke(2.5f);
			g2d.setStroke(wideStroke);
			g2d.drawRoundRect(activity.getX(),activity.getY(), activity.getWidth(), activity.getHeight(), corner, corner);
			ImageIO.write(buffImg, "png", imageStream);
			imageStream.close();
			imageStream.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void drowCurrentTaskImageByBkey(OutputStream imageStream, String bKey) {
		String pdId = null;
		String activityId = null;
		int corner = 22;
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(bKey)
				.singleResult();
		if(instance==null){
			HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(bKey).singleResult();
			pdId = hpi.getProcessDefinitionId();
			HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery().processInstanceId(hpi.getId()).activityType("endEvent").singleResult();
			activityId = act.getActivityId();
			corner = 30;
		}else{
			pdId = instance.getProcessDefinitionId();
			activityId = instance.getActivityId();
		}
		
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdId).singleResult();
		
		//获取流程图
		String deploymentId = pd.getDeploymentId();
		String imageName = pd.getDiagramResourceName();
		InputStream input = repositoryService.getResourceAsStream(deploymentId, imageName);
		//获取当前活动任务
		
		//获取流程定义信息，包括bpmn文件中的定义，普通通过query方式查询到的是数据库中的流程定义信息 不包括bpmn文件和内存中的信息
		ProcessDefinitionEntity pde = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(pd.getId());
		
		//获取当前活动任务坐标信息
		ActivityImpl activity = pde.findActivity(activityId);
		try{
			BufferedImage buffImg = ImageIO.read(input);
			Graphics2D g2d = buffImg.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.red);
			BasicStroke wideStroke = new BasicStroke(2.5f);
			g2d.setStroke(wideStroke);
			
			g2d.drawRoundRect(activity.getX(),activity.getY(), activity.getWidth(), activity.getHeight(), corner, corner);
			ImageIO.write(buffImg, "png", imageStream);
			imageStream.close();
			imageStream.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}



	
}
