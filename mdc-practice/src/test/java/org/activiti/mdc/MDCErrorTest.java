package org.activiti.mdc;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.logging.LogMDC;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author fy
 * @version 1.0
 * @date 2019/7/10
 */
public class MDCErrorTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_debug.cfg.xml");

    @Test

    @Deployment(resources = {"MDCError.bpmn20.xml"})
    public void testMDCError(){
        LogMDC.setMDCEnabled(true);
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        runtimeService.startProcessInstanceByKey("MDCError");
    }

}
