package com.v.common;/**
 * Created by VLoye on 2018/12/19.
 */

/**
 * @author V
 * @Classname LifecycleManager
 * @Description 生命周期管理
 **/
public interface LifecycleManager {

    void init();

    void start();

    void close();

    void setLifeState(LifeState lifeState);

}
