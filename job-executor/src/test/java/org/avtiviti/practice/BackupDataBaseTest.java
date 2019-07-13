package org.avtiviti.practice;

import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BackupDataBaseTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BackupDataBaseTest.class);
    
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"timerStartEvent.bpmn20.xml"})
    public void execute() throws InterruptedException {
        List<Job> jobs = activitiRule.getManagementService().createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            LOGGER.info("job = {} ,type = {}, 重试次数={}",job,job.getJobType(),job.getRetries());
        }
        Thread.sleep(1000*60);
    }
}