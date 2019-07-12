package org.avtiviti.practice;

import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class BackupDataBaseTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"timerStartEvent.bpmn20.xml"})
    public void execute() throws InterruptedException {
        Thread.sleep(1000*70);
    }
}