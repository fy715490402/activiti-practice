package org.avtiviti.practice;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackupDataBase implements JavaDelegate {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackupDataBase.class);
    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("开始备份数据库");
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.info("备份数据库完成");
    }
}
