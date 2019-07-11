package org.activiti.event;

import org.activiti.engine.TaskService;
import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EventLogTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"test-event.bpmn20.xml"})
    public void testEventLog(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("testEvent");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());

        List<EventLogEntry> eventLogEntries = activitiRule.getManagementService().getEventLogEntriesByProcessInstanceId(processInstance.getId());
        for (EventLogEntry eventLogEntry : eventLogEntries) {
            LOGGER.info("eventLogEntry.type = [{}],eventLogEntry.data = [{}]",eventLogEntry.getType(),new String(eventLogEntry.getData()));
        }
    }

}
