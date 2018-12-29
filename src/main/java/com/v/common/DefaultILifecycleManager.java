package com.v.common;/**
 * Created by VLoye on 2018/12/19.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname DefaultILifecycleManager
 * @Description 默认生命周期管理空实现
 **/
public class DefaultILifecycleManager extends AbstractILifecycleManager {
    private static final Logger logger = LoggerFactory.getLogger(DefaultILifecycleManager.class);
    private int state;
    public boolean initialized = false;
    @Override
    public void init() {
        logger.info("[{}] is initializing",this.getClass().getName());
    }

    @Override
    public void start() {
        logger.info("[{}] is starting",this.getClass().getName());
    }

    @Override
    public void close() {
        logger.info("[{}] is closing",this.getClass().getName());
    }

    @Override
    public void setLifeState(LifeState lifeState) {
        this.state = lifeState.getState();
    }
}
