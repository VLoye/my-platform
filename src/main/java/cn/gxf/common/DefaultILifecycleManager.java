package cn.gxf.common;/**
 * Created by VLoye on 2018/12/19.
 */

import org.apache.catalina.LifecycleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author V
 * @Classname DefaultILifecycleManager
 * @Description 默认生命周期管理空实现
 **/
public class DefaultILifecycleManager extends AbstractILifecycleManager {
    private static final Logger logger = LoggerFactory.getLogger(DefaultILifecycleManager.class);
    private LifeState lifeState = LifeState.CREATEED;
    public boolean initialized = false;


    @Override
    public void initialize() throws LifecycleException {
        updateLifeState(LifeState.INITING);
    }

    @Override
    public void startUp() throws LifecycleException {
        if (lifeState.getState() < 2) {
            initialize();
        }
        updateLifeState(LifeState.STARTING);

    }

    @Override
    public void shutDown() throws LifecycleException {
        if (lifeState.getState() > 5) {
            updateLifeState(LifeState.CLOSING);
        }

    }

    @Override
    public void updateLifeState(LifeState lifeState) {
        logger.info("[{}] is {}",this.getClass().getName(),lifeState.getDescription());
        this.lifeState = lifeState;
    }
}
