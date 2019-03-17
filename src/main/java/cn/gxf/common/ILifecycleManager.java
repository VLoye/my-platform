package cn.gxf.common;/**
 * Created by VLoye on 2018/12/19.
 */

import org.apache.catalina.LifecycleException;

/**
 * @author V
 * @Classname ILifecycleManager
 * @Description 生命周期管理
 **/
public interface ILifecycleManager {

    void initialize() throws LifecycleException;

    void startUp()throws LifecycleException;

    void shutDown()throws LifecycleException;

    void updateLifeState(LifeState lifeState);

}
