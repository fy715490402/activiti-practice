package org.activiti.history;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryLevelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryLevelTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"test-history.bpmn20.xml"})
    public void testLevel(){

        //变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("var1","value1");
        variables.put("var2","value2");
        //启动流程实例
        LOGGER.info("启动流程实例...");
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testLevel",variables);
        LOGGER.info("流程实例启动完成,实例ID [{}]",processInstance.getId());

        List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).list();
        runtimeService.setVariable(executions.get(0).getId(),"var1","newValue1");

        LOGGER.info("查询任务...");
        TaskService taskService = activitiRule.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();
        for (Task task : tasks) {
            LOGGER.info("查询到任务[{}]",task.getName());
            taskService.complete(task.getId());
            LOGGER.info("完成任务[{}]",task.getName());
        }
        LOGGER.info("流程实例结束...");

        HistoryService historyService = activitiRule.getHistoryService();
        //历史流程实例
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().list();
        LOGGER.info("历史流程实例总数 [{}]",historicProcessInstances.size());
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            LOGGER.info("historicProcessInstance [{}] ",historicProcessInstance.getName());
        }

        //历史活动
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().list();
        LOGGER.info("历史活动总数 [{}]",historicActivityInstances.size());
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance [{}] ",historicActivityInstance.getActivityName());
        }

        //历史任务
        List<HistoricTaskInstance> taskInstances = historyService.createHistoricTaskInstanceQuery().list();
        LOGGER.info("任务总数 [{}]",taskInstances.size());
        for (HistoricTaskInstance taskInstance : taskInstances) {
            LOGGER.info("taskInstance [{}] ",taskInstance.getName());
        }

        //变量详情
        List<HistoricDetail> details = historyService.createHistoricDetailQuery().list();
        LOGGER.info("details [{}]",details.size());
        for (HistoricDetail detail : details) {
            LOGGER.info("detail [{}] ",detail);
        }

    }

}
