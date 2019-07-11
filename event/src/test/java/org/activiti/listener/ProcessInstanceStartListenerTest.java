package org.activiti.listener;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProcessInstanceStartListenerTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_listener.cfg.xml");

    @Test
    @Deployment(resources = {"test-listener.bpmn20.xml"})
    public void testListener(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("testListener");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
    }

}