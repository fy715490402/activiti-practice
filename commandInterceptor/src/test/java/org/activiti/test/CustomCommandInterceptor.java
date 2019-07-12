package org.activiti.test;

import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

public class CustomCommandInterceptor {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"test-event.bpmn20.xml"})
    public void testCustomCommandInterceptor(){
        
    }

}
