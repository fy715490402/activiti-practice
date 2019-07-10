package org.activiti.practice.approval;

import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fy
 * @version 1.0
 * @date 2019/7/8
 */
public class ApprovalMain {

    private static Logger logger = LoggerFactory.getLogger(ApprovalMain.class);

    public static void main(String[] args) throws ParseException {
        logger.info("application starting");
        //创建流程引擎
        ProcessEngine processEngine = getProcessEngine();
        //部署流程文件
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);
        //启动流程
        ProcessInstance processInstance = getProcessInstance(processEngine, processDefinition);
        //处理流程任务
        handleTask(processEngine, processInstance);
        processEngine.close();
        logger.info("application exit");
    }

    private static void handleTask(ProcessEngine processEngine, ProcessInstance processInstance) throws ParseException {
        logger.info("query user task...");
        TaskService taskService = processEngine.getTaskService();
        Scanner scanner = new Scanner(System.in);
        FormService formService = processEngine.getFormService();
        while (processInstance != null && !processInstance.isEnded()) {
            List<Task> tasks = taskService.createTaskQuery().list();
            logger.info("user task count [{}]", tasks.size());
            for (Task task : tasks) {
                logger.info("current user task [{}]", task.getName());
                TaskFormData taskFormData = formService.getTaskFormData(task.getId());
                List<FormProperty> formProperties = taskFormData.getFormProperties();
                Map<String, Object> variables = new HashMap<String, Object>();
                for (FormProperty formProperty : formProperties) {
                    if (StringFormType.class.isInstance(formProperty.getType())) {
                        System.out.println("请输入" + formProperty.getName() + ":");
                        String value = scanner.nextLine();
                        variables.put(formProperty.getId(), value);
                    } else if (DateFormType.class.isInstance(formProperty.getType())) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        System.out.println("请输入" + formProperty.getName() + "(格式:yyyy-MM-dd):");
                        Date date = simpleDateFormat.parse(scanner.nextLine());
                        variables.put(formProperty.getId(), date);
                    } else {
                        logger.warn("not support type [{}]", formProperty.getType());
                    }
                }
                taskService.complete(task.getId(), variables);
                logger.info("user task [{}] completed", task.getName());
            }
            processInstance = processEngine.getRuntimeService()
                    .createProcessInstanceQuery()
                    .processInstanceId(processInstance.getId())
                    .singleResult();
        }
        scanner.close();
    }

    private static ProcessInstance getProcessInstance(ProcessEngine processEngine, ProcessDefinition processDefinition) {
        logger.info("starting a process instance");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinition.getKey());
        logger.info("process instance successful activation,process instance id [{}]", processInstance.getId());
        return processInstance;
    }

    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        logger.info("deploy process definition starting");
        RepositoryService repositoryService = processEngine.getRepositoryService();

        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey("Level_2_Approval").list();
        if (definitions != null) {
            for (ProcessDefinition processDefinition : definitions) {
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
            }
        }

        Deployment deployment = repositoryService.createDeployment().addClasspathResource("level_Ⅱ_approval.bpmn20.xml").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();
        logger.info("deploy process definition completed, the new definition's name [{}], key [{}]", processDefinition.getName(), processDefinition.getKey());
        return processDefinition;
    }

    private static ProcessEngine getProcessEngine() {
        ProcessEngine processEngine = new StandaloneInMemProcessEngineConfiguration().buildProcessEngine();
        logger.info("Get Default Process Engine [{}]", processEngine.getName());
        return processEngine;
    }

}
