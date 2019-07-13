package org.avtiviti.practice;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * @version 1.0
 * @date 2019-07-12
 */

public class JobEventListener implements ActivitiEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobEventListener.class);

    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        String name = activitiEvent.getType().name();
        if (name.startsWith("TIMER") || name.startsWith("JOB")){
            LOGGER.info("监听到JOB事件 [{}] \t [{}]",activitiEvent.getType(),activitiEvent.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
