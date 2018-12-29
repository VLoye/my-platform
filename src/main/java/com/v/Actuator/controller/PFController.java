package com.v.Actuator.controller;

/**
 * Created by VLoye on 2018/12/27.
 * 平台控制器，负责管理整个平台的组件以及为组件之间提供互相通讯
 */
public interface PFController {

    public void register(String key, Object component);

    public void unRegister(String key);



}
