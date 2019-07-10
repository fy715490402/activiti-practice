package org.activiti.mdc.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fy
 * @version 1.0
 * @date 2019/7/10
 */
public class ErrorTaskDelegate implements JavaDelegate {
    private static Logger logger = LoggerFactory.getLogger(ErrorTaskDelegate.class);
    public void execute(DelegateExecution execution) {
        logger.info("execute ServiceTask [{}]",this.getClass().getName());
        //throw new RuntimeException("error test");
    }
}
