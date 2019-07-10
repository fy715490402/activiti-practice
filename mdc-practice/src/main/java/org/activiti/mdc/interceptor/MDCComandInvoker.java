package org.activiti.mdc.interceptor;

import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fy
 * @version 1.0
 * @date 2019/7/10
 */
public class MDCComandInvoker extends DebugCommandInvoker {

    private static final Logger logger = LoggerFactory.getLogger(MDCComandInvoker.class);

    @Override
    public void executeOperation(Runnable runnable) {
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation) runnable;
            if (operation.getExecution() != null) {
                LogMDC.putMDCExecution(operation.getExecution());
            }
        }
        super.executeOperation(runnable);
    }

}
