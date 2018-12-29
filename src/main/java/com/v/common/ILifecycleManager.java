package com.v.common;/**
 * Created by VLoye on 2018/12/19.
 */

/**
 * @author V
 * @Classname ILifecycleManager
 * @Description 生命周期管理
 **/
public interface ILifecycleManager {

    void init();

    void start();

    void close();

    void setLifeState(LifeState lifeState);

}
