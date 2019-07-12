package org.activiti.interceptor;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomPostCommandInterceptor extends AbstractCommandInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomPostCommandInterceptor.class);
    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        long start = System.currentTimeMillis();
        try {
            return this.getNext().execute(config,command);
        } finally {
            long duration = System.currentTimeMillis() - start;
            LOGGER.info("执行花费 [{}] 毫秒",duration);
        }
    }
}
