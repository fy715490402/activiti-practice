package org.activiti.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessInstanceStartListener implements ActivitiEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessInstanceStartListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
        if (ActivitiEventType.PROCESS_STARTED == event.getType()){
            LOGGER.info("流程 [{}] 启动",event.getProcessInstanceId());
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

}
