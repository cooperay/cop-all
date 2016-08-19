package com.cooperay.facade.process.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.cooperay.facade.process.entity.CompleteTask;
import com.cooperay.facade.process.entity.JsonComment;
import com.cooperay.facade.process.entity.JsonProcessDefinition;
import com.cooperay.facade.process.entity.JsonTask;
import com.cooperay.facade.process.entity.WorkFlowDeploy;



/**
 * 
 * @author liyang
 * Activiti 工作流引擎服务对象
 *
 */
public interface WorkFlowFacade {
	
void startProcess(String key,String bKey,Map<String, Object> vars);
	
	void startProcessAndCompleteFirst(String key,String bKey,Map<String, Object> vars);
	
	void deploy(WorkFlowDeploy deploy);
	
	void deploy(ZipInputStream inputStream,String deployName);

	List<JsonProcessDefinition> list();

	int remove(String id);

	InputStream viewImage(String id,String resourceName);


	void drowCurrentTaskImage(OutputStream imageStream,String taskId);
	
	void drowCurrentTaskImageByBkey(OutputStream imageStream,String bKey);

	void complete(CompleteTask cTask);

	List<JsonTask> selfTaskList(String user,Integer pageNO,Integer rows);
	//根据taskId 得到业务对象id
	String getObjId(String taskId);

	List<JsonTask> selfTaskHisList(String userName,Integer pageNO,Integer rows);
	
	//ProcessDefinition getPdById(String id);

	List<String> getFormOutGoing(String taskId);
	
	List<JsonComment> getProcessInstanceCommentByTaskId(String taskId);

	Object getInputUser(String taskId);

	Long selfTaskCount(String userName);

	Long selfTaskHisCount(String userName);
	
	JsonTask getJsonTaskById(String taskId);

	Long groupTaskCount(String groupName);

	List<JsonTask> groupTask(String groupName);

	Integer claim(String taskId,String userCode);
}
